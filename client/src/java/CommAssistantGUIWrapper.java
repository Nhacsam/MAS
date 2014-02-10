// CArtAgO artifact code for project projetmas

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cartago.Artifact;
import cartago.OPERATION;

public class CommAssistantGUIWrapper extends Artifact implements ActionListener {
	
	private CommAssistantGUI m_GUI ;
	
	void init() {

		m_GUI = new CommAssistantGUI("Community Assistant", this);
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
	
	@OPERATION
	public void addNicknameGUI( String Nick, String Name ) {
		m_GUI.addNickname(Nick, Name);
	}
	
	@OPERATION
	public void receiveMessage( String Comm, String Name, String Mess ) {
		m_GUI.receiveMessage( Comm, Name, Mess ) ;
	}
	
	@OPERATION
	public void addFollower( String Comm, String Name ) {
		m_GUI.addFollower( Comm, Name ) ;
	}
	
	
	public void signalFollow( String Name ) {
		signal( "gui", "follow", Name );
	}
	
	public void signalStopFollowing( String Name ) {
		System.out.println(Name);
		signal( "gui", "stopFollowing", Name );
	}
	
	public void sendMessageGUI( String comm, String to, String mess ) {
		signal( "gui", "sendMessage", to, comm, mess );
	}
	
	
	public void actionPerformed(ActionEvent e) { 
		
		Object obj = e.getSource() ;
		
		if( obj == m_GUI.nicknameOk) {
			String nick = m_GUI.nickname.getText() ;
			signal( "gui", "nick", nick );
			
		} else if ( obj == m_GUI.toCreateOk) {
			
			String name = m_GUI.toCreate.getText() ;
			String type = (String) m_GUI.createType.getSelectedItem() ;
			type = "community.arts." + type ;
			
			try {
				signal( "gui", "create", name, type );
			} catch (IllegalMonitorStateException ex ) {}
		}
	}
	
	
}

