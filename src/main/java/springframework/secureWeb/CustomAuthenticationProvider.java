package springframework.secureWeb;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.Account;

@Component
@SessionAttributes("accountRol")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private AccountRepository accountRepo;

    @Autowired
    public CustomAuthenticationProvider(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Account inlogAccount = accountRepo.findByuserNaam(name);
        if (inlogAccount == null) {
            return null;
        }
                
        if (BCrypt.checkpw(password, inlogAccount.getPassword())) {
            // use the credentials
            // and authenticate against the third-party system
        	
        	
        	 SimpleGrantedAuthority r = new  SimpleGrantedAuthority ("ROLE_"+ inlogAccount.getRol().toString().toUpperCase());
           //  System.out.println(r);            
             inlogAccount.setGrantedAuthorities(r);
             Collection<? extends GrantedAuthority> authorities = inlogAccount.getAuthorities();
             
        	// System.out.println(inlogAccount.getUserNaam() + inlogAccount.getAuthorities());
        	 
            return new UsernamePasswordAuthenticationToken(name, password, authorities);
        	
            
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
  
}
