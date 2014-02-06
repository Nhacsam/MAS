/**
 *  Agent communityServer in project projetmas
 * 
 * Handle th ecreation, the deletion and the index and the following
 * of each community
 */

/* Initial beliefs and rules */

/**
 * Community list of the server
 */
communities( [] ).

/* Initial goals */
!setup_and_monitor.
+!setup_and_monitor
	<- 	createWorkspace("CommunityServer");
		joinWorkspace("CommunityServer", CommId).


/* Functions */


/**
 * Create a new community
 * @param Name Name of the community to create
 * @param Src Agent who want to create the community
 */
+!createCommunity( Name, Src ) : .string(Name) <-
	makeArtifact(Name ,"MailBoxCommunity",[ Src ],C);
	//focus(C);
	.concat("Create ", Name, Text );
	.print( Text );
	
	!addnewCommunity( Name );
	!newCommunityMsg(Name).

// The name is not a string
+!createCommunity( Name, R ).
// an error occured
-!createCommunity( Name, R ).




/**
 * Follow a community
 * @param Name Name of the community to follow
 * @param Src Agent who want to follow the community
 */
+!followCommunity( Name, Src ) <-
	lookupArtifact( Name, Id);
	follow(Src);
	
	.concat("Follow ", Name, Text );
	.print( Text )
.

-!followCommunity( R ).





/**
 * Stop following a Community
 * @param Name Name of the community to stop following
 * @param Src Agent who want to stop following the community
 */
+!stopfollowingCommunity( Name, Src ) <-
	
	lookupArtifact( Name, Id);
	stopfollow(Src);
	
	.concat("Stop Following ", Name, Text );
	.print( Text ).

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
 * @param Name Name of the community to Add
 */
+!addnewCommunity( Name) : communities(Comms)<-
	.concat( [Name], Comms, Added ).
	


///////////////////////////////////////
///// Messages From the artifacts /////
///////////////////////////////////////

+newFollowerArti( Name, To )[source(Arti)] <- .print(Arti); !newFollowerMsg(Arti, Name, To).

	
///////////////
///// API /////
///////////////

/*
 * IN
 */

/**
 * Ask to Follow a community
 * @param Name Name of the community to follow
 */
+follow( Name )[source(Src) ] <- !followCommunity( Name, Src ).

/**
 * Ask to create a new community
 * @param Name Name of the community to create
 */
+create( Name )[source(Src) ] <- !createCommunity( Name, Src ).

/**
 * Ask to stop following a community
 * @param Name Name of the community to stop following
 */
+stopfollowing( Name )[source(Src) ] <- !stopfollowingCommunity( Name, Src ).




/*
 * OUT
 */
 
 /**
  * Inform the agents that a new community have been created
  */
+!newCommunityMsg(Name) <- .broadcast( tell, newCommunity(Name) ).

/**
 * Inform an agents that there are a new follower on a followed community
 */
+!newFollowerMsg(ArtiName, FollowerName, To) <- .send( To, tell, newFollower(ArtiName, FollowerName) ).