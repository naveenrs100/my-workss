package com.example.authorizationserver.com.example.authorizationserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;


@Configuration
public class ConfigService extends GlobalAuthenticationConfigurerAdapter {
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		.withUser("Gouri").password("gouri").roles("USER","ADMIN").authorities("password").
		and()
		.withUser("Ashok").password("ashok").roles("USER").authorities("password");
	}

}