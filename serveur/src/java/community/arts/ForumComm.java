package community.arts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import cartago.OPERATION;

public class ForumComm extends Community{
	
	protected LinkedList<ArrayList<String>> news;
	
	void init(String c_name, String agentName) {
		System.out.println("Forum Community Server Ready");
		createCommunity(c_name, agentName);
	}
	
	@OPERATION void createCommunity(String c_name, String agentName){
		setOwner(agentName);
		setName(c_name);
		commId=counter++;
		follow();
		System.out.println(agentName+" created the community "+c_name+":"+commId+ " as a ForumCommunity");
	}
		
	
	@OPERATION void post(String new_post){
		String agentName = this.getOpUserName();
		if(followers.contains(agentName)){
			ArrayList<String> new_entry = new ArrayList<String>();		
			new_entry.add(agentName);
			new_entry.add(new_post);
			news.add(new_entry);		
			System.out.println(agentName+" posted the message : "+new_post);
		}else
			System.out.println("You need to be a follower to be able to post in this Forum Community.");
	}
	
	@OPERATION void readNewsBy(String agentName){
		ListIterator<ArrayList<String>> ite = news.listIterator(0);
		ArrayList<String> cur;
		System.out.println("List of "+agentName+" posts :");
		while( ite.hasNext() ) {
			cur = ite.next();
			if(cur.contains(agentName))
				System.out.println(cur.get(1));
		}
	}
	
	@OPERATION void readAllNews(){
		ListIterator<ArrayList<String>> ite = news.listIterator(0);
		ArrayList<String> cur;
		while( ite.hasNext() ) {
			cur = ite.next();
			System.out.println(cur.get(0) +" posted : "+ cur.get(1));
		}
	}
	
	
}
