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
 * @param Name The community Name
 * @param Type Type of the community
 */
+!createCommunity( Name, Type ) : createdCommunity(Comms)  <-
	!userCallbackCreate( Name, Type );
	makeArtifact(Name, Type ,[] ,C );
	
	.concat( [Name], Comms, Added );
	-+createdCommunity( Added );
	
	!addnewCommunity( Name);
	!followCommunity( Name );
	
	.broadcast( tell, newCommunity(Name) ).

+!createCommunity( Name ) <- !createCommunity( Name, "community.arts.MailComm").


-!userCallbackCreate( Name, Type ).



/**
 * Delete a community
 * @param Name Name of the community to create
 */

+!deleteComm( Name ) : .string(Name) & createdCommunity( Created ) & contains(Created, Name ) <-
	lookupArtifact(Name, Id);
	deleteComm;
	dispose(Id).

// The name is not a string
+!deleteComm( Name, R ).
// an error occured
-!deleteComm( Name, R ).




/**
 * Follow a community
 * @param Name Name of the community to follow
 */
+!followCommunity( Name ) : .string(Name) & communities(Comms) & followedCommunity ( FollowedComms ) <-
	!userCallbackFollow( Name );
	lookupArtifact( Name, Id);
	focus( Id );
	follow [artifact_id(Id)];
	
	.concat(FollowedComms, [C], Added );
	-+followedCommunity( Added );
	
	.delete( C, Comms, Deleted );
	-+communities( Deleted )
.

// Crate a new community if we try de follow a community which not exist
+!followCommunity( R ) : .my_name( Name )  <-
	.random(RandName);
	.concat("", RandName, R );
	!createCommunity( R ).
	
-!followCommunity( R ).

-!userCallbackFollow( Name ).






/**
 * Stop following a Community
 * @param Name Name to te community to stop following
 */
+!stopfollowingCommunity( Name ) : .string( Name ) & communities(Comms) & followedCommunity ( FollowedComms )  <-
	!userCallbackStopFollowing( Name );
	lookupArtifact( Name, Id);
	stopfollow [artifact_id(Id)];
	
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
+!addnewCommunity( Name) : communities(Comms)<-
	
	lookupArtifact( Name, Id);
	getType(Type) [artifact_id(Id)];
	
	
	!userCallbackNew( Name, Type );
	.concat( [Name], Comms, Added );
	-+communities( Added ).

-!userCallbackNew( Name, Type ).



/**************
 *** EVENTS ***
 **************/

+newCommunity( Name)[source(S)]<-  !addnewCommunity( Name ) .

