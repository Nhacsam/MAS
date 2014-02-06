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
 * @param Name Return the community Name
 */
+!createCommunity( Name ) <-
	.random(Rand);
	.concat("", Rand, Name );
	.send( "communityServer", tell, create(Name) ).

/**
 * Follow a community
 * @param Name Name of the community to follow
 */
+!followCommunity( Name ) : .string(Name) & communities(Comms) & followedCommunity ( FollowedComms ) <-
	.send( "communityServer", tell, follow(Name) );
	
	.concat(FollowedComms, [C], Added );
	-+followedCommunity( Added );
	
	.delete( C, Comms, Deleted );
	-+communities( Deleted )
.

-!followCommunity( R ).





/**
 * Stop following a Community
 */
+!stopfollowingCommunity( Name ) : .string( Name ) & communities(Comms) & followedCommunity ( FollowedComms )  <-
	
	.send( "communityServer", tell, stopfollowing(Name) );
	
	.concat(Comms, [C], Added );
	-+communities( Added );
	
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