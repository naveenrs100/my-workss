package com.ibm.jazzsandbox;

import java.util.HashMap;
import java.util.Map;

import com.ibm.jazzsandbox.idc.ServiceProvider;
import com.ibm.team.repository.client.TeamPlatform;

public class JazzExecutor {

	private static String repositoryURI = "https://abhishhm.in.ibm.com:9443/ccm";
	private static String username = "Abhishek";
	private static String password = "123456Abhi";
	private static String projectAreaName  ="Eclipse_Created_Project_Area";
	private static String categoryName = "Category 1";
	private static String summary="Lets create something new ";

	private static JazzConnector jazz = new JazzConnector( username,  password,  repositoryURI,  categoryName,  projectAreaName, summary);
	
	public static void main(String[] args) throws Throwable  {
		
		
		try
		{
			TeamPlatform.startup();
		
//		int id =jazz.createDefectItem();
//
//		System.out.println("Defect id is :"+id);
		
//		jazz.updateDefect(375);
//
//		System.out.println("Updated defect is ......");
//
//
//		System.out.println(jazz.findDefectById(375).toString());

			jazz.queryingJazz();

			//		System.out.println(jazz.findDefectById(id).getComments().toString());
			
//			Map<String,String> parameters = new HashMap<>();
//			
//			parameters.put("username", "IgniteAdmin");
//			
//			parameters.put("password", "IgniteAdmin123");
//			
			//https://135.188.242.28:8443/ignitePlatform/setup.html#/
			//https://135.188.242.28:9443/authenticationendpoint/login.do?
			//client_id=kEwo7ZC34GXNg6OSYRMvhGmN7wIa&commonAuthCallerPath=%2Foauth2%2Fauthorize&forceAuth=false&
			//passiveAuth=false&redirect_uri=https%3A%2F%2F135.188.242.28%3A8443%2FignitePlatform%2Fsetup.html&response_type=code&
			//scope=openid&tenantDomain=carbon.super&sessionDataKey=80595787-2760-41a5-97f8-4277b21157e4&relyingParty=kEwo7ZC34GXNg6OSYRMvhGmN7wIa&
			//type=oidc&sp=Ignite&isSaaSApp=false&
			//authenticators=BasicAuthenticator:LOCAL
			
			//https://135.188.242.28:8443/ignitePlatform/index.html
//			ServiceProvider provider = new ServiceProvider("https://135.188.242.28:9443/commonauth", parameters);
//			
//			provider.doPostRequest();
//			
//			System.out.println("Accomolished");
			

		}catch(Exception e)
		{
			System.err.println("Exception is : "+e.getClass().getSimpleName());
			e.printStackTrace();
		}finally
		{
			TeamPlatform.shutdown();
		}

	}

}
