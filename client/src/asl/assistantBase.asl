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


	
//contains/2
//contains(L,X) is true if L contains at least one X 
contains([X|_],X).
contains([_|L],X):- contains(L,X).


/* Plans */


/**
 * Create a new community
 * @param Name Return the community Name
 */
+!createCommunity( Name ) <-
	!userCallbackCreate( Name );
	.send( "communityServer", tell, create(Name) ).

-!userCallbackCreate( Name ).

/**
 * Follow a community
 * @param Name Name of the community to follow
 */
+!followCommunity( Name ) : .string(Name) & communities(Comms) & followedCommunity ( FollowedComms ) <-
	!userCallbackFollow( Name );
	.send( "communityServer", tell, follow(Name) );
	
	.concat(FollowedComms, [C], Added );
	-+followedCommunity( Added );
	
	.delete( C, Comms, Deleted );
	-+communities( Deleted )
.

// Crate a new community if we try de follow a community which not exist
+!followCommunity( R ) : .my_name( Name )  <-
	.random(RandName);
	.concat("", RandName, R );
	!createCommunity( R );
	!followCommunityNico( R ).
	
-!followCommunity( R ).


-!userCallbackFollow( Name ).


/**
 * Stop following a Community
 */
+!stopfollowingCommunity( Name ) : .string( Name ) & communities(Comms) & followedCommunity ( FollowedComms )  <-
	!userCallbackStopFollowing( Name );
	.send( "communityServer", tell, stopfollowing(Name) );
	
	.concat(Comms, [C], Added );
	-+communities( Added );
	
	.delete( C, FollowedComms, Deleted );
	-+followedCommunity( Deleted )
	.
+!stopfollowingCommunity( R ).
-!stopfollowingCommunity( R ).

-!userCallbackStopFollowing( Name ).



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
	!userCallbackNew( ComId );
	.concat( [ComId], Comms, Added );
	-+communities( Added ).

-!userCallbackNew( Name ).
