/**
 * Majordomo of the Community Server
 * Create the worspace CommunityServer
 */
 
!setup_and_monitor.

+!setup_and_monitor
	<- 	createWorkspace("CommunityServer");
		joinWorkspace("CommunityServer", CommId).
