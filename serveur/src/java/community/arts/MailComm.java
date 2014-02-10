package community.arts;

import cartago.OPERATION;

public class MailComm extends Community{
	
	void init() {
		super.init( "MailComm" );
	}
	
	@OPERATION void mail(String to, String message) {
		sendPrivateMessage(getOpUserId(), to, message);
	}
	
}
