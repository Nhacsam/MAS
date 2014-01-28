// CArtAgO artifact code for project projetmas

import java.util.LinkedList;
import java.util.ListIterator;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;

abstract class Community extends Artifact {
	
	
	protected LinkedList<AgentId> followers ;
	
	
	/**
	 * Community Initialization
	 * @param type Community Type
	 */
	void init(String type) {
		defineObsProperty("type", type);
		
		followers = new LinkedList<AgentId>() ;
		followers.add( getCreatorId() );
	}
	
	
	/**
	 * Ask to follow a community
	 * Add the agent to the follower and signal to others
	 */
	@OPERATION
	void follow() {
		followers.add( getOpUserId() );
		signal("newFollower", getOpUserId().getAgentName() );
	}
	
	
	/**
	 * Ask to  stop following a community
	 * Remove the agent to the follower and signal to others
	 */
	@OPERATION
	void stopfollow() {
		followers.remove( getOpUserId() );
		signal("followerLeave", getOpUserId().getAgentName() );
	}
	
	
	
	
	/**
	 * Send a public message for all agent who focused the Community Artifact
	 * @param from Agent who sent the message
	 */
	protected void sendPublicMessage( AgentId from ) {
		signal("newMessage", from);
	}
	
	/**
	 * Send a private mesage for an other Agent
	 * @param from Agent who sent the message
	 * @param to Agent who will received the message
	 */
	protected void sendPrivateMessage( AgentId from, AgentId to ) {
		signal(to, "newMessage", from);
	}
	
	/**
	 * Send a private mesage for an other Agent
	 * @param from Agent who sent the message
	 * @param to Agent who will received the message
	 */
	protected void sendPrivateMessage( AgentId from, String to ) {
		
		AgentId toId = findFollowerByName(to );
		signal(toId, "newMessage", from);
	}
	
	/**
	 * 
	 */
	protected AgentId findFollowerByName( String name ) {
		
		ListIterator<AgentId> ite = followers.listIterator(0);
		while( ite.hasNext() ) {
			AgentId id = ite.next();
			
			if( id.getAgentName() == name )
				return id ;
		}
		return null ;
	}
}

