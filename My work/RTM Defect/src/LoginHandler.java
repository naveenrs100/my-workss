
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.ITeamRepository.ILoginHandler;
import com.ibm.team.repository.client.ITeamRepository.ILoginHandler.ILoginInfo;

public class LoginHandler implements ILoginHandler, ILoginInfo {

	private String fUserId;
	private String fPassword;


	
	public LoginHandler(String userId, String password) {
		fUserId = userId;
		fPassword = password;
	}

	public String getUserId() {
		return fUserId;
	}

	public String getPassword() {
		return fPassword;
	}

	@Override
	public ILoginInfo challenge(ITeamRepository arg0) {
		return this;
	}
}