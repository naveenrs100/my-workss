package com.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import com.ibm.team.build.client.ITeamBuildClient;
import com.ibm.team.build.common.model.BuildState;
import com.ibm.team.build.common.model.IBuildAverageData;
import com.ibm.team.build.common.model.IBuildAverageDataHandle;
import com.ibm.team.build.common.model.IBuildDefinition;
import com.ibm.team.build.common.model.query.IBaseBuildResultQueryModel.IBuildResultQueryModel;
import com.ibm.team.links.client.ILinkManager;
import com.ibm.team.links.common.IItemReference;
import com.ibm.team.links.common.ILink;
import com.ibm.team.links.common.ILinkCollection;
import com.ibm.team.links.common.IReference;
import com.ibm.team.links.common.IURIReference;
import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ILoginHandler2;
import com.ibm.team.repository.client.ILoginInfo2;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.client.login.UsernameAndPasswordLoginInfo;
import com.ibm.team.repository.common.IContributor;
import com.ibm.team.repository.common.IContributorHandle;
import com.ibm.team.repository.common.IItem;
import com.ibm.team.repository.common.IItemHandle;
import com.ibm.team.repository.common.IItemType;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.repository.common.UUID;
import com.ibm.team.repository.common.query.IItemQuery;
import com.ibm.team.repository.common.query.IItemQueryPage;
import com.ibm.team.repository.common.query.ast.IPredicate;
import com.ibm.team.repository.common.service.IQueryService;
import com.ibm.team.scm.client.IWorkspaceManager;
import com.ibm.team.scm.common.IVersionable;
import com.ibm.team.scm.common.IVersionableHandle;
import com.ibm.team.workitem.api.common.IWorkItem;

