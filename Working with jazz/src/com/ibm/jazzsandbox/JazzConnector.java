package com.ibm.jazzsandbox;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.ibm.team.foundation.common.text.XMLString;
import com.ibm.team.process.client.IProcessClientService;
import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.process.common.IProjectAreaHandle;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.common.IAuditableHandle;
import com.ibm.team.repository.common.IContributor;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IAuditableClient;
import com.ibm.team.workitem.client.IDetailedStatus;
import com.ibm.team.workitem.client.IQueryClient;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.client.IWorkItemWorkingCopyManager;
import com.ibm.team.workitem.client.WorkItemOperation;
import com.ibm.team.workitem.client.WorkItemWorkingCopy;
import com.ibm.team.workitem.common.IAuditableCommon;
import com.ibm.team.workitem.common.IQueryCommon;
import com.ibm.team.workitem.common.expression.AttributeExpression;
import com.ibm.team.workitem.common.expression.IQueryableAttribute;
import com.ibm.team.workitem.common.expression.IQueryableAttributeFactory;
import com.ibm.team.workitem.common.expression.QueryableAttributes;
import com.ibm.team.workitem.common.expression.Term;
import com.ibm.team.workitem.common.expression.Term.Operator;
import com.ibm.team.workitem.common.model.AttributeOperation;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.ICategoryHandle;
import com.ibm.team.workitem.common.model.IEnumeration;
import com.ibm.team.workitem.common.model.ILiteral;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.ibm.team.workitem.common.model.IWorkItemHandle;
import com.ibm.team.workitem.common.model.IWorkItemType;
import com.ibm.team.workitem.common.model.Identifier;
import com.ibm.team.workitem.common.model.ItemProfile;
import com.ibm.team.workitem.common.query.IQueryDescriptor;
import com.ibm.team.workitem.common.query.IQueryResult;
import com.ibm.team.workitem.common.query.IResolvedResult;
import com.ibm.team.workitem.common.query.IResult;
import com.ibm.team.workitem.common.query.QueryTypes;
import com.ibm.team.workitem.common.text.WorkItemTextUtilities;


public class JazzConnector extends WorkItemOperation{
	
	public JazzConnector(String summary, ICategoryHandle category) {

		super("Initializing Work Item");
		
		this.summary=summary;
		
		this.category=category;
		

	}
	
	private String summary;

	private ICategoryHandle category;
	
	private String username;
	
	private String password;
	
	private String repositoryURI;
	
	private String categoryName;
	
	private String projectAreaName;
	
	
	public JazzConnector( String username, String password, String repositoryURI, String categoryName,
			String projectAreaName,String summary) {
		
		super("Initialising an instance");
		
		this.username = username;
		
		this.password = password;
		
		this.repositoryURI = repositoryURI;
		
		this.categoryName = categoryName;
		
		this.projectAreaName = projectAreaName;
		
		this.summary = summary;
	}

	public int createDefectItem() throws TeamRepositoryException
	{
		//TeamPlatform.startup();
		
       	ITeamRepository teamRepository = TeamPlatform.getTeamRepositoryService()
       			.getTeamRepository(repositoryURI);
       	
		teamRepository.registerLoginHandler(new LoginHandler(password,username));
		
		
        IProgressMonitor monitor = new NullProgressMonitor();
		
		teamRepository.login(monitor);
		
		IWorkItemClient workItemClient = (IWorkItemClient) teamRepository
				.getClientLibrary(IWorkItemClient.class);
		
		IProcessClientService processClient = (IProcessClientService) teamRepository
				.getClientLibrary(IProcessClientService.class);
		
		IAuditableClient auditableClient = (IAuditableClient) teamRepository
				.getClientLibrary(IAuditableClient.class);
		
		URI uri = URI.create(projectAreaName.replaceAll(" ", "%20"));
		
		IProjectArea projectArea = (IProjectArea) processClient
				.findProcessArea(uri, null, monitor);

		IWorkItemType workItemType = workItemClient.findWorkItemType(
				projectArea, "defect", monitor);
		
		List<String> path = Arrays.asList(categoryName.split("/"));
		
		ICategoryHandle category = workItemClient.findCategoryByNamePath(
				projectArea, path, monitor);

		JazzConnector connector = new JazzConnector("Summary is required i guess",category);
		
		IWorkItemHandle handle = connector.run(workItemType, monitor);

		IWorkItem workItemT = auditableClient.resolveAuditable(handle,
				IWorkItem.FULL_PROFILE, monitor);
		
		return workItemT.getId();
		
		
	}
	
