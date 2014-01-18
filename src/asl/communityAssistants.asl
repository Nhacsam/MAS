// Agent communityAssistants in project projetmas

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start <- 
	?joinWork ;
	?joinArti(CommId) ;
	.print(CommId);
	follow;
	//focus(CommId);
	.print(CommId).


+?joinWork: true <- joinWorkspace("CommunityServer",SrvId).
-?joinWork: true <- .wait(10); ?joinWork.
	
+?joinArti(CommId): true <- lookupArtifact("c",CommId).
-?joinArti(CommId): true <- .wait(10); ?joinArti(CommId).

     
     
     
+newFollower : ID <- .print(ID) .