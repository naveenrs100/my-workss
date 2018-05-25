package com.ibm;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main {
	
	// https://abhishhm.in.ibm.com:9443/ccm/rpt/repository/workitem/workItem/id/312?Java%20UI=workitem/workItem/

	public static void main(String[] args) throws Exception  {
		URL url=new URL("http://9.109.212.174:8975/display/getAll");
		SiteConn aConnection=new SiteConn(url);
		StringBuffer sb=aConnection.getContents();
		String s = sb.toString();
		System.out.println(s);
		JSONArray js = new JSONArray(s);
		for (int u = 0; u < js.length(); u++) {
			JSONObject jb  = js.getJSONObject(u);
			System.out.println(jb.get("address"));
	}

	}

}
