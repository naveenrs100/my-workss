package com.example.gsa.cli;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CliApplication.class, args);
	}

	
	public class Hi implements Iterable<Integer>
	{
		
		ArrayList<Integer> i = new ArrayList<>();
	
		public void adder()
		{
		i.add(14);
		i.add(20);
		i.add(12);
		}

		@Override
		public java.util.Iterator<Integer> iterator() {
			return i.iterator();
		}
		
	}
	@Override
	public void run(String... args) throws Exception {
		
		Hi h = new Hi();
		h.adder();
		
		for(Integer i : h)
		{
			System.out.println(i);
		}
		
		ResourceOwnerPasswordResourceDetails owner = new ResourceOwnerPasswordResourceDetails();
		
		owner.setClientAuthenticationScheme(AuthenticationScheme.header);
		
		owner.setAccessTokenUri("http://localhost:9999/services/oauth/token");
		
		owner.setClientId("pluralsight");
		
		owner.setClientSecret("pluralsightsecret");
		
		owner.setScope(Arrays.asList("toll_read"));
		
		
		owner.setUsername("Gouri");
		
		owner.setPassword("gouri");
		
		OAuth2RestTemplate temp = new OAuth2RestTemplate(owner);
		
		String s = temp.getForObject("http://localhost:9997/get", String.class);
		
		
		
		System.out.println(temp.getAccessToken().toString());	
		
		System.out.println(s);
	}
}
