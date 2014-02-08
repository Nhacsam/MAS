import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 */

/**
 * @author nikkidbz
 *
 */
public class CommAssistantGUINico extends JFrame {
	
	public JTextField nickname ;
	public JButton nicknameOk ;
	
	public JTabbedPane tabs ;
	public JPanel globalPanel, nicknamePanel, contentPanel ;
	
	
	
	
	public JButton addScheme, removeScheme, setOwner, adoptRole, leaveRole;
	public JTextField roleToAdopt, roleToLeave, schemeToAdd, schemeToRemove, owner;
	public JTextField groupName;
	
	
	public CommAssistantGUINico (String title ) {
		setTitle(title);
		setSize(400,400);
		
		
		globalPanel = new JPanel();
		setContentPane(globalPanel);
		globalPanel.setLayout(new BoxLayout(globalPanel,BoxLayout.Y_AXIS));
		
		
		// NickName
		nicknamePanel = new JPanel();
		nicknamePanel.add(new JLabel("Nickname : "));
		
		nickname = new JTextField(10);
		nicknamePanel.add(nickname);
		
		nicknameOk = new JButton("Ok");
		nicknamePanel.add(nicknameOk);
		
		globalPanel.add(nicknamePanel);
		
		
		// Content
		contentPanel =  new JPanel();
		
		
		
		JPanel panel = new JPanel();
		//setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Group name"));
		groupName = new JTextField(10);
		p1.add(groupName);
		panel.add(p1);
		
		JPanel p2 = new JPanel();
		adoptRole = new JButton("adoptRole");
		roleToAdopt = new JTextField(10);
		p2.add(adoptRole);
		p2.add(roleToAdopt);
		panel.add(p2);
		
		JPanel p3 = new JPanel();
		leaveRole = new JButton("leaveRole");
		roleToLeave = new JTextField(10);
		p3.add(leaveRole);
		p3.add(roleToLeave);
		panel.add(p3);
	
		JPanel p4 = new JPanel();
		addScheme = new JButton("addScheme");
		schemeToAdd = new JTextField(10);
		p4.add(addScheme);
		p4.add(schemeToAdd);
		panel.add(p4);
		
		JPanel p5 = new JPanel();
		removeScheme = new JButton("removeScheme");
		schemeToRemove = new JTextField(10);
		p5.add(removeScheme);
		p5.add(schemeToRemove);
		panel.add(p5);
	}
	
	
}
