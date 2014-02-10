// Agent communityAssistants in project projetmas


/* Include the base */
{ include("assistantBase.asl") }

/* Initial beliefs and rules */

maxNumberOfFollowedCommunities( 10 ).


/* Initial goals */

!start.

/* Plans */

+!start : .my_name(Name) <- 
	makeArtifact(Name ,"CommAssistantGUIWrapperNico",[],C);
	focus(C);
	!joinWork ;
	!handleCommunity.


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
	
	.wait(1000);
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


+!followCommunityNico( C ) : .my_name( Name ) <-
	!followCommunity( C );
	lookupArtifact( Name, Id);
	.concat("Try to follow ", C, Text ); .print(Text);
	followCommunityGUI( C ) [ artifact_id( Id )]
	.




+!userCallbackNew( CommName ) : .my_name( Name ) <-
	.concat("New Comm ", CommName, Text ); .print(Text);
	lookupArtifact( Name, Id);
	addCommunityGUI(CommName) [artifact_id( Id )].


+!userCallbackCreate( CommName ) : .my_name( Name ) <-
	.concat("Create Comm ", CommName, Text ); .print(Text);
	lookupArtifact( Name, Id);
	addCommunityGUI(CommName) [artifact_id( Id )].

+!userCallbackFollow( CommName ) : .my_name( Name ) <-
	.concat("Try to follow ", CommName, Text ); .print(Text);
	lookupArtifact( Name, Id);
	followCommunityGUI( CommName ) [ artifact_id( Id )].
	
+!userCallbackStopFollowing( CommName ) : .my_name( Name ) <-
	.concat("Stop follow ", CommName, Text ); .print(Text);
	lookupArtifact( Name, Id);
	stopFollowingCommunityGUI( CommName ) [ artifact_id( Id )].



/* Events */

+newCommunity( ComId)[source(S)]<- 
	!addnewCommunity( ComId ) .




+newFollower(ArtiName, Name)  <- .concat("New follower ", Name, Text ); .print(Text).
+followerLeave(Name) <- .concat("Follower Leave ", Name, Text ); .print(Text).

