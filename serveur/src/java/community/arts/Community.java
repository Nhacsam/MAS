package community.arts;

import java.util.LinkedList;
import java.util.ListIterator;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

//CArtAgO artifact code for project projetmas


/**
 * Abstract class for all the communities
 * @author nikkidbz
 *
 */
abstract class Community extends Artifact {
	
	protected LinkedList<AgentId> m_followers ;
	
	protected AgentId m_owner ;
	protected String m_type ;
	
	
	
	abstract void init() ;
	
	
	/**
	 * Community Initialization
	 * @param type Community Type
	 */
	void init(String type) {
		
		m_type = type ;
		m_owner =  getCreatorId() ;
//		m_owner =  getOpUserId() ;
		m_followers = new LinkedList<AgentId>() ;
		m_followers.add( m_owner );
		
		
	}
	
	
	/**
	 * Ask to follow a community
	 * Add the agent to the follower and signal to others
	 */
	@OPERATION void follow() {
		AgentId src = this.getOpUserId() ;
		
		if(!m_followers.contains(src))
			m_followers.add( src );
		
		signal( "arti", "newFollower", src.getAgentName() );

	}
	
	
	
	/**
	 * Ask to  stop following a community
	 * Remove the agent to the follower and signal to others
	 */
	@OPERATION void stopfollow() {
		AgentId src = this.getOpUserId() ;
		
		if (src == m_owner)
			deleteComm();
		else
			m_followers.remove(src);
	}
	
	
	/**
	 * Signal that the community will be deleted
	 */
	@OPERATION void deleteComm() {
		
		AgentId src = this.getOpUserId() ;
		
		if (src == m_owner){
			m_followers.clear();
		}
		
		signal( "arti", "willBeDeleted" );
	}
	
	
	/**
	 * Get the type of the community
	 * @param type Return the Type of the community
	 */
	@OPERATION void getType( OpFeedbackParam<String> type ) {
		type.set(m_type);
	}
	
	
	
	
	
	/**
	 * Send a public message for all agent who focused the Community Artifact
	 * @param from Agent who sent the message
	 */
	protected void sendPublicMessage( AgentId from, String message ) {
		signal("arti", "newMessage", from, message);
	}
	
	/**
	 * Send a private mesage for an other Agent
	 * @param from Agent who sent the message
	 * @param to Agent who will received the message
	 */
	protected void sendPrivateMessage( AgentId from, AgentId to, String message ) {
		signal(to, "arti", "newMessage", from, message);
	}
	
	/**
	 * Send a private mesage for an other Agent
	 * @param from Agent who sent the message
	 * @param to Agent who will received the message
	 */
	protected void sendPrivateMessage( AgentId from, String to, String message ) {
		
		AgentId toId = findFollowerByName(to );
		signal(toId, "arti", "newMessage", from, message);
	}
	
	/**
	 * Find a follower
	 * @param name Name of the follower to find
	 * @return Id of the agent
	 */
	protected AgentId findFollowerByName( String name ) {
		
		ListIterator<AgentId> ite = m_followers.listIterator(0);
		while( ite.hasNext() ) {
			AgentId currname = ite.next();
			
			if( currname.getAgentName() == name )
				return currname ;
		}
		return null ;
	}
	

}

