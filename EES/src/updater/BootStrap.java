package updater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Checks the current version of the program against a server, then proceeds to
 * update as necessary.
 * 
 * @author Cody Swendrowski
 */
public class BootStrap {

	static double version = 0.0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loadVersionData();
		System.out.println("Version: " + version);

		NetworkAdapter adapter = new NetworkAdapter();
		try {
			adapter.connect("107.8.156.5", 60);
			adapter.getOutputStream().write(0); // Request for version

			double serverVersion = 0.0;
			while (serverVersion == 0.0) {
				if (adapter.isDataAvailable())
					serverVersion = adapter.getInputStream().readDouble();
			}

			System.out.println("Server Version: " + serverVersion);
			
			if (serverVersion > version) {
				adapter.getOutputStream().write(1); // Request for updated jar
				System.out.println("Requested update");
				
				BufferedInputStream bufIn = new BufferedInputStream(adapter.getInputStream());

				File fileWrite = new File("outputsrc.jar");
				OutputStream out = new FileOutputStream(fileWrite);
				BufferedOutputStream bufOut = new BufferedOutputStream(out);
				
				byte buffer[] = new byte[1024];
				while (true) {
					int nRead = bufIn.read(buffer, 0, buffer.length);
					if (nRead <= 0)
						break;
					bufOut.write(buffer, 0, nRead);
				}

				bufOut.flush();
				out.close();
				Runtime.getRuntime().exec("java -jar outputsrc.jar");
			} else
				Runtime.getRuntime().exec("java -jar EmployeeEvalSystem.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void loadVersionData() {
		File file = new File("src\\versionID.txt");
		try {
			if (!file.exists()) { // If file doesn't exist, create one
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("1.0");
				writer.close();
			}

			Scanner scanner = new Scanner(file);

			version = scanner.nextDouble();

			scanner.close();

		} catch (IOException e) {
		}
	}

}
