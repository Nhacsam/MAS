import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Class which save the information on a community
 * And display its
 * @author nikkidbz
 */
public class CommunityGUI extends JScrollPane {
	
	/**
	 * @var name Name of the community
	 */
	private String name ;
	
	/**
	 * @var followed Mak if the community is followed by the user
	 */
	private boolean followed ;
	
	/**
	 * @var associedTreeNode Tree node associed to the community in the left Pannel 
	 */
	private DefaultMutableTreeNode associedTreeNode = null ;
	
	
	private JPanel globalPanel ;
	
	/**
	 * Constructor
	 * @param name Name of the community
	 */
	public CommunityGUI(String name) {
		this.name = name ;
		constructPanel() ;
	}
	
	

	public void constructPanel() {
		
		globalPanel = new JPanel () ;
		globalPanel.setLayout(new BoxLayout(globalPanel,BoxLayout. Y_AXIS));
		add(globalPanel ) ;
		
		JLabel nameLabel = new JLabel(name) ;
		nameLabel.setFont( new Font( "Serif", Font.PLAIN, 30 ) );
		
		globalPanel.add( nameLabel ) ;
		
		/*
		
		JButton newMessage = new JButton( "Write") ;
		globalPanel.add( newMessage ) ;
		
		
		addMessage("toto", "iqsdh oisqh oif hdoih sdopi fpiosd fiopsd hoi dspoif hopisd fopis doif soif posdi hfpoisdp piof dpoif dspoi hfposid hfpoisd hfpoidsh iofsd oisfd poifsd poifsd pofsdi pfosdi posid fsdoi hpofsdi hposdfi dfspoi fdspoi hfdpo hfsdpoi hfdspoi h" );
		*/
		
	}
	
	
	public CommunityGUI addMessage( String from, String msg ) {
		
		JPanel messagePannel = new JPanel();
		messagePannel.setLayout(new BorderLayout());
		
		JLabel fromLabel = new JLabel( "Message from " + from );
		fromLabel.setForeground(new Color( 255, 0, 0));
		messagePannel.add(fromLabel,BorderLayout.NORTH );
		
		JLabel message = new JLabel(msg );
		messagePannel.add(message,BorderLayout.CENTER );
		
		globalPanel.add( messagePannel ) ;
		
		return this ;
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
	
	
	
	
}
