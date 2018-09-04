package springframework.secureWeb;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import springframework.secureWeb.data.AccountRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider  {
	
	
private AccountRepository accountRepo;

@Autowired
	public CustomAuthenticationProvider(AccountRepository accountRepo) {
	
		  this.accountRepo=accountRepo;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
	//	if (accountRepo.findByuserNaam(name).getPassword().equals(passwordEncoder().encode(password))) {
		if (BCrypt.checkpw(password, accountRepo.findByuserNaam(name).getPassword())) {
			// use the credentials
			// and authenticate against the third-party system
			return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
		} else {
			return null;
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}