package com.rnctech.nrdata.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	public String validate(HttpServletRequest request) throws AccountException {
		try {
			final String authorization = request.getHeader("Authorization");
			if (authorization == null)
				throw new AccountException("No header of Authorization found!");
			
			if(authorization.toLowerCase().startsWith("basic")) {
				String base64Credentials = authorization.substring("Basic".length()).trim();
				byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				String credentials = new String(credDecoded, StandardCharsets.UTF_8);
				int lidx = credentials.lastIndexOf(":");
				if(-1 == lidx) throw new AccountException("Invalid credientials");
				String pwd = credentials.substring(lidx+1).trim();
				String user = credentials.substring(0, lidx).trim();
				//@Todo check user
				return validateCrediential(user, pwd);
			}else { // Bearer
				String token = authorization.substring("Bearer".length()).trim();
				//@Todo validate token against auth server
				return validateToken(token);
			}
			
		}catch(Throwable t) {
			//any other cases, throw
			throw new AccountException("Invalid credientials");
		}
	}
		
	private String validateCrediential(String user, String pwd) {
		//local db check or ldap service or kerberos(system)
		return user;
	}
	
	
	private String validateToken(String token) {
		//oauth or jwt
		return "pass";
	}

}
