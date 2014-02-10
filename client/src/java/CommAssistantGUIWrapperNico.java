// CArtAgO artifact code for project projetmas

import cartago.*;

public class CommAssistantGUIWrapperNico extends Artifact {
	
	private CommAssistantGUINico m_GUI ;
	
	void init() {
		m_GUI = new CommAssistantGUINico("Community Assistant");
		m_GUI.setVisible(true);
	}
	
	
	@OPERATION
	public void addCommunityGUI( String newComm ) {
		m_GUI.addCommunity(newComm);
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

