// CArtAgO artifact code for project projetmas

import java.util.LinkedList;
import java.util.ListIterator;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;

abstract class Community extends Artifact {
	
	
	protected LinkedList<String> m_followers ;
	
	protected AgentId m_server ;
	
	protected String m_owner ;
	
	
	/**
	 * Community Initialization
	 * @param type Community Type
	 */
	void init(String type, String src ) {
		defineObsProperty("type", type);
		
		m_followers = new LinkedList<String>() ;
		m_followers.add( src );
		
		m_owner = src ;
		m_server = getCreatorId() ;
	}
	
	
	/**
	 * Ask to follow a community
	 * Add the agent to the follower and signal to others
	 */
	@OPERATION
	void follow( String src ) {
		m_followers.add( src );
		
		ListIterator<String> ite = m_followers.listIterator(0);
		while( ite.hasNext() ) {
			String currname = ite.next();
			
			signal("newFollowerArti", src, currname );
		}
	}
	
	
	/**
	 * Ask to  stop following a community
	 * Remove the agent to the follower and signal to others
	 */
	@OPERATION
	void stopfollow( String src ) {
		m_followers.remove( getOpUserId() );
		signal("followerLeaveArti", getOpUserId().getAgentName() );
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
		
		ListIterator<String> ite = m_followers.listIterator(0);
		while( ite.hasNext() ) {
			String currname = ite.next();
			
			if( currname == name )
				return currname ;
		}
		return null ;
	}
}

