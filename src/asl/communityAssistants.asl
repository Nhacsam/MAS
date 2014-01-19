// Agent communityAssistants in project projetmas

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start <- 
	!joinWork ;
	!joinArti(CommId) ;
	.print(CommId);
	focus(CommId);
	follow (Text) ;
	.print(Text).


+!joinWork: true <- joinWorkspace("CommunityServer",SrvId).
-!joinWork: true <- .wait(10); !joinWork.
	
+!joinArti(CommId): true <-  lookupArtifact("c",CommId).
-!joinArti(CommId): true <- .wait(10); !joinArti(CommId).


+!createCommunity <-
	makeArtifact("c","Community",["MAILBOX"],C).


     
     
     
+newFollower [artifact_name(Id,"c")] <- .print(ID) .