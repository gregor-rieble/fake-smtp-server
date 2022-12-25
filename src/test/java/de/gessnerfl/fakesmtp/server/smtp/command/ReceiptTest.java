package de.gessnerfl.fakesmtp.server.smtp.command;

import org.junit.jupiter.api.Test;
import de.gessnerfl.fakesmtp.server.smtp.util.ServerTestCase;

/**
 * @author Jon Stevens
 */
public class ReceiptTest extends ServerTestCase {

	@Test
	public void testReceiptBeforeMail() throws Exception {
		this.expect("220");

		this.send("HELO foo.com");
		this.expect("250");

		this.send("RCPT TO: bar@foo.com");
		this.expect("503 5.5.1 Error: need MAIL command");
	}

	@Test
	public void testReceiptErrorInParams() throws Exception {
		this.expect("220");

		this.send("HELO foo.com");
		this.expect("250");

		this.send("MAIL FROM: success@subethamail.org");
		this.expect("250 Ok");

		this.send("RCPT");
		this.expect("501 Syntax: RCPT TO: <address>  Error in parameters:");
	}

	@Test
	public void testReceiptAccept() throws Exception {
		this.expect("220");

		this.send("HELO foo.com");
		this.expect("250");

		this.send("MAIL FROM: success@subethamail.org");
		this.expect("250 Ok");

		this.send("RCPT TO: failure@subethamail.org");
		this.expect("553 <failure@subethamail.org> address unknown.");

		this.send("RCPT TO: success@subethamail.org");
		this.expect("250 Ok");
	}

	@Test
	public void testReceiptNoWhiteSpace() throws Exception {
		this.expect("220");

		this.send("HELO foo.com");
		this.expect("250");

		this.send("MAIL FROM: success@subethamail.org");
		this.expect("250 Ok");

		this.send("RCPT TO:success@subethamail.org");
		this.expect("250 Ok");
	}
}
