package community.arts;

public class MailComm extends Community{
	
	/**
	 * Send a public message for all agent who focused the Community Artifact
	 * @param from Agent who sent the message
	 */
	protected void sendPublicMessage( String from ) {
		signal("newMessage", from);
	}
	
	/**
	 * Send a private message for an other Agent
	 * @param from Agent who sent the message
	 * @param to Agent who will received the message
	 */
	protected void sendPrivateMessage( String from, String to ) {
		signal(to, "newMessage", from);
	}

}
