// CArtAgO artifact code for project projetmas

import cartago.*;

public class CommAssistantGUIWrapperNico extends Artifact {
	
	private CommAssistantGUINico m_GUI ;
	
	void init() {
		m_GUI = new CommAssistantGUINico("Community Assistant");
		m_GUI.setVisible(true);
	}
	
}

