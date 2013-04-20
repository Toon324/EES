package fbla;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
