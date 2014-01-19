// CArtAgO artifact code for project projetmas

import java.util.LinkedList;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class Community extends Artifact {
	
	public enum COMMUNITYTYPE {
		MAILBOX, FORUM, MODFORUM, TWITTERLIKE, VOTING
	}
	
	private LinkedList<AgentId> followers ;
	
	
	void init(String type) {
		defineObsProperty("type", type);
		
		followers = new LinkedList<AgentId>() ;
		followers.add( getCreatorId() );
	}
	
	@OPERATION
	void follow(OpFeedbackParam text) {
		text.set("test");
		followers.add( getOpUserId() );
		signal("newFollower", getOpUserId() );
	}
	
	private void sendMessage( AgentId from, AgentId to ) {
		signal(to, "newMessage", from);
	}
	
}

