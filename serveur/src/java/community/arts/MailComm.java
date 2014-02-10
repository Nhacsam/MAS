package community.arts;

import cartago.OPERATION;

public class MailComm extends Community{
	
	@OPERATION void mail(String to) {
		sendPrivateMessage(getOpUserId(), to);
	}
	
}
