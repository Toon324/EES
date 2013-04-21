package server;

import java.io.IOException;

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
				}

			} catch (IOException e) {
			}
		}
	}

}
