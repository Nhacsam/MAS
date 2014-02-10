import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * 
 */

/**
 * @author nikkidbz
 *
 */
public class CommAssistantGUI extends JFrame implements TreeSelectionListener {
	
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
	 * @var createType Allow to chose the type of community to create
	 */
	public JComboBox<String> createType ;
	
	/**
	 * @var nicknameOK Button to save the new community
	 */
	public JButton toCreateOk ;
	
	
	/**
	 * @var contentPanel Panel which include the tree and the community Content  
	 */
	public JPanel communityPanel ;
	
	
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
	private CommunityGUI currentCommunity = null ;
	
	
	public JTree communityTree ;
	
	
	public CommAssistantGUI (String title, ActionListener listener) {
		setTitle(title);
		setSize(800,600);
		
		
		JPanel globalPanel = new JPanel();
		setContentPane(globalPanel);
		globalPanel.setLayout(new BorderLayout());
		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
		
		
		// NickName
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.add(new JLabel("Nickname : "));
		
		nickname = new JTextField(10);
		nicknamePanel.add(nickname);
		
		nicknameOk = new JButton("Ok");
		nicknameOk.addActionListener(listener);
		nicknamePanel.add(nicknameOk);
		
		topPanel.add(nicknamePanel);
		
		// Create button 
		JPanel createPanel = new JPanel() ;
		createPanel.add( new JLabel("Create a new community : "));
		
		toCreate = new JTextField(10);
		createPanel.add(toCreate);
		
		String[] Types = { "ForumComm", "MailComm" };
		createType = new JComboBox<String>(Types);
		createPanel.add(createType);
		
		toCreateOk = new JButton("Create");
		toCreateOk.addActionListener(listener);
		createPanel.add(toCreateOk);
		
		topPanel.add(createPanel);
		globalPanel.add(topPanel, BorderLayout.NORTH );
		
		
		// Content
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		knowedCommNode = new DefaultMutableTreeNode("Knowed Community");
		followedCommNode = new DefaultMutableTreeNode("Followed Community"); 
		
		communitiesList = new LinkedList<CommunityGUI>() ;
		
		root.add(knowedCommNode) ;
		root.add(followedCommNode) ;
		
		communityTree = new JTree(  );
		communityTree.addTreeSelectionListener(this);
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(communityTree);
		communityTree.setModel(new DefaultTreeModel(root));
		communityTree.setRootVisible(false);
		
		globalPanel.add(jScrollPane, BorderLayout.WEST);
		
		communityPanel = new JPanel( new CardLayout() );
		globalPanel.add(communityPanel, BorderLayout.CENTER);
		
		
	}
	
	
	
	/**
	 * Add a new community in the knew community list
	 * @param Name Name of the new community
	 * @return this
	 */
	public CommAssistantGUI addCommunity( String Name, String Type ) {
		
		CommunityGUI newComm = new CommunityGUI( Name, Type ) ;
		communitiesList.add( newComm ) ;
		DefaultMutableTreeNode commNode = new DefaultMutableTreeNode(newComm);
		newComm.setAssociedTreeNode(commNode);
		knowedCommNode.add(commNode);
		
		communityPanel.add(newComm.toString(), newComm) ;
		
		if( currentCommunity == null )
			onCommunityClick( newComm );
		
		( (DefaultTreeModel)communityTree.getModel() ).reload() ;
		return this ;
	}
	
	/**
	 * Signal that a community is now followed
	 * @param Name Name the community which is now followed
	 * @return this
	 */
	public CommAssistantGUI followCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			if( comm.getFollowed() )
					return this ;
			
			knowedCommNode.remove( 
					comm.setFollowed(true)
					.getAssociedTreeNode()
				) ;
			followedCommNode.add(comm.getAssociedTreeNode());
		} else 
			throw new InvalidParameterException( "Name Not found ! " + Name );
		
		( (DefaultTreeModel)communityTree.getModel() ).reload() ;
		return this ;
	}
	
	
	/**
	 * Signal that a community has been deleted
	 * @param Name Name the community which has been deleted
	 * @return this
	 */
	public CommAssistantGUI removeCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			( (DefaultMutableTreeNode) comm.getAssociedTreeNode().getParent() ).remove( 
					comm.getAssociedTreeNode()
				) ;
			
			communitiesList.remove(commIndex);
			
		} else 
			throw new InvalidParameterException( "Name Not found !" );
		
		( (DefaultTreeModel)communityTree.getModel() ).reload() ;
		return this ;
	}
	
	/**
	 * Signal that a community is not followed any more
	 * @param Name Name the community which is not followed any more
	 * @return this
	 */
	public CommAssistantGUI stopFollowingCommunity( String Name ) {
		int commIndex = communitiesList.indexOf( new CommunityGUI( Name ) ) ;
		
		if( commIndex != -1 ) {
			CommunityGUI comm = communitiesList.get(commIndex);
			
			if( ! comm.getFollowed() )
				return this ;
			
			followedCommNode.remove( 
					comm.setFollowed(true)
					.getAssociedTreeNode()
				) ;
			knowedCommNode.add(comm.getAssociedTreeNode());
		} else 
			throw new InvalidParameterException( "Name Not found !" );
		
		( (DefaultTreeModel)communityTree.getModel() ).reload() ;
		return this ;
	}
	
	
	
	/**
	 * Change the community displayed
	 * @param c New Community to display
	 */
	public void onCommunityClick( CommunityGUI c ) {
		
		CardLayout cl = (CardLayout)(communityPanel.getLayout());
		cl.show(communityPanel, c.toString() );
		currentCommunity = c ;
	}
	
	/**
	 * Event generated when a community is selected
	 * @param e
	 */
	public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) communityTree
			.getLastSelectedPathComponent();
		if (node == null || node.getLevel() <= 1 )
	      return;
	    
		onCommunityClick( (CommunityGUI) node.getUserObject() );
	}
	
	
	
	
	
	
	
	
	
}
