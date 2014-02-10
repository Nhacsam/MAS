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
	makeArtifact(Name ,"CommAssistantGUIWrapperNico",[],C);
	focus(C);
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
	
	.wait(10);
	!handleCommunity.


// Follow New Community
+!agentAction( Rand, LF ) : Rand < 0.3
					& maxNumberOfFollowedCommunities( M ) &
					LF < M <-
	!getRandomCommunity( Community );
	!followCommunityNico( Community ).

// Create a New Community
+!agentAction( Rand, LF ) : 0.3 < Rand & Rand < 0.35
					& maxNumberOfFollowedCommunities( M )
					& LF < M <-
	.random(RandName);
	.concat("", RandName, Community );
	!createCommunity( Community );
	!followCommunityNico( Community ).

// Stop Following a Community
+!agentAction( Rand, LF ) : 0.4 < Rand & Rand < 0.5  & LF > 0  <-
	!getRandomCommunity( Community );
	.concat("Want to delete ", Community, Text );
	.print( Text );
	!stopfollowingCommunity( Community ).

// Do Nothing
+!agentAction( Rand, LF ).


+!followCommunityNico( C ) : .my_name( Name ) <-
	!followCommunity( C );
	lookupArtifact( Name, Id);
	followCommunity( C ) [ artifact_name( Name )]
	.



// Crate a new community if we try de follow a community which not exist
+!followCommunity( R ) <-
	!createCommunity( S );
	!followCommunityNico( S ).



/* Events */

+newCommunity( ComId)[source(S)] : .my_name( Name ) <- 
	lookupArtifact( Name, Id);
	addCommunity(ComId) [artifact_name( Name )] ; 
	!addnewCommunity( ComId ) .




+newFollower(ArtiName, Name)  <- .concat("New follower ", Name, Text ); .print(Text).
+followerLeave(Name) <- .concat("Follower Leave ", Name, Text ); .print(Text).

