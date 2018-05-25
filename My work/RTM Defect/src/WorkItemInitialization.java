import org.eclipse.core.runtime.IProgressMonitor;

import com.ibm.team.workitem.client.WorkItemOperation;
import com.ibm.team.workitem.client.WorkItemWorkingCopy;
import com.ibm.team.workitem.common.model.IWorkItem;

import com.ibm.team.foundation.common.text.XMLString;
import com.ibm.team.repository.common.TeamRepositoryException;

public class WorkItemInitialization extends WorkItemOperation {
	
	
	 private String fSummary;

	    public WorkItemInitialization(String summary) {
	        super("Initializing Work Item", IWorkItem.FULL_PROFILE);
	        fSummary= summary;
	    }

	    @Override
	    protected void execute(WorkItemWorkingCopy workingCopy, IProgressMonitor monitor) throws TeamRepositoryException {
	        IWorkItem workItem= workingCopy.getWorkItem();
	        workItem.setHTMLSummary(XMLString.createFromPlainText(fSummary));
	    }

	    @Override
	    protected void commit(WorkItemWorkingCopy[] workingCopies, IProgressMonitor monitor) throws TeamRepositoryException {
	    }

}
