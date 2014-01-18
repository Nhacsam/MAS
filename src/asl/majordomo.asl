/**
 * Majordomo of the Community Server
 * Create the worspace CommunityServer
 */
 
!setup_and_monitor.

+!setup_and_monitor : .my_name( Id )
	<- 	createWorkspace("CommunityServer");
		joinWorkspace("CommunityServer", CommId);
		makeArtifact("c","Community",["MAILBOX"],C).

