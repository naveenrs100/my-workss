package com.ibm.jazzsandbox;

import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.ITeamRepository.ILoginHandler;
import com.ibm.team.repository.client.ITeamRepository.ILoginHandler.ILoginInfo;

public class LoginHandler implements ILoginHandler, ILoginInfo {

	private String password;
	private String username;
	
	

	
	public LoginHandler(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUserId() {
		return username;
	}

	@Override
	public ILoginInfo challenge(ITeamRepository arg0) {
		return this;
	}

	
}
