package community.arts;

import java.util.LinkedList;

import cartago.Artifact;
import cartago.OPERATION;

//CArtAgO artifact code for project projetmas


abstract class Community extends Artifact {
	
	protected int commId;
	protected LinkedList<String> followers ;
	protected String owner ;
	protected String name ;
	static int counter=0;
	
	
	void init() {
		System.out.println("Community Server Ready");
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

