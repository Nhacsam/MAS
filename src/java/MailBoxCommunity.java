// CArtAgO artifact code for project projetmas

import cartago.*;

public class MailBoxCommunity extends Community {
	
	void init() {
		super.init( "MAILBOX");
	}
	
	@OPERATION
	void mail(OpFeedbackParam text) {
		text.set("test2");
		followers.add( getOpUserId() );
		signal("newFollower", getOpUserId().getAgentName() );
	}
	
}

