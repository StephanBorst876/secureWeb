package springframework.secureWeb;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
