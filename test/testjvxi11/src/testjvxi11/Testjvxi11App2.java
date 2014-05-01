/**
 * 
 */
package testjvxi11;

import java.util.Arrays;

import jvxi11.*;

/**
 * @author mferrara
 *
 */
public class Testjvxi11App2 {

	/**
	 * This simple test connects to an MSO4104B via VXI-11 and id's the device.
	 * Note the addresses are set to -1, and the VXI-11 interface is "inst0"
	 * 
	 * @param args
	 *            command line args (ignored)
	 */
	public static void main(String[] args) {
		byte[] inbuf = new byte[1024];

		try {
			VXI11Controller ctlr = VXI11Factory
					.create("134.63.40.139", "inst0");
			VXI11User user = VXI11UserFactory.create();

			Status rval = ctlr.connect(user);
			// System.out.println(rval.toString() + " "
			// + user.getError().toString() + " " + user.getString());
			VXI11Device scope = ctlr.createDevice(-1, -1);
			scope.connect(user);
			// System.out.println(rval.toString() + " "
			// + user.getError().toString() + " " + user.getString());

			// System.out.println(rval.toString());
			if (ctlr.isConnected()) {
				System.out.println("connected");
			} else {
				System.out.println("NOT connected");
			}

			String cmd = "*idn?";
			rval = scope.write(user, cmd.getBytes(), cmd.length());
			rval = scope.read(user, inbuf, 50);
			// System.out.println(rval.toString() + " "
			// + user.getError().toString() + " " + user.getString());
			String resp = new String(inbuf, 0, user.getInt());

			System.out.println("*IDN? returns:" + resp + "\n");

			cmd = ":MESSAGE:SHOW \"Hello\nThis is a test.\n\";:MESSAGE:STATE ON;";
			rval = scope.write(user, cmd.getBytes(), cmd.length());

			Thread.sleep(5000);

			cmd = ":MESSAGE:STATE OFF;";
			rval = scope.write(user, cmd.getBytes(), cmd.length());

			cmd = "SET?";
			rval = scope.write(user, cmd.getBytes(), cmd.length());
			Arrays.fill(inbuf, (byte) 0);
			StringBuilder learnStr = new StringBuilder(20000);
			int nchar = 0;
			do {
				Arrays.fill(inbuf, (byte) 0);
				rval = scope.read(user, inbuf, 512);
				nchar += user.getInt();
				learnStr.append(new String(inbuf, 0, user.getInt()));
				// learnStr.trimToSize();
			} while ((user.getReason() != VXI11User.EOM_CHR)
					&& (user.getReason() != VXI11User.EOM_END));

			System.out.println("Learn string Got " + nchar
					+ " bytes. learnstr length is " + learnStr.length());
			System.out.println("\nLRN string:");
			System.out.println(learnStr);

			rval = scope.local(user);
			scope.disconnect();
		} catch (Exception e) {
			System.out.println("Caught exception" + e.getMessage());
			e.printStackTrace(System.out);
			System.out.flush();
		} finally {
			System.out.println("exit.");
			System.exit(0);
		}
	}

}
