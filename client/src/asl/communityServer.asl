/**
 *  Agent communityManager in project projetmas
 * 
 * Handle the creation, the deletion and the index and the following
 * of each community
 */


/**
 * Community list of the server
 */
communities( [] ).

/* Initial goals */
!setup_and_monitor.
+!setup_and_monitor
	<- 	 joinWorkspace("CommunityServer",SrvId).


/* Functions */

/**
 * Create a new community
 * @param Name Name of the community to create
 * @param Src Agent who want to create the community
 */

+!createForumComm( Name, AgentName) : .string(Name)  <-
	makeArtifact(Name,"community.arts.ForumComm",[Name, AgentName], C);	
	focus(C);
	
	.concat(Name, " was created by ", AgentName , Text);
	printMsg( Text );
	
	!addnewCommunity( Name );
	!newCommunityMsg(Name).

// The name is not a string
+!createCommunity( Name, R ).
// an error occured
-!createCommunity( Name, R ).

/**
 * Delete a community
 * @param Name Name of the community to create
 * @param Src Agent who want to create the community
 */

+!deleteComm( Name, AgentName) : .string(Name)  <-
	lookupArtifact(Name, Id);
	deleteComm(AgentName);
	dispose(Id);
	.concat(Name, " was disposed by ", AgentName , Text);
	printMsg( Text );
	
	!removeCommunity( Name ).

// The name is not a string
+!deleteComm( Name, R ).
// an error occured
-!deleteComm( Name, R ).

/**
 * Add new community in belief base
 * @param Name Name of the community to Add
 */
+!addnewCommunity( Name) : communities(Comms)<-
	.concat( [Name], Comms, Added ).

/**
 * removes a community from belief base
 * @param Name Name of the community to Add
 */
+!removeCommunity( Name) : communities(Comms)<-
	.concat( [Name], Comms, Added ).
	
/**
 * Add new community in belief base
 * @param Name Name of the community to Add
 */
+!addnewCommunity( Name) : communities(Comms)<-
	.concat( [Name], Comms, Added ).


/**
 * Follow a community
 * @param Name Name of the community to follow
 * @param Src Agent who want to follow the community
 */
+!followCommunity( Name, Src ) <-
	lookupArtifact( Name, Id);
	follow(Src) [artifact_name(Name)].

-!followCommunity( R ).


/**
 * Stop following a Community
 * @param Name Name of the community to stop following
 * @param Src Agent who want to stop following the community
 */
+!stopfollowingCommunity( Name, Src ) <-
	
	lookupArtifact( Name, Id);
	stopfollow(Src) [artifact_name(Name)].

+!stopfollowingCommunity( R ).
-!stopfollowingCommunity( R ).


///////////////////////////////////////
///// Messages From the artifacts /////
///////////////////////////////////////

+newFollowerArti( Name, To)[ artifact_name(Id, Arti) ] <- !newFollowerMsg(Arti, Name, To).


	
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
  * Inform the agents that a new community has been created
  */
+!newCommunityMsg(Name) <- .broadcast( tell, newCommunity(Name) ).

/**
 * Inform an agents that there are a new follower on a followed community
 */
+!newFollowerMsg(ArtiName, FollowerName, To) <- .send( To, tell, newFollower(ArtiName, FollowerName) ).