import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Class which save the information on a community
 * And display its
 * @author nikkidbz
 */
public class CommunityGUI extends JPanel  implements ActionListener {
	
	/**
	 * @var name Name of the community
	 */
	private String name ;
	
	/**
	 * @var type Type of the community
	 */
	private String type ;
	
	/**
	 * @var followed Mak if the community is followed by the user
	 */
	private boolean followed ;
	
	/**
	 * @var associedTreeNode Tree node associed to the community in the left Pannel 
	 */
	private DefaultMutableTreeNode associedTreeNode = null ;
	
	private CommAssistantGUI parent ;
	
	
	/**
	 * @var followers List of followers
	 */
	LinkedList<String> followers ;
	
	/**
	 * @var followButton Follow/unFollow Button 
	 */
	private JButton followButton ;
	
	/**
	 * @var newMessage Write Button
	 */
	private JButton newMessage ;
	
	
	private JPanel messagePanel ;
	private JPanel forumPanel ;
	
	

	
	/**
	 * @var PostMessage Field for posting a new message in the forum community
	 */
	public JTextField PostMessage ;
	
	/**
	 * @var PostMessageOk Button to validate posting the message
	 */
	public JButton PostMessageOk ;
	
	
	/**
	 * @var PreviousMessages where we print the previous posted messages
	 */
	public JLabel PreviousMessages = new JLabel();
	
	/**
	 * Constructor
	 * @param name Name of the community
	 */
	public CommunityGUI(String name) {
		this.name = name ;
	}
	
	
	/**
	 * Constructor
	 * @param name Name of the community
	 * @param type Type of community
	 */
	public CommunityGUI(String name, String type, CommAssistantGUI parent ) {
		this.parent = parent ;
		this.name = name ;
		this.type = type ;
		followers = new LinkedList<String>() ;
		
		if( type ==  "ForumComm"  )
			constructForumPanel() ;
		else
			constructMailPanel() ;
	}
	
	
	
	public void constructForumPanel() {
		
		JPanel globalPanel = new JPanel () ;
		globalPanel.setLayout(new BoxLayout(globalPanel,BoxLayout.Y_AXIS));
		globalPanel.setVisible(true);
		
		JLabel nameLabel = new JLabel(name) ;
		nameLabel.setFont( new Font( "Serif", Font.PLAIN, 30 ) );
		globalPanel.add( nameLabel ) ;
		
		this.add(globalPanel ) ;
		
		
		JButton newMessage = new JButton( "Write") ;
		globalPanel.add( newMessage ) ;
		
		
		forumPanel = new JPanel () ;
		forumPanel.setLayout(new BoxLayout(forumPanel,BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane( forumPanel );
		scrollPane.setPreferredSize( new Dimension(500, 400) );
		globalPanel.add( scrollPane ) ;
		
		// Post new message button 
		forumPanel.add( new JLabel("Post new message: "));
		
		PostMessage = new JTextField(30);
		forumPanel.add(PostMessage);
		
		
		PostMessageOk = new JButton( "Post") ;
		PostMessageOk.addActionListener(this);
		forumPanel.add( PostMessageOk ) ;
		
		followButton = new JButton( "Follow/UnFollow") ;
		followButton.addActionListener(this);
		globalPanel.add( followButton ) ;
		
		forumPanel.add(PreviousMessages);
		
		this.add(forumPanel );
			
	}
	
	
	
	

	public void constructMailPanel() {
		
		JPanel globalPanel = new JPanel () ;
		globalPanel.setLayout(new BoxLayout(globalPanel,BoxLayout.Y_AXIS));
		globalPanel.setVisible(true);
		
		JLabel nameLabel = new JLabel(name) ;
		nameLabel.setFont( new Font( "Serif", Font.PLAIN, 30 ) );
		globalPanel.add( nameLabel ) ;
		
		this.add(globalPanel ) ;
		
		followButton = new JButton( "Follow/UnFollow") ;
		followButton.addActionListener(this);
		globalPanel.add( followButton ) ;
		
		newMessage = new JButton( "Write") ;
		newMessage.addActionListener(this);
		globalPanel.add( newMessage ) ;
		
		
		messagePanel = new JPanel () ;
		messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane( messagePanel );
		scrollPane.setPreferredSize( new Dimension(500, 400) );
		globalPanel.add( scrollPane ) ;
		
		
		addMessage("toto", "iqsdh oisqh oif hdoih sdopi fpiosd fiopsd hoi" );
		addMessage("toto", "iqsdh oisqh oif hdoih sdopi fpiosd fiopsd hoi" );
		addMessage("toto", "iqsdh oisqh oif hdoih sdopi fpiosd fiopsd hoi" );
	}
	
	public CommunityGUI addMessage( String from, String msg ) {
		if( type ==  "ForumComm"  )
			addForumMessage(from, msg) ;
		else
			addMailMessage(from, msg) ;
		
		return this ;
	}
	
	
	
	
	
	
	public void addMailMessage( String from, String msg ) {
		
		JLabel fromLabel = new JLabel( "Message from " + from );
		fromLabel.setForeground(new Color( 255, 0, 0));
		messagePanel.add( fromLabel ) ;
		
		JLabel message = new JLabel(msg );
		
		JScrollPane messagePannel = new JScrollPane( message );
		messagePannel.setPreferredSize(new Dimension(500, 150) );
		messagePanel.add( messagePannel ) ;
		
	}
	
	
	public void addForumMessage(String from, String msg) {
		JLabel postLabel = new JLabel( "Posted by" + from + ":" + msg );
		forumPanel.add( postLabel );
		
	}
	
	
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) { 
		
		Object obj = e.getSource() ;
		
		if( obj == followButton ) {
			if( followed )
				parent.stopFollowingCommunity(this );
			else
				parent.followCommunity(this) ;
			
		} else if ( obj == newMessage ) {
			String to = getMessageToDialog();
			String message = getMessageDialog() ;
			parent.sendMessage(this, to, message );
		}
		
		
	}
	
	
	
	private String getMessageToDialog() {
		
		String[] followersArray = {} ;
		followers.toArray( followersArray ) ;
		
		return (String) JOptionPane.showInputDialog(this,
                "Send a message",
                "Send a message - Select a receiver",
                JOptionPane.PLAIN_MESSAGE,
                null,
                followersArray,
                "a" );
	}
	
	private String getMessageDialog() {
		
		return (String) JOptionPane.showInputDialog(this,
                "Send a message",
                "Send a message - Write a message",
                JOptionPane.PLAIN_MESSAGE);
	}
	
	
	
	public void addFollower( String nick ) {
		followers.add(nick);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name ;
	}
	

	/**
	 * Redefine the equals method to just compare names
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if( obj instanceof String )
			return (name == obj) ;
		if (!(obj instanceof CommunityGUI))
			return false;
		CommunityGUI other = (CommunityGUI) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}

	/**
	 * Setter for followed attribute
	 * @param f
	 * @return this
	 */
	public CommunityGUI setFollowed( boolean f ) {
		followed = f ;
		return this ;
	}
	
	/**
	 * Getter for the followed attribute
	 * @return followed
	 */
	public boolean getFollowed() {
		return followed ;
	}

	/**
	 * @return the associedTreeNode
	 */
	public DefaultMutableTreeNode getAssociedTreeNode() {
		return associedTreeNode;
	}

	/**
	 * @param associedTreeNode the associedTreeNode to set
	 * @return this
	 */
	public CommunityGUI setAssociedTreeNode(DefaultMutableTreeNode associedTreeNode) {
		this.associedTreeNode = associedTreeNode;
		return this ;
	}
	
	public String getName(){
		return name ;
	}
	
	
}
