// Agent communityAssistants in project projetmas

/* Initial beliefs and rules */

/*
 * non followed existing Community 
 */
communities( [] ).

/**
 * Comunity followed
 */
followedCommunity( [] ).


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
	!agetAction(Rand, LF);
	
	.length(C, LC );
	.length(F, LF );
	.concat( "Community knowed : ", LC, "  Communities Followed : ", LF, Text);
	.print(Text);
	
	.wait(100);
	!handleCommunity.


// Follow New Community
+!agetAction( Rand, LF ) : Rand < 0.3
					& maxNumberOfFollowedCommunities( M ) &
					LF < M <-
	!getRandomCommunity( Community );
	!followCommunity( Community ).

// Create a New Community
+!agetAction( Rand, LF ) : 0.3 < Rand & Rand < 0.35
					& maxNumberOfFollowedCommunities( M )
					& LF < M <-
	!createCommunity( Community );
	!followCommunity( Community ).

// Stop Following a Community
+!agetAction( Rand, LF ) : 0.4 < Rand & Rand < 0.5  & LF > 0  <-
	!getRandomCommunity( Community );
	.concat("Want to delete ", Community, Text );
	.print( Text );
	!stopfollowingCommunity( Community ).

// Do Nothing
+!agetAction( Rand, LF ).








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

+!followCommunity( R ) <-
	!createCommunity( S );
	!followCommunity( S ).

-!followCommunity( R ).


/**
 * Strop following a Community
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




/* Events */


+newFollower(Name)  <- .concat("New follower ", Name, Text ); .print(Text).
+followerLeave(Name) <- .concat("Follower Leave ", Name, Text ); .print(Text).
+newCommunity( ComId)[source(S)] <- !addnewCommunity( ComId ) .
