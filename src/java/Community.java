// CArtAgO artifact code for project projetmas

import java.util.LinkedList;
import java.util.ListIterator;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;

abstract class Community extends Artifact {
	
	
	protected LinkedList<String> followers ;
	
	protected AgentId server ;
	
	protected String owner ;
	
	
	/**
	 * Community Initialization
	 * @param type Community Type
	 */
	void init(String type, String src ) {
		defineObsProperty("type", type);
		
		followers = new LinkedList<String>() ;
		followers.add( src );
		
		owner = src ;
		server = getCreatorId() ;
	}
	
	
	/**
	 * Ask to follow a community
	 * Add the agent to the follower and signal to others
	 */
	@OPERATION
	void follow( String src ) {
		followers.add( src );
		
		ListIterator<String> ite = followers.listIterator(0);
		while( ite.hasNext() ) {
			String currname = ite.next();
			
			signal(server, "newFollowerArti", src, currname);
		}
	}
	
	
	/**
	 * Ask to  stop following a community
	 * Remove the agent to the follower and signal to others
	 */
	@OPERATION
	void stopfollow( String src ) {
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
		
		String toId = findFollowerByName(to );
		signal(toId, "newMessage", from);
	}
	
	/**
	 * 
	 */
	protected String findFollowerByName( String name ) {
		
		ListIterator<String> ite = followers.listIterator(0);
		while( ite.hasNext() ) {
			String currname = ite.next();
			
			if( currname == name )
				return currname ;
		}
		return null ;
	}
}

