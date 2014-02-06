// Agent assistantBase in project projetmas

/* Initial beliefs and rules */

/*
 * non followed existing Community 
 */
communities( [] ).

/**
 * Comunity followed
 */
followedCommunity( [] ).

/**
 * Community created TODO
 */
createdCommunity( [] ).



/* Plans */


/**
 * Create a new community
 * @param C Return the community id
 */
+!createCommunity( R ) <-
	.random(Rand);
	.concat("", Rand, R );
	makeArtifact(R,"MailBoxCommunity",[],C);
	
	.concat("Create ", R, Text );
	.print( Text );
	
	!addnewCommunity( R );
	.broadcast( tell, newCommunity(R) ).

/**
 * Follow a community
 * @param C Id of the community to follow
 */
+!followCommunity( R ) : .string(R) & communities(Comms) & followedCommunity ( FollowedComms ) <-
	lookupArtifact( R, Id);
	follow;
	
	.concat("Follow ", R, Text );
	.print( Text );
	
	.concat(FollowedComms, [C], Added );
	-+followedCommunity( Added );
	
	.delete( C, Comms, Deleted );
	-+communities( Deleted )
.

-!followCommunity( R ).





/**
 * Stop following a Community
 */
+!stopfollowingCommunity( R ) : .string( R ) & communities(Comms) & followedCommunity ( FollowedComms )  <-
	
	lookupArtifact( R, Id);
	stopfollow;
	.concat(Comms, [C], Added );
	-+communities( Added );
	
	.concat("Stop follow ", R, Text );
	.print( Text );
	
	.delete( C, FollowedComms, Deleted );
	-+followedCommunity( Deleted )
	.
+!stopfollowingCommunity( R ).
-!stopfollowingCommunity( R ).



/**
 * Choose a new random community
 */
+!getRandomCommunity( Name ) : communities( Comms ) <- 
	.shuffle(Comms,Result);
	-+communities( Result );
	!getFirstCommunity(Name) .

+!getFirstCommunity(S) : communities( [S|L] ).
+!getFirstCommunity(S) : communities( [] ) <-
	.print("no community").


/**
 * Add new community in belief base
 */
+!addnewCommunity( ComId) : communities(Comms)<-
	.concat( [ComId], Comms, Added );
	-+communities( Added ).