	@SuppressWarnings("rawtypes")
	public void updateDefect(int id) throws TeamRepositoryException
	{
		
      //  TeamPlatform.startup();
		
       	ITeamRepository teamRepository = TeamPlatform.getTeamRepositoryService()
       			.getTeamRepository(repositoryURI);
       	
		teamRepository.registerLoginHandler(new LoginHandler(password,username));
		
		
        IProgressMonitor monitor = new NullProgressMonitor();
		
		teamRepository.login(monitor);
		
		IWorkItemClient workItemClient = (IWorkItemClient) teamRepository
				.getClientLibrary(IWorkItemClient.class);

		IWorkItem workItem = findDefectById(id);

		IWorkItemWorkingCopyManager copyManager = workItemClient.getWorkItemWorkingCopyManager();

		try {
			
			copyManager.connect(workItem, IWorkItem.FULL_PROFILE, monitor);
			
			WorkItemWorkingCopy wc = copyManager.getWorkingCopy(workItem);
			
			IWorkItem workItemTe = wc.getWorkItem();
			
			workItemTe.setHTMLSummary(XMLString.createFromPlainText("Not required"));
			
			
		IAttribute someAttribute= workItemClient.findAttribute(workItem.getProjectArea(), "cause_root", monitor);

		workItemTe.setValue(someAttribute,"Hello Gouri Sankar");

		IAttribute create= workItemClient.findAttribute(workItem.getProjectArea(), "internalSeverity", monitor);

//
//			String ie = someAttribute.getIdentifier();
		
		System.out.println(someAttribute);
//
//		//	 workItemTe.setValue(someAttribute,"Hello everyone how are you");
//
//			 IContributor commentUser = null;
//
//			 commentUser = teamRepository.contributorManager().fetchContributorByUserId(
//						ie, monitor);
//
//			 IComments comments = workItem.getComments();
//
//				IComment newComment = comments.createComment(commentUser,
//						XMLString.createFromPlainText("Exactly it must work...."));
//
//				comments.append(newComment);
//
			// comments.createComment(commentUser, XMLString.createFromPlainText("Lets see what happens"));
			
			 
			 //-------------------------------------------------------------------
			 
			//  IWorkItemClient workItemClient = (IWorkItemClient) teamrepository.getClientLibrary(IWorkItemClient.class);


                   Identifier literalID = null;
                   
                // or IWorkitemCommon  create = IattributeHandle
                   
				    IEnumeration enumeration = workItemClient.resolveEnumeration(create, monitor); 
				    
				    List literals = enumeration.getEnumerationLiterals();
				    
				    for (Iterator iterator = literals.iterator(); iterator.hasNext();) {
				    	
				        ILiteral iLiteral = (ILiteral) iterator.next();
				        
				        if (iLiteral.getName().equals("Minor")) {
				        	
				            literalID = iLiteral.getIdentifier2();
				            
				            break;
				        }
				    }

			  workItemTe.setValue(create,literalID);
		
			
			List<String> tags = workItemTe.getTags2();
			
		   
			tags.add("NewTags created");
			
			workItemTe.setTags2(tags);

			IDetailedStatus	status = wc.save(monitor);
		
			if (status.getCode() != org.eclipse.core.runtime.IStatus.OK) {
				
				System.out.println("Error: " + status.getDetails()); 
			}
		
		}catch (Exception e) {
		
			System.out.println("Unexpected error: " + e.getMessage());
		
		} finally {
		
			copyManager.disconnect(workItem);
		}
	}
	
