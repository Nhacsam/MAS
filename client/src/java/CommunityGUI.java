import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Class which save the information on a community
 * And display its
 * @author nikkidbz
 */
public class CommunityGUI extends JPanel {
	
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
	
	
	/**
	 * Constructor
	 * @param name Name of the community
	 */
	public CommunityGUI(String name) {
		this.name = name ;
		constructPanel() ;
	}

	/**
	 * Constructor
	 * @param name Name of the community
	 * @param layout
	 */
	public CommunityGUI(String name, LayoutManager layout) {
		super(layout);
		this.name = name ;
		constructPanel() ;
	}

	/**
	 * Constructor
	 * @param name Name of the community
	 * @param isDoubleBuffered
	 */
	public CommunityGUI(String name, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.name = name ;
		constructPanel() ;
	}

	/**
	 * Constructor
	 * @param name Name of the community
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public CommunityGUI(String name, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.name = name ;
		constructPanel() ;
	}
	
	
	

	public void constructPanel() {
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add( new JLabel(name) ) ;
		
		
		
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
