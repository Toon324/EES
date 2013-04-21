package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Cody Swendrowski
 * 
 */
public class EmployeeEvalServer {
	private static final double VERSION = 1.0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NetworkAdapter adapter = new NetworkAdapter();

		while (true) {
			try {
				adapter.host(60);
				while (!adapter.isConnected()) {
				} // Do nothing until Connected
				while (true) {
					if (adapter.isDataAvailable())
						if (adapter.getInputStream().readInt() == 0) {
							adapter.getOutputStream().writeDouble(VERSION);
							break;
						}
						else if (adapter.getInputStream().readInt() == 1) {
							File updatedJar = new File("EmployeeEvalSystem.jar");
							InputStream in = new FileInputStream(updatedJar);
							
							byte[] buf = new byte[1024];
							int len;
							while ((len = in.read(buf)) > 0)
								adapter.getOutputStream().write(buf, 0, len);
							in.close();
						}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
