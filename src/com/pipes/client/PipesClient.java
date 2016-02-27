package com.pipes.client;

import com.pipes.client.communication.CommunicationManager;
import com.pipes.client.contact.Contact;
import com.pipes.client.contact.ContactManager;
import com.pipes.client.data.objects.Handle;
import com.pipes.client.data.objects.Pipe;
import com.pipes.client.user.User;
import com.pipes.client.user.UserManager;

/**
 * Client to do Integration testing with the Pipes server system and so that we
 * can mimic a Client using.
 * 
 * @author venkata
 *
 */
public class PipesClient {
	public static void main(String[] args) {

		// 1. Create user Profiles
		User alice = UserManager.createNewUser("Alice", "password1");
		User bob = UserManager.createNewUser("Bob", "password2");

		// 2.Alice creates a Handle to give to bob
		Handle aliceHandle = HandleHelper.createNewHandle(alice, "Bob_Club", "capabilities_alice",
				"metadata_alice");

		// Have a variable here to represent that alice has to transfer this
		// token to bob and bob does not have direct access to the handle
		// created by alice.
		String token = aliceHandle.getToken();

		// 3.Bob creates a Pipe based on the handle token created by Alice
		// TODO: Should this be connect to handle???
		Pipe pipe = PipeHelper.createNewPipe(bob, token, "Alice_club", "capabilties_bob",
				"metadata_bob");

		// 4. Bob communicates with Alice
		Contact aliceContact = ContactManager.getContact("Alice_club");
		CommunicationManager.sendTextMessage(bob, aliceContact, "Hey there!!!");

		// 5. Alice communicates with Bob
		Contact bobContact = ContactManager.getContact("Bob_Club");
		CommunicationManager.sendTextMessage(alice, bobContact, "Hi. Great to hear from you!!!!");

		// 6. Disconnect the pipe
		// TODO: Where will the Pipeid be associated to in the client? to the
		// contact?
		PipeHelper.disconnectPipe(pipe.getPipeId());

	}
}
