!setup_and_monitor.

+!setup_and_monitor
	<- 	createWorkspace("server");
		joinWorkspace("server",Id);
		!setupArtifacts.

+!setupArtifacts
	<-  makeArtifact("msg-console","screen.arts.GUIConsole",[]);
//		makeArtifact("forum-community","community.arts.ForumComm",[]);
		printMsg("ready. Your turn!").
		
		
+test <- .print("Test").