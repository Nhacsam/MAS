// Agent communityAssistants in project projetmas


/* Include the base */
{ include("assistantBase.asl") }

/* Initial beliefs and rules */

maxNumberOfFollowedCommunities( 10 ).

/* Initial goals */

!start.

/* Plans */

+!start : .my_name(Name) <- 
	makeArtifact(Name ,"CommAssistantGUIWrapper",[],C);
	+guiId( C );
	focus(C);
	
	!joinWork 
	.//!handleCommunity.


+!joinWork: true <- joinRemoteWorkspace("server","localhost",WspID2).
-!joinWork: true <- .wait(10); !joinWork.


/*
 * Add and Delete Loop
 */
+!handleCommunity : communities( C ) & followedCommunity( F ) <-
	.random(Rand);
	.length(F, LF );
	!agentAction(Rand, LF);
	
	.length(C, LC );
	.length(F, LF );
	.concat( "Community knowed : ", LC, "  Communities Followed : ", LF, Text);
	//.print(Text);
	
	.wait(5000);
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
	.random(RandName);
	.concat("", RandName, Community );
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



+!userCallbackNew( CommName, Type ) : .my_name( Name ) & guiId( GUIID) <-
	.concat("New Comm ", CommName, Text ); .print(Text);
	addCommunityGUI(CommName, Type) [artifact_id( GUIID )].

+!userCallbackFollow( CommName ) : .my_name( Name ) & guiId( GUIID) <-
	.concat("Try to follow ", CommName, Text ); .print(Text);
	followCommunityGUI( CommName ) [ artifact_id( GUIID )].
	
+!userCallbackStopFollowing( CommName ) : .my_name( Name ) & guiId( GUIID) <-
	.concat("Stop follow ", CommName, Text ); .print(Text);
	stopFollowingCommunityGUI( CommName ) [ artifact_id( GUIID )].



/* Events */

+gui( "create", Name, Type ) <- !createCommunity( Name, Type ).



+newFollower(ArtiName, Name)  <- .concat("New follower ", Name, Text ); .print(Text).
+followerLeave(Name) <- .concat("Follower Leave ", Name, Text ); .print(Text).

