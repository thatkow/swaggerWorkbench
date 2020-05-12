package thatkow.swaggerparser;

import java.io.File;

public class MavenProject implements Project {

	final File root;

	public MavenProject(File root) {
		super();
		this.root = root;
		if (getRoot().exists()) {
			if (!getRoot().isDirectory()) {
				throw new RuntimeException(this + " is not a directory");
			}
		} else {
			throw new RuntimeException(this + " does not exist");
		}
	}

	public File getRoot() {
		return root;
	}

	public File getJavaSrcRoot() {
		return new File(root, "src/main/java");
	}

	@Override
	public String toString() {
		return getRoot().toString();
	}

}
