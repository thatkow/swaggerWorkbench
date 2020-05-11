package someproject.SwaggerParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectPackage {

	final Project project;
	final List<String> path;

	public ProjectPackage(Project project, String... path) {

		this(project, Arrays.asList(path));
	}

	public ProjectPackage(Project project, List<String> path) {
		this.project = project;
		this.path = path;

		if (asFile().exists()) {
			if (!asFile().isDirectory()) {
				throw new RuntimeException(this + "is not a directory");
			}
		} else {
			throw new RuntimeException(this + " does not exist");
		}
	}

	public ProjectPackage subPackage(String... path) {
		List<String> p = new ArrayList<String>();
		p.addAll(this.path);
		p.addAll(Arrays.asList(path));
		return new ProjectPackage(project, p);
	}

	public File asFile() {
		return new File(project.getJavaSrcRoot(), String.join(File.separator, path));
	}

	@Override
	public String toString() {
		return asFile().toString();
	}

	public boolean deleteFiles() {
		return Arrays.asList(asFile().listFiles()).stream().filter(e -> e.isFile()).allMatch(e -> e.delete());
	}

	public String asPackageDecleration() {
		return String.join(".", path);
	}
}
