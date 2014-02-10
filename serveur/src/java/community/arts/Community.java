package community.arts;

import java.util.LinkedList;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;

//CArtAgO artifact code for project projetmas


/**
 * Abstract class for all the communities
 * @author nikkidbz
 *
 */
abstract class Community extends Artifact {
	
	protected int commId;
	protected LinkedList<String> followers ;
	protected String owner ;
	protected String name ;
	static int counter=0;
	
	protected LinkedList<String> m_followers ;
	
	protected AgentId m_server ;
	
	protected String m_owner ;
	
	protected String m_type ;
	
	/**
	 * Community Initialization
	 * @param type Community Type
	 */
	void init(String type, String src ) {
		
		m_type = type ;
		m_followers = new LinkedList<String>() ;
		m_followers.add( src );
		
		m_owner = src ;
		m_server = getCreatorId() ;
	}
	
	
	/**
	 * Ask to follow a community
	 * Add the agent to the follower and signal to others
	 */
	@OPERATION void follow( String src ) {
		if(!followers.contains(src))
			followers.add( src );
	}
	
	
	/**
	 * Ask to  stop following a community
	 * Remove the agent to the follower and signal to others
	 */
	@OPERATION void stopfollow( String src ) {
		if (src == owner){
			if(followers.size() == 1){
				followers.remove(src);
				deleteComm(owner);
			}
		}
		else {
			followers.remove(src);
		}
	}
	
	@OPERATION void deleteComm( String src ) {
		if (src == owner){
			followers.clear();
		}
	}
	
	
	
	public int getCommId(){
		return commId;
	}
	

	public LinkedList<String> getFollowers() {
		return followers;
	}
	
	public String listFollowers(){
		return followers.toString();
	}
	
	public int numberFollowers(){
		return followers.size();
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	public static int getCounter() {
		return counter;
	}
	

}

