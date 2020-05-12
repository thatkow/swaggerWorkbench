package thatkow.swaggerparser;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class SwaggerParser {

	final Project swaggerExportProject;
	final ProjectPackage apiDest;
	final ProjectPackage modelDest;
	final ProjectPackage interfaceDest;
	final Function<String, String> interfaceVarParser;
	final ProjectPackage swaggerApiPackage;
	final ProjectPackage swaggerModelPackage;
	final String swaggerCollectiveApiClassname;

	public SwaggerParser(Project swaggerExportProject, ProjectPackage apiDest, ProjectPackage modelDest,
			ProjectPackage interfaceDest, String swaggerCollectiveApiClassname) {
		this(swaggerExportProject, apiDest, modelDest, interfaceDest, new Function<String, String>() {

			@Override
			public String apply(String t) {
				return "I" + t;
			}

		}, swaggerCollectiveApiClassname);
	}

	private SwaggerParser(Project swaggerExportProject, ProjectPackage apiDest, ProjectPackage modelDest,
			ProjectPackage interfaceDest, Function<String, String> interfaceVarParser,
			String swaggerCollectiveApiClassname) {
		super();
		this.swaggerExportProject = swaggerExportProject;
		this.apiDest = apiDest;
		this.modelDest = modelDest;
		this.interfaceDest = interfaceDest;
		this.interfaceVarParser = interfaceVarParser;
		this.swaggerApiPackage = new ProjectPackage(swaggerExportProject, "io", "swagger", "api");
		File modelDir = new File(swaggerExportProject.getRoot(),"src/main/java/io/swagger/model");
		if(!modelDir.exists()){
			modelDir.mkdirs();
		}
		this.swaggerModelPackage = new ProjectPackage(swaggerExportProject, "io", "swagger", "model");
		this.swaggerCollectiveApiClassname = swaggerCollectiveApiClassname;
	}

	public void parse() throws FileNotFoundException {

		if (!apiDest.deleteFiles()) {
			throw new RuntimeException("Failed to delete files in " + apiDest);
		}
		if (!modelDest.deleteFiles()) {
			throw new RuntimeException("Failed to delete files in " + modelDest);
		}
		if (!interfaceDest.deleteFiles()) {
			throw new RuntimeException("Failed to delete files in " + interfaceDest);
		}

		// Process Api
		File[] apiFiles = listFilesMatching(swaggerApiPackage.asFile(), ".*Api.java");
		for (File f : apiFiles) {
			// creates an input stream for the file to be parsed
			FileInputStream in;

			in = new FileInputStream(f.getPath());
			CompilationUnit cu;

			// parse the file
			cu = JavaParser.parse(in);
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cu.setPackageDeclaration(apiDest.asPackageDecleration());
			cu.addImport("org.springframework.http.HttpStatus");

			replaceModelImports(cu);

			final TypeDeclaration<?> rootNode = cu.getType(0);
			if (!rootNode.getFields().stream().allMatch(e -> e.remove())) {
				throw new RuntimeException("Failed to delete fields");
			}
			rootNode.getMethods().forEach(e -> {
				e.removeBody();
				e.setDefault(false);
			});
			
			
			File classApiDest = new File(apiDest.asFile(), f.getName());
			writeOut(classApiDest, cu.toString());
		}

		// Process ApiControllers, adding to the collective API
		File collectiveApi = new File(interfaceDest.asFile(), swaggerCollectiveApiClassname + ".java");
		CompilationUnit collectiveCU = new CompilationUnit(interfaceDest.asPackageDecleration());
		ClassOrInterfaceDeclaration collectiveCUInterface = collectiveCU.addInterface(swaggerCollectiveApiClassname);

		File[] apiControllerFiles = listFilesMatching(swaggerApiPackage.asFile(), ".*ApiController.java");
		for (File f : apiControllerFiles) {
			// creates an input stream for the file to be parsed
			FileInputStream in;

			in = new FileInputStream(f.getPath());
			CompilationUnit cu;

			// parse the file
			cu = JavaParser.parse(in);
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final TypeDeclaration<?> rootNode = cu.getType(0);
			if (!rootNode.getFields().stream().allMatch(e -> e.remove())) {
				throw new RuntimeException("Failed to delete fields");
			}
			rootNode.addAnnotation("Configuration");
			rootNode.findAll(ConstructorDeclaration.class).forEach(e -> e.remove());
			// visit and print the methods names
			new MethodVisitor().visit(cu, rootNode.getNameAsString());
			String interfaceName = interfaceVarParser.apply(rootNode.getNameAsString());
			rootNode.addField(interfaceName, "mapper", Modifier.PRIVATE).addAnnotation("Autowired");
			// fields.forEach(e -> rootNode.add);
			cu.setPackageDeclaration(apiDest.asPackageDecleration());
			cu.addImport("org.springframework.context.annotation.Configuration");
			cu.addImport("org.springframework.beans.factory.annotation.Autowired");
			cu.addImport(interfaceDest.asPackageDecleration() + "." + interfaceName);

			CompilationUnit icu = new CompilationUnit(interfaceDest.asPackageDecleration());
			ClassOrInterfaceDeclaration icuInterface = icu.addInterface(interfaceName);
			rootNode.getMethods().forEach(e -> {
				final MethodDeclaration addMethod = icuInterface.addMethod(e.getNameAsString());
				addMethod.setBody(null);
				e.getParameters().forEach(p -> {

					Parameter pp = new Parameter();
					pp.setName(p.getName());
					pp.setType(p.getTypeAsString());
					addMethod.addParameter(pp);
				});
				addMethod.setType(e.getType());
			});
			icu.addImport("java.util.List");
			icu.addImport("org.springframework.http.ResponseEntity");
			icu.addImport("org.springframework.core.io.Resource");
			icu.addImport("org.springframework.web.multipart.MultipartFile");
			replaceModelImports(cu, icu);

			collectiveCUInterface.addExtendedType(interfaceName);

			File classApiDest = new File(apiDest.asFile(), f.getName());
			writeOut(classApiDest, cu.toString());
			writeOut(new File(interfaceDest.asFile(), interfaceName + ".java"), icu.toString());
		}
		writeOut(collectiveApi, collectiveCU.toString());

		// Copy any other files from io.swagger.api over
		List<File> allApiFiles = Arrays.asList(listFilesMatching(swaggerApiPackage.asFile(), ".*.java"));
		List<File> otherFiles = allApiFiles.stream().filter(e -> !Arrays.asList(apiFiles).contains(e))
				.filter(e -> !Arrays.asList(apiControllerFiles).contains(e)).collect(Collectors.toList());
		for (File f : otherFiles) {
			// creates an input stream for the file to be parsed
			FileInputStream in;

			in = new FileInputStream(f.getPath());
			CompilationUnit cu;

			// parse the file
			cu = JavaParser.parse(in);
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cu.setPackageDeclaration(apiDest.asPackageDecleration());
			File classApiDest = new File(apiDest.asFile(), f.getName());
			writeOut(classApiDest, cu.toString());
		}

		// Process Models
		for (File f : listFilesMatching(swaggerModelPackage.asFile(), ".*.java")) {
			// creates an input stream for the file to be parsed
			FileInputStream in;

			in = new FileInputStream(f.getPath());
			CompilationUnit cu;

			// parse the file
			cu = JavaParser.parse(in);
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cu.setPackageDeclaration(modelDest.asPackageDecleration());
			File classApiDest = new File(modelDest.asFile(), f.getName());
			writeOut(classApiDest, cu.toString());
		}
	}

	private void replaceModelImports(CompilationUnit cu, CompilationUnit... otherCu) {
		// Remove an model imports to swagger and replace pointing to desired location
		List<ImportDeclaration> importsToChange = cu.getImports().stream()
				.filter(e -> e.getNameAsString().contains(swaggerModelPackage.asPackageDecleration()))
				.collect(Collectors.toList());
		for (ImportDeclaration i : importsToChange) {
			cu.remove(i);
		}
		importsToChange.stream().map(e -> modelDest.asPackageDecleration()
				+ e.getNameAsString().replace(swaggerModelPackage.asPackageDecleration(), "")).forEach(r -> {
					cu.addImport(r);
					Arrays.asList(otherCu).forEach(e -> e.addImport(r));
				});

	}

	private void writeOut(File f, String cuAsString) throws FileNotFoundException {
		PrintWriter apiWriter = new PrintWriter(f);
		apiWriter.println(cuAsString);
		apiWriter.close();
	}

	private class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration n, Object arg) {
			deligateMethodsToMapper(n);
		}

		private void deligateMethodsToMapper(MethodDeclaration n) {
			NodeList<Statement> statements = new NodeList<Statement>();
			Statement node = new ReturnStmt("mapper." + n.getNameAsString() + "("
					+ String.join(", ",
							n.getParameters().stream().map(e -> e.getNameAsString()).collect(Collectors.toList()))
					+ ")");
			statements.add(node);
			n.getBody().get().setStatements(statements);
		}
	}

	public static File[] listFilesMatching(File root, String regex) {
		if (!root.isDirectory()) {
			throw new IllegalArgumentException(root + " is no directory.");
		}
		final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
		return root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				boolean matches = p.matcher(file.getName()).matches();
				return matches;
			}
		});
	}
}