	public IWorkItem findDefectById(int id) throws TeamRepositoryException
	{
		
       	ITeamRepository teamRepository = TeamPlatform.getTeamRepositoryService()
       			.getTeamRepository(repositoryURI);
       	
		teamRepository.registerLoginHandler(new LoginHandler(password,username));

        IProgressMonitor monitor = new NullProgressMonitor();
		
		teamRepository.login(monitor);
		
		IWorkItemClient workItemClient = (IWorkItemClient) teamRepository
				.getClientLibrary(IWorkItemClient.class);
		           
		IWorkItem workItem = workItemClient.findWorkItemById(id, IWorkItem.FULL_PROFILE, monitor);
		
		return workItem;
	}


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public void queryingJazz() throws TeamRepositoryException {




		ITeamRepository teamRepository = TeamPlatform.getTeamRepositoryService()
				.getTeamRepository(repositoryURI);

		teamRepository.registerLoginHandler(new LoginHandler(password,username));


		IProgressMonitor monitor = new NullProgressMonitor();

		teamRepository.login(monitor);


		IProcessClientService processClient = (IProcessClientService) teamRepository
				.getClientLibrary(IProcessClientService.class);

		IAuditableCommon auditableCommon = (IAuditableCommon) teamRepository
				.getClientLibrary(IAuditableCommon.class);

		IWorkItemClient workItemClient = (IWorkItemClient) teamRepository
				.getClientLibrary(IWorkItemClient.class);

		URI uri = URI.create(projectAreaName.replaceAll(" ", "%20"));

		IProjectArea projectArea = (IProjectArea) processClient
				.findProcessArea(uri, null, monitor);
		
		IProjectAreaHandle projectAreaHandle =projectArea.getProjectArea();


		IQueryClient queryClient = workItemClient.getQueryClient();
		
		
		
		// Get the current user.
		IContributor loggedIn = teamRepository.loggedInContributor();
		
		IQueryDescriptor queryToRun = null;
		
		//IQueryableAttribute queryToRun = null;
		
		// Get all queries of the user in this project area.
		
		List queries = queryClient.findPersonalQueries(
				projectArea.getProjectArea(), loggedIn,
				QueryTypes.WORK_ITEM_QUERY,
				IQueryDescriptor.FULL_PROFILE, monitor);
		
		// Find a query with a matching name
		
		Term t = new Term(Operator.AND);
		
		IQueryableAttribute something = null;
		
		for (Iterator iterator = queries.iterator(); iterator.hasNext();) {
			
			IQueryDescriptor iQueryDescriptor = (IQueryDescriptor) iterator.next();
			
			// something = (IQueryableAttribute) iterator.next();
			
			if (/*something*/iQueryDescriptor.getName().equals("All work items")) {
				
				queryToRun = iQueryDescriptor;
				
			//	Timestamp value= new Timestamp(System.currentTimeMillis());
		//		System.out.println(System.currentTimeMillis());
				
			//	AttributeExpression dueExpression= new AttributeExpression( queryToRun, AttributeOperation.BEFORE, value);
				
			//	t.add(dueExpression);
				
			
//				for (String colId : colIds){
//				log.info( “column ” + colId);
//				}
				
				//System.out.println(queryToRun.getRequestedModified().toString());
//				
//				Timestamp arg0 = new Timestamp(new Date().getTime());
//				queryToRun.setRequestedModified(arg0);
				//queryToRun.setRe
				
				break;
			}
		}
		
		
//		IQueryCommon query =  queryClient;	
//		ItemProfile<IWorkItem> profile= getProfile(something).createExtension(IWorkItem.CREATOR_PROPERTY);
//		IQueryResult<IResolvedResult<IWorkItem>> result= query.getResolvedExpressionResults(projectArea, t, profile);
		
		IAuditableClient auditableClient = (IAuditableClient) teamRepository
				.getClientLibrary(IAuditableClient.class);
		
		

		IQueryableAttribute projectAreaAttribute= findAttribute(projectArea.getProjectArea(), auditableClient, "dueDate", monitor);
		
		IQueryableAttribute projectAreaAttribute5= findAttribute(projectArea.getProjectArea(), auditableClient, "workItemType", monitor);
		
		System.out.println(findAttribute(projectAreaHandle, auditableClient, IWorkItem.PROJECT_AREA_PROPERTY, monitor));
		
		AttributeExpression projectAreaExpression= new AttributeExpression(projectAreaAttribute, AttributeOperation.BEFORE, 
				new Timestamp(System.currentTimeMillis()));
		
		//workItemType
		AttributeExpression projectAreaExpression2= new AttributeExpression(projectAreaAttribute5, AttributeOperation.EQUALS, 
				"defect");
		
		Term term= new Term(Operator.AND);
		term.add(projectAreaExpression);
		term.add(projectAreaExpression2);
		
		IQueryResult<IResolvedResult<IWorkItem>> results = queryClient.
				getResolvedExpressionResults(projectArea, term, IWorkItem.SMALL_PROFILE);
		
		while (results.hasNext(null)) {
//			IResolvedResult<IWorkItem> resolved= 
			System.out.println(results.next(null).getItem().getId());
//			double score= resolved.getScore();
//			String label= WorkItemTextUtilities.getWorkItemText(resolved.getItem());
//			String formatted= String.format("%.2f %s", score, label);
			System.out.println("working");
		}
		
		IQueryResult unresolvedResults = queryClient.getQueryResults(queryToRun);

		ItemProfile loadProfile = IWorkItem.SMALL_PROFILE;
		
		IQueryResult<IResolvedResult> resolvedResults = queryClient.getResolvedQueryResults( queryToRun, loadProfile);

//		while(unresolvedResults.hasNext(monitor))
//		{
//			IResult result = (IResult) unresolvedResults.next(monitor);
//			
//			IWorkItem workItem = auditableCommon.resolveAuditable(
//					(IAuditableHandle) result.getItem(),IWorkItem.SMALL_PROFILE , monitor);
//
//			System.out.println(workItem.getId());
//		}
		
	//	System.out.println(unresolvedResults);
		
	}
	
	@Override
	protected  void execute(WorkItemWorkingCopy workingCopy,
			IProgressMonitor monitor) throws TeamRepositoryException {
	
		
		IWorkItem workItem = workingCopy.getWorkItem();
		
		workItem.setHTMLSummary(XMLString.createFromPlainText(summary));
		
		workItem.setHTMLDescription(XMLString.createFromPlainText(summary));
		
		workItem.setCategory(category);
			
		List<String> tags = workItem.getTags2();
		
		System.out.println(workItem.toString());
		
		tags.add("NewTags created");
		
		workItem.setTags2(tags);
		
		IDetailedStatus s = workingCopy.save(monitor);
		
		System.out.println(s.toString());
		
	}
	
	private static IQueryableAttribute findAttribute(IProjectAreaHandle projectArea, IAuditableCommon auditableCommon, String attributeId, IProgressMonitor monitor) throws TeamRepositoryException {
		IQueryableAttributeFactory factory= QueryableAttributes.getFactory(IWorkItem.ITEM_TYPE);
	//	System.out.println(factory);
		
		return factory.findAttribute(projectArea, attributeId, auditableCommon, monitor);
	}

}
