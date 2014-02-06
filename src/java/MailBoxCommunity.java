// CArtAgO artifact code for project projetmas

import cartago.*;

public class MailBoxCommunity extends Community {
	
	void init( String src) {
		super.init( "MAILBOX", src);
	}
	
	@OPERATION
	void mail(OpFeedbackParam text) {
		text.set("test2");
		signal("newFollower", getOpUserId().getAgentName() );
	}
	
}

