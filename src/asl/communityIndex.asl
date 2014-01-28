// Agent communityIndex in project projetmas

/* Initial beliefs and rules */

/*
 * Community created
 */
communities( [] ).


/* Initial goals */

!joinWork.

/* Plans */

+!joinWork: true <- joinWorkspace("CommunityServer",SrvId).
-!joinWork: true <- .wait(10); !joinWork.

+newCommunity( ComId)[source(S)] : communities( [Comms] ) <-
	.print( ComId);
	-+communities( [Comms, ComId ] ).

+newCommunity( ComId)[source(S)] : communities( [] ) <-
	.print( ComId);
	-+communities( [ ComId ] ).
	
+getRandomCommunity(A)[source(S)] : communities( Comms ) <-
	.print("received random event");
	.shuffle(Comms,Result);
	-+communities( Result );
	!sendFirstCommunity(S) .

+!sendFirstCommunity(S) : communities( [X|L] ) <-
	.print("send 1");
	.send( S, tell, askedCommunity(X) ).
+!sendFirstCommunity(S) : communities( [X|[]] ) <-
	.print("send 2");
	.send( S, tell, askedCommunity(X) ).
+!sendFirstCommunity(S) : communities( [] ) <-
	.print("no community");
	.send( S, tell, noCommunity ).