public class ServerProb {
	public static void main(String[] args) {  
	
		//Create a progress monitor
	IProgressMonitor myProgressMonitor = new IProgressMonitor() {
		
		@Override
		public void worked(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void subTask(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setTaskName(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setCanceled(boolean arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isCanceled() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void internalWorked(double arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void done() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beginTask(String arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	} ;
	  
	// Retrieve the userId in a secure way 
	String userId = "hello man";
	
	// Retrieve the password in a secure way  	
	String password = "kdwhfiwhekf"; 
	
	 	
	String repoUri = "https://myserver:9443/ccm";  	
	TeamPlatform.startup() ;
	try {  		
		
		// Login to the repository using the provided credentials  	
		ITeamRepository repo = TeamPlatform.getTeamRepositoryService().getTeamRepository(repoUri);  	
		
		repo.registerLoginHandler(new ILoginHandler2() {  	
			@Override  			
			public ILoginInfo2 challenge(ITeamRepository repo) {  			
				return new UsernameAndPasswordLoginInfo(userId, password);  			
				}  		
			});  		
		repo.login( myProgressMonitor);  		  	
		
		/* Do all of my work with myserver here. */  		  		
		repo.logout();  	
		} catch (TeamRepositoryException e) {  		
			
			/* Handle repository exceptions such as login problems here. */  	
			} finally {  		
				
				TeamPlatform.shutdown();  	
				
			} 
	  }  
	
	

	/* -------------------------If you need a particular client in your program you access them using
	 *  the ITeamRepository that you used to log into the server. 
	 *  Once you have a client you can begin accessing items on the server. */
	
	/* Use the repo to get the build client library */ 
	ITeamBuildClient buildClient = (ITeamBuildClient) repo.getClientLibrary(ITeamBuildClient.class);  
	
	/* Get the build definition for "my.build.id" */ 
	IBuildDefinition definition = buildClient.getBuildDefinition("my.build.id", myProgressMonitor);  
	
	
	/*  Using the build client to get a build definition   */
	
	 IItemManager itemManager = repo.itemManager();
	
	 IBuildAverageDataHandle buildAverageData = definition.getBuildAverageData(); 
	
	 IBuildAverageData data = (IBuildAverageData)itemManager.fetchCompleteItem(buildAverageData,
			 																IItemManager.DEFAULT, myProgressMonitor);    
	 
	 /* The average build time is not available from the handle. It has to be fetched from the server. */  
	   long averageBuildTime = data.getAverageBuildTimeTaken();  
	   
	   
	   /*------------------ Fetching a complete item using the item manager----------------------------------- */
	   
	   
	   /*      Find all of contributor handles for people who resolved the defects in a certain range.   
	    *   There could be a lot of duplicates. Handles do not override equals() or hashCode() so   
	    *     a HashMap with the UUID as a key helps to eliminates duplicate contributor handles.     */  
	   Map<UUID, IContributorHandle> resolverHandles = new HashMap<UUID, IContributorHandle>();
	   
	   for (int i=1000; i<1234; i++) { 
		   
		   IWorkItem workItem = workItemClient.findWorkItemById(String.valueOf(i),
				   												IWorkItem.FULL_PROFILE, myProgressMonitor);  
		   
		   IContributorHandle resolver = workItem.getResolver();  
		   
		   resolverHandler.put(resolver.getItemId(), resolver); 
		   
		   }   
	
	   
	   static {
	   /* Fetch all of the unique resolvers from the server in one request. */  
	   @SuppressWarnings("unchecked")  
	   List<IContributor> resolvers = itemManager.fetchCompleteItems(
			   
			   new ArrayList<IContributorHandle>(resolverHandles.values()),  
			   
			   IItemManager.DEFAULT, myProgressMonitor);  
	   
	   
	  
	   
	   /* ----------------------------------  Special Case: Versionables ------------------------------------  */
	   IWorkspaceManager workspaceManager = (IWorkspaceManager)repo.getClientLibrary(IWorkspaceManager.class);
	   
	   IVersionableHandle handle = new IVersionableHandle() {
		
		@Override
		public long size() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean sameStateId(IItemHandle arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean sameItemId(IItemHandle arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void protect() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void makeImmutable() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isUnmanaged() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSimple() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isImmutable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isConfigurationAware() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isAuditable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasStateId() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasFullState() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public UUID getStateId() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getOrigin() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public IItemType getItemType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UUID getItemId() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public IItem getFullState() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getAdapter(Class arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	};   
	   
	   IVersionable versionable = workspaceManager.versionableManager().fetchCompleteState(handle, myProgressMonitor); 
	   
	   versionable.getName(); 
	   /* Gets the name of the file, folder or symbolic link in this state. */  
	   
	   
	   
	   /* ------------------------------ Querying for Items --------------------------------------------*/
	   /* The build definition is easy to get once you know the build ID */
	   IBuildDefinition definition = buildClient.getBuildDefinition("my.build.id", myProgressMonitor);  
	   
	   IBuildResultQueryModel buildResultQueryModel = IBuildResultQueryModel.ROOT; 
	   
	   IItemQuery query = IItemQuery.FACTORY.newInstance(buildResultQueryModel);   
	   
	   /* Build up a query filter predicate that accepts a build definition as input to the query and checks for  
	    *     any non-personal builds that have a completed state. */
	   IPredicate predicate = (buildResultQueryModel.buildDefinition()._eq(query.newItemHandleArg()))._and(     
			   						buildResultQueryModel.personalBuild()._isFalse())._and(      	       
			   								buildResultQueryModel.buildState()._eq(BuildState.COMPLETED.name()));  
	   query.filter(predicate);   
	   
	   /* Order by build start time in descending order */  
	   query.orderByDsc(buildResultQueryModel.buildStartTime());   
	   /* Query for items using the build definition's item ID as the argument. */  
	   
	   /* Use a smaller page size if possible depending on what is queried. */ 
	   IItemQueryPage queryPage = buildClient.queryItems(query, new Object[] { definition.getItemId() },  
			   IQueryService.ITEM_QUERY_MAX_PAGE_SIZE, myProgressMonitor);   
	   
	   /* Iterate through the results of this page */  
	   while (queryPage.hasNext()) {  	
		   queryPage = (IItemQueryPage) buildClient.fetchPage(queryPage.getToken(), 
				   queryPage.getNextStartPosition(), 1, new SysoutProgressMonitor());  
		  
		   
		  // Thats mine man .........
		 @SuppressWarnings("unchecked")
		List<String> p= (List<String>) queryPage.getItemHandles();
		 
		 p.forEach(System.out::println);
		 
		 //Yup thats the end.....
		   
	   /* Iterate through each subsequent page. Break out of the loop if finished early. */
		   
	   }  
	   
	   
	   /* ----------------- Discovering Links Between Items ------------------------------------*/
	   
	   ILinkManager linkManager = (ILinkManager) repo.getClientLibrary(ILinkManager.class); 
	   
	   IItemReference itemReference = linkManager.referenceFactory().createReferenceToItem(myItem); 
	   
	   ILinkCollection linkCollection = linkManager.findLinksByTarget(workItemReference, myProgressMonitor).getAllLinksFromHereOn();   
	   
	   for (ILink link: linkCollection) { 
		   /* Links can have types that may help to figure out the meaning of the link.   	
		    * 	Also, a specific link type can be provided to findLinks() methods on the  	
		    * 	ILinkManager to filter out all links except the provided type. */  	
		   
		   String linkTypeId = link.getLinkTypeId();    	
		   
		   IReference sourceRef = link.getSourceRef();  	
		   
		   IReference targetRef = link.getTargetRef();  	  
		   
		   /* If the source reference is an item reference then it can be resolved  	  
		    *   to an item handle.*/  	
		   
		   if (sourceRef.isItemReference()) {  		
			   
			   Object source = sourceRef.resolve();  		
			   
		   /* You can use instanceof to see if it is an instance of a  	
		    *         particular item handle type (e.g. IChangeSetHandle). And  	  
		    *         fetch the complete item in the usual way. */  
			   } else if (sourceRef instanceof IURIReference) 
			   {  	
				   /* IURIReference provides a URI and an option content type. */  
				   }  	 
		   /* The target of the reference can be inspected in the same way as the source. */ 
		   
	   }  
	   }
	}
	
}





	