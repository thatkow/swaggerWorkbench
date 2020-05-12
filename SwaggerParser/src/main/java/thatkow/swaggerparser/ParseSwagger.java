package thatkow.swaggerparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

public class ParseSwagger {
	
	private static final String PREFIX_APILIBS = "apilibs";
	private static final String PREFIX_SWAGGER = "swagger";
	
	public static void main(String[] args) throws IOException {

		String downloadsFolder = getVar("DOWNLOADS_FOLDER_PATH");
		String springServerFolderName = getVar("DOWNLOADED_SERVER_ZIP_FILENAME","spring-server");

		
		// Look into the downloads folder, and find the latest spring-server project
		List<File> downloadSpringServers = Arrays
				.asList(SwaggerParser.listFilesMatching(new File(downloadsFolder), "^"+springServerFolderName+".*\\d*\\.zip"))
				.stream().collect(Collectors.toList());

		List<Integer> downloadNumber = downloadSpringServers.stream().map(e -> {
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(e.getName());
			if (!m.find()) {
				return Integer.MIN_VALUE;
			} else {
				int parseInt = Integer.parseInt(m.group());
				return Integer.valueOf(parseInt);
			}
		}).collect(Collectors.toList());
		if(downloadNumber.isEmpty()) {
			throw new RuntimeException("No zip with the name '"+springServerFolderName+"' could be found");
		}

		File selectedSpringProjZip = downloadSpringServers
				.get(downloadNumber.indexOf(downloadNumber.stream().mapToInt(e -> e).max().getAsInt()));
		System.out.println("Selected swagger project " + selectedSpringProjZip);

		File selectedSpringProj = Files.createTempDir();

		unzip(selectedSpringProjZip.getPath(), selectedSpringProj.getPath());

		Project swaggerExportProject = new MavenProject(new File(selectedSpringProj,springServerFolderName));

		Project ds14db_swagger = new MavenProject(new File(new File("..").getAbsoluteFile(), PREFIX_SWAGGER));
		Project ds14db_api = new MavenProject(new File(new File("..").getAbsoluteFile(), PREFIX_APILIBS));

		ProjectPackage ds14dbSwaggerPackageRoot = new ProjectPackage(ds14db_swagger, "io", "swagger" );
		ProjectPackage ds14dbApiPackageRoot = new ProjectPackage(ds14db_api, "io", "swagger");

		ProjectPackage apiDest = ds14dbSwaggerPackageRoot.subPackage("api");
		ProjectPackage modelDest = ds14dbApiPackageRoot.subPackage("model");
		ProjectPackage interfaceDest = ds14dbApiPackageRoot.subPackage("apidefinitions");

		SwaggerParser swaggerParser = new SwaggerParser(swaggerExportProject, apiDest, modelDest, interfaceDest,
				"ISwaggerApiEntity");

		swaggerParser.parse();
		FileUtils.deleteDirectory(selectedSpringProj);
	}

	private static String getVar(String key) {
		String getenv = System.getProperty(key);
		if(getenv==null) {
			throw new RuntimeException("Environment variable '"+key+"' not found");
		}else {
			return getenv;
		}
	}
	
	private static String getVar(String key, String defaultName) {
		String getenv = System.getenv(key);
		return getenv != null ? getenv : defaultName;
	}

	private static void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
