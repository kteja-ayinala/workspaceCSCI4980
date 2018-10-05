package util;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import simpletableast2editableexample.Activator;

public class PathUtil {
	public static String getNeighborProjectPath(String projectName) {
		String thisPluginID = Activator.getContext().getBundle().getSymbolicName();
		Bundle plugin = Platform.getBundle(thisPluginID);
		URL url = plugin.getEntry("/");
		String result = null;
		try {
			URL resolvedURL = FileLocator.resolve(url);
			File file = new File(resolvedURL.getFile());
			File parentFile = file.getParentFile();
			File neighborProject = new File(parentFile.getAbsolutePath() + File.separator + projectName);
			result = neighborProject.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
