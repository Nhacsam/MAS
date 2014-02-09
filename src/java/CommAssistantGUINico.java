import java.security.InvalidParameterException;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * 
 */

/**
 * @author nikkidbz
 *
 */
public class CommAssistantGUINico extends JFrame {
	
	/**
	 * @var nickname Nickname field
	 */
	public JTextField nickname ;
	
	/**
	 * @var nicknameOK Button to save nickname changes
	 */
	public JButton nicknameOk ;
	
	
	/**
	 * @var toCreate Field for creating a new community
	 */
	public JTextField toCreate ;
	
	/**
	 * @var nicknameOK Button to save the new community
	 */
	public JButton toCreateOk ;
	
	
	/**
	 * @var contentPanel Panel which include the tree and the community Content  
	 */
	public JPanel contentPanel ;
	
	
	/**
	 * @var knowedCommNode Tree node which include the knew and not followed communities
	 * @var followedCommNode Tree node which include the followed communities
	 */
	public DefaultMutableTreeNode knowedCommNode, followedCommNode ;
	
	/**
	 * @var communitiesList List of knew communities
	 */
	private LinkedList<CommunityGUI> communitiesList ; 
	
	/**
	 * @var currentCommunity Community which is currently diplayed on the screen
	 */
	private JPanel currentCommunity = null ;
	
	
	public CommAssistantGUINico (String title ) {
		setTitle(title);
		setSize(800,600);
		
		
		JPanel globalPanel = new JPanel();
		setContentPane(globalPanel);
		globalPanel.setLayout(new BoxLayout(globalPanel,BoxLayout.Y_AXIS));
		
		
		// NickName
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.add(new JLabel("Nickname : "));
		
		nickname = new JTextField(10);
		nicknamePanel.add(nickname);
		
		nicknameOk = new JButton("Ok");
		nicknamePanel.add(nicknameOk);
		
		globalPanel.add(nicknamePanel);
		
		// Create button 
		JPanel createPanel = new JPanel() ;
		createPanel.add( new JLabel("Create a new community : "));
		
		toCreate = new JTextField(10);
		createPanel.add(toCreate);
		
		toCreateOk = new JButton("Create");
		createPanel.add(toCreateOk);
		
		globalPanel.add(createPanel);
		
		
		// Content
		contentPanel =  new JPanel();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		knowedCommNode = new DefaultMutableTreeNode("Knowed Community");
		followedCommNode = new DefaultMutableTreeNode("Followed Community"); 
		
		communitiesList = new LinkedList<CommunityGUI>() ;
		
		root.add(knowedCommNode) ;
		root.add(followedCommNode) ;
		
		JTree jTree = new JTree(  );
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTree);
		jTree.setModel(new DefaultTreeModel(root));
		jTree.setRootVisible(false);
		
		contentPanel.add(jScrollPane);
		globalPanel.add(contentPanel);
		
		
		this.addCommunity("test 1 ");
		this.addCommunity("Test 2 ");
		this.addCommunity("Test 3 ");
		
		followCommunity( "Test 2 ");
		
	}
	
	
	
	/**
	 * Add a new community in the knew community list
	 * @param Name Name of the new community
	 * @return this
	 */
	public CommAssistantGUINico addCommunity( String Name ) {
		
		CommunityGUI newComm = new CommunityGUI( Name ) ;
		communitiesList.add( newComm ) ;
		DefaultMutableTreeNode commNode = new DefaultMutableTreeNode(newComm);
		newComm.setAssociedTreeNode(commNode);
		knowedCommNode.add(commNode);
		
		if( currentCommunity == null )
			onCommunityClick( newComm );
		
		return this ;
	}
	
	/**
	 * Signal that a community is now followed
	 * @param Name Name the community which is now followed
	 * @return this
	 */
	public CommAssistantGUINico followCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			knowedCommNode.remove( 
					comm.setFollowed(true)
					.getAssociedTreeNode()
				) ;
			followedCommNode.add(comm.getAssociedTreeNode());
		} else 
			throw new InvalidParameterException( "Name Not found !" );
		
		return this ;
	}
	
	
	/**
	 * Signal that a community has been deleted
	 * @param Name Name the community which has been deleted
	 * @return this
	 */
	public CommAssistantGUINico removeCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			( (DefaultMutableTreeNode) comm.getAssociedTreeNode().getParent() ).remove( 
					comm.getAssociedTreeNode()
				) ;
			
			communitiesList.remove(commIndex);
			
		} else 
			throw new InvalidParameterException( "Name Not found !" );
		
		return this ;
	}
	
	/**
	 * Signal that a community is not followed any more
	 * @param Name Name the community which is not followed any more
	 * @return this
	 */
	public CommAssistantGUINico stopFollowingCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			followedCommNode.remove( 
					comm.setFollowed(true)
					.getAssociedTreeNode()
				) ;
			knowedCommNode.add(comm.getAssociedTreeNode());
		} else 
			throw new InvalidParameterException( "Name Not found !" );
		
		return this ;
	}
	
	/**
	 * Change the community displayed
	 * @param c New Community to display
	 */
	public void onCommunityClick( CommunityGUI c ) {
		
		if( currentCommunity != null ) {
			contentPanel.remove(currentCommunity);
		}
		
		currentCommunity = c ;
		contentPanel.add(c);
	}
	
	
	
}
