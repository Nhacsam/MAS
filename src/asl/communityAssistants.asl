// Agent communityAssistants in project projetmas


/* Include the base */
{ include("assistantBase.asl") }

/* Initial beliefs and rules */

maxNumberOfFollowedCommunities( 10 ).


/* Initial goals */

!start.

/* Plans */

+!start : .my_name(Name) <- 
	!joinWork ;
	!handleCommunity.


+!joinWork: true <- joinWorkspace("CommunityServer",SrvId).
-!joinWork: true <- .wait(10); !joinWork.


/*
 * Add and Delete Loop
 */
+!handleCommunity : communities( C ) & followedCommunity( F ) <-
	.random(Rand);
	.length(F, LF );
	!agentAction(Rand, LF);
	
	//!createCommunity(A);
	
	.length(C, LC );
	.length(F, LF );
	.concat( "Community knowed : ", LC, "  Communities Followed : ", LF, Text);
	.print(Text);
	
	.wait(100);
	!handleCommunity.


// Follow New Community
+!agentAction( Rand, LF ) : Rand < 0.3
					& maxNumberOfFollowedCommunities( M ) &
					LF < M <-
	!getRandomCommunity( Community );
	!followCommunity( Community ).

// Create a New Community
+!agentAction( Rand, LF ) : 0.3 < Rand & Rand < 0.35
					& maxNumberOfFollowedCommunities( M )
					& LF < M <-
	!createCommunity( Community );
	!followCommunity( Community ).

// Stop Following a Community
+!agentAction( Rand, LF ) : 0.4 < Rand & Rand < 0.5  & LF > 0  <-
	!getRandomCommunity( Community );
	.concat("Want to delete ", Community, Text );
	.print( Text );
	!stopfollowingCommunity( Community ).

// Do Nothing
+!agentAction( Rand, LF ).






// Crate a new community if we try de follow a community which not exist
+!followCommunity( R ) <-
	!createCommunity( S );
	!followCommunity( S ).



/* Events */


+newFollower(ArtiName, Name)  <- .concat("New follower ", Name, Text ); .print(Text).
+followerLeave(Name) <- .concat("Follower Leave ", Name, Text ); .print(Text).
+newCommunity( ComId)[source(S)] <- !addnewCommunity( ComId ) .
