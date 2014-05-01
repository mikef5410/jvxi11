/**
 * 
 */
package testjvxi11;

import java.util.*;
import jvxi11.*;

/**
 * @author mikef
 *
 */
public class Testjvxi11App {

	/**
	 * This program connects to a Centi SCS via an HP E2050 via VXI-11 Note the
	 * E2050 is configured for device name "hpib". The SCS sits on HPIB at
	 * address 20.
	 * 
	 * @param args
	 *            command line args (ignored)
	 */
	public static void main(String[] args) {
		byte[] inbuf = new byte[1024];
		VXI11Controller ctlr = VXI11Factory.create("mf2050", "hpib");
		VXI11User user = VXI11UserFactory.create();

		Status rval = ctlr.connect(user);
		VXI11Device scs = ctlr.createDevice(20, 0);
		scs.connect(user);
		scs.remote(user);
		// scs.lock(user);
		// scs.setTermChar(user, 13);

		// System.out.println(rval.toString());
		if (ctlr.isConnected()) {
			System.out.println("connected");
		} else {
			System.out.println("NOT connected");
		}

		String cmd = "*idn?";
		rval = scs.remote(user);
		rval = scs.write(user, cmd.getBytes(), cmd.length());
		rval = scs.read(user, inbuf, 50);
		// System.out.println(rval.toString() + " " + user.getError().toString()
		// + " " + user.getString());
		// String resp = new String(inbuf);

		System.out.println("We got back:" + new String(inbuf));

		String cmd2 = ":SOUR:FREQ 5.5GHz; :OUTDelay:OUTPUT ON; :OUTSubrate:DIV 8; :OUTSubrate:OUTPUT ON;";
		rval = scs.write(user, cmd2.getBytes(), cmd2.length());

		// cmd2=":OUTDelay:OUTPUT ON;";
		// rval=scs.write(user, cmd2.getBytes(), cmd2.length());

		cmd2 = ":SOUR:FREQ?";
		rval = scs.write(user, cmd2.getBytes(), cmd2.length());
		Arrays.fill(inbuf, (byte) 0);
		rval = scs.read(user, inbuf, 50);

		System.out.println(new String(inbuf));

		// rval=scs.local(user);
		// System.out.println(rval.toString() + " " + user.getError().toString()
		// + " " + user.getString());

		// scs.unlock(user);
		// System.out.println(rval.toString() + " " + user.getError().toString()
		// + " " + user.getString());
		ctlr.setREN(user, false);
		scs.disconnect();

		System.exit(0);
	}

}
