package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

	public static List<Class<?>> getChildren(Class<?> superClass) {

		List<Class<?>> childClasses = new ArrayList<Class<?>>();

		for (String classpathEntry : System.getProperty("java.class.path").split(System.getProperty("path.separator"))) {
			collectChildrenInFolder(superClass, new File(classpathEntry), childClasses, "");
		}

		return childClasses;
	}

	private static void collectChildrenInFolder(Class<?> parentClass, File folder, List<Class<?>> childClasses, String parentPath) {
		for (File childFile : folder.listFiles()) {
			if (childFile.isDirectory()) {
				collectChildrenInFolder(parentClass, childFile, childClasses, parentPath + childFile.getName() + ".");
			} else {
				if (childFile.getName().endsWith(".class")) {
					try {
						Class<?> c = Class.forName(parentPath + childFile.getName().substring(0, childFile.getName().length() - 6));
						if (parentClass.isAssignableFrom(c) && !parentClass.equals(c)) {
							childClasses.add(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}