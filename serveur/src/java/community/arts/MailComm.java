package community.arts;

import cartago.OPERATION;

public class MailComm extends Community{
	
	void init() {
		super.init( "MailComm" );
	}
	
	@OPERATION void mail(String to) {
		sendPrivateMessage(getOpUserId(), to);
	}
	
}
