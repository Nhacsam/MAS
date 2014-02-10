// CArtAgO artifact code for project projetmas

import cartago.*;

public class CommAssistantGUIWrapper extends Artifact {
	
	private CommAssistantGUI m_GUI ;
	
	void init() {
		m_GUI = new CommAssistantGUI("Community Assistant");
		m_GUI.setVisible(true);
	}
	
	
	@OPERATION
	public void addCommunityGUI( String newComm, String Type ) {
		m_GUI.addCommunity(newComm, Type);
	}
	
	@OPERATION
	public void removeCommunityGUI( String deletedComm ) {
		m_GUI.removeCommunity( deletedComm );
	}
	
	@OPERATION
	public void followCommunityGUI( String comm ) {
		m_GUI.followCommunity(comm);
	}
	
	@OPERATION
	public void stopFollowingCommunityGUI( String comm ) {
		m_GUI.stopFollowingCommunity(comm);
	}
	
}

