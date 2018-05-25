
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.ibm.team.foundation.common.text.XMLString;
import com.ibm.team.process.client.IProcessClientService;
import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IAuditableClient;
import com.ibm.team.workitem.client.IDetailedStatus;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.client.IWorkItemWorkingCopyManager;
import com.ibm.team.workitem.client.WorkItemOperation;
import com.ibm.team.workitem.client.WorkItemWorkingCopy;
import com.ibm.team.workitem.common.IWorkItemCommon;
import com.ibm.team.workitem.common.model.ICategoryHandle;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.ibm.team.workitem.common.model.IWorkItemHandle;
import com.ibm.team.workitem.common.model.IWorkItemType;

public class CreateDefect  extends WorkItemOperation {

	
	private static String fSummary;
	private static ICategoryHandle fCategory;

	public CreateDefect(String summary, ICategoryHandle category) {
		super("Initializing Work Item");
		fSummary = summary;
		fCategory = category;
	}
	
	
//	private static boolean run(String[] args) throws TeamRepositoryException {
//		if (args.length != 6) {
//			System.out.println("UsageateWorkItemApproval [repositoryURI] [userId] [password] [workItemID] [approverUserID] [approvalName]");
//			System.out.println(" [approverUserID] for example 'bob' ");
//			System.out.println(" [approvalName] for example 'Code Review' ");
//			return false;
//		}
//		return true;
//		}

		
		public static void main(String args[]) throws Exception
		{
		String repositoryURI = "https://abhishhm.in.ibm.com:9443/ccm";
		String userId = "Abhishek";
		String password = "123456Abhi";
		String workItemID = args[3];
		String approverUserID = args[4];
		String approvalName = args[5];
		String projectAreaName  ="Eclipse_Created_Project_Area";
		String categoryName = "Category 1";
		
		
		 TeamPlatform.startup();
		 
		// Logger logger = LogManager.getLogger(CreateDefect.class);
		 
		 Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
			
			//logger.log(Level.INFO, "Connection successful");
		
		
		ITeamRepository teamRepository = TeamPlatform.getTeamRepositoryService().getTeamRepository(repositoryURI);
			teamRepository.registerLoginHandler(new LoginHandler(userId, password));
			IProgressMonitor monitor = new NullProgressMonitor();
			teamRepository.login(monitor);
			

			IProcessClientService processClient = (IProcessClientService) teamRepository
					.getClientLibrary(IProcessClientService.class);
			IAuditableClient auditableClient = (IAuditableClient) teamRepository
					.getClientLibrary(IAuditableClient.class);
			IWorkItemClient workItemClient = (IWorkItemClient) teamRepository
					.getClientLibrary(IWorkItemClient.class);
			
			IWorkItemCommon common = (IWorkItemCommon) teamRepository
					.getClientLibrary(IWorkItemCommon.class);
			
			//IProjectAreaHandle handle
			
			System.out.println(common);
			
			logger.log(Level.INFO, "Connection successful");
			
			
			
			System.out.println("Connection successful");
		
			
		//	URI u = URI.create(projectArea.replace(" ", "%20"));
			
			URI uri = URI.create(projectAreaName.replaceAll(" ", "%20"));
			IProjectArea projectArea = (IProjectArea) processClient
					.findProcessArea(uri, null, null);
			
			
			
			//Mine
	
			
			System.out.println(auditableClient);
			
			//CreateDefect d = new CreateDefect("summary is something",iCategoryhandler);
			
	
			
			//System.out.println(teamRepository.getClientLibrary(IWorkItemType.class));
			
			//processClient.findProcessArea(u , null, monitor);
			
			
//			IProjectArea projectArea = (IProjectArea) processClient
//					.findProcessArea(u, IProcessClientService.ALL_PROPERTIES, monitor);
//			
			
			//System.out.println(projectArea);
			
			// Creating a service ........................
			
			IWorkItemClient service = (IWorkItemClient) teamRepository.getClientLibrary(IWorkItemClient.class);
		
			// This is not correct explore it ..........
			
			                                                                   
		//	IWorkItemType i = service.findWorkItemType(projectArea, "defect" , monitor);

			
			// Getting data by id..................
				int id = 112 ;	
				IWorkItem workItem = workItemClient.findWorkItemById(id, IWorkItem.FULL_PROFILE, monitor);
				
				//IProjectAreaHandle handle =  workItem.getProjectArea();
				
		//		ICategoryHandle iCategoryhandler  =  resolveCategoryId(teamRepository, repositoryURI,  workItem);
				
				IWorkItemType workItemType = workItemClient.findWorkItemType(
						projectArea, "defect", null);
				
				System.out.println(workItemType);
				
				//Alternative ....................
				
				List<String> path = Arrays.asList(categoryName.split("/"));
				ICategoryHandle category = workItemClient.findCategoryByNamePath(
						projectArea, path, monitor);
//				if (category == null) {
//					System.out.println("Category not found.");
//					return false;
//				}
				
				
				
				IWorkItemType newType = service.findWorkItemType(workItem.getProjectArea(),workItem.getWorkItemType() ,monitor );
				
			
//		
		//		IProjectArea projectArea =(IProjectArea) workItem.getProjectArea();
			
				// IWorkItemType workItemType = (IWorkItemType) teamRepository.getClientLibrary(IWorkItemType.class);
				
			//	CreateDefect.run(newType,monitor);
				CreateDefect d = new CreateDefect("Summary is required i guess",category);
				
				
			// May be correct testing is required ................	
//		    WorkItemInitialization operation = new WorkItemInitialization(fSummary);
//		    IWorkItemHandle handle = operation.run(projectArea, monitor);
			 
			 System.out.println("Work item is  "+ workItem.toString());
			 
			 System.out.println(workItem.getHTMLSummary());
		
			 
			
			IWorkItemHandle handle = d.run(workItemType, monitor);

			IWorkItem workItemT = auditableClient.resolveAuditable(handle,
					IWorkItem.FULL_PROFILE, null);
			
			System.out.println(workItemT.getId());
			
			
			IWorkItemWorkingCopyManager copyManager = workItemClient.getWorkItemWorkingCopyManager();

			try {
				copyManager.connect(workItemT, IWorkItem.FULL_PROFILE, monitor);
				WorkItemWorkingCopy wc = copyManager.getWorkingCopy(workItemT);

				// TODO: Do something to the work item.
				
				IWorkItem workItemTe = wc.getWorkItem();
				
				workItemTe.setHTMLSummary(XMLString.createFromPlainText("Not required"));
				
				
				List<String> tags = workItemTe.getTags2();
				
			//	System.out.println(workItem.toString());
				tags.add("NewTags created");
				workItemTe.setTags2(tags);

				IDetailedStatus	status = wc.save(monitor);
				if (status.getCode() != org.eclipse.core.runtime.IStatus.OK) {
					System.out.println("Error: " + status.getDetails()); 
				}
			} catch (Exception e) {
				System.out.println("Unexpected error: " + e.getMessage());
			} finally {
				copyManager.disconnect(workItemT);
			}
				
		     //	d.run(workItemClient, monitor);
			
	//		logger.log(Level.INFO, "Creation successful");
			
			//IWorkItemHandle workItemType = d.getHandler(monitor, teamRepository);
			
		//	logger.log(Level.INFO, "Creation successful");
	
			//IWorkItemHandle workItemType =(IWorkItemHandle) teamRepository.getClientLibrary(IWorkItemHandle.class);;
			 
		}


		
	@Override
	protected  void execute(WorkItemWorkingCopy workingCopy,
			IProgressMonitor monitor) throws TeamRepositoryException {
		
		System.out.println("HY");
		IWorkItem workItem = workingCopy.getWorkItem();
		workItem.setHTMLSummary(XMLString.createFromPlainText(fSummary));
		workItem.setHTMLDescription(XMLString.createFromPlainText(fSummary));
		workItem.setCategory(fCategory);
			
		List<String> tags = workItem.getTags2();
		
		System.out.println(workItem.toString());
		tags.add("NewTags created");
		workItem.setTags2(tags);
		
		
		IDetailedStatus s = workingCopy.save(monitor);
		
		System.out.println(s.toString());
		
		
	
	}
	
	
	protected static ICategoryHandle resolveCategoryId(ITeamRepository teamRepository,String repositoryUri, IWorkItem workItem) throws TeamRepositoryException {
		//  ITeamRepository teamRepository = getTeamRepository();
		  IWorkItemClient workItemClient = (IWorkItemClient) teamRepository.getClientLibrary(IWorkItemClient.class);

		  String subtreePattern = repositoryUri;
		  List<String> path = Arrays.asList(subtreePattern.split("/"));

		  ICategoryHandle category = null;
		 

		  try {
		    category = workItemClient.findCategoryByNamePath(workItem.getProjectArea(), path, null);
		  } catch (Exception e) {
		    String message =
		        "RTCClient: setWorkItemCategory() - findCategoryByNamePath() failed for categoryName '" + subtreePattern
		            + "'!!" + e + ":" + e.getMessage();
		    throw new TeamRepositoryException(message, e);
		  }

		  if (category == null) {
		    throw new TeamRepositoryException(
		        "RTCClient: modifyWorkItemWorkingCopyWithoutSave() - findCategoryByNamePath() failed for categoryName '"
		            + subtreePattern + "'!!");
		  }

		  return category;
		}
}
