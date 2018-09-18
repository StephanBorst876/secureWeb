package springframework.secureWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;
    

    String[] staticResources = {
        "/styles.css",
        "/images/**",};

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/artikelen","/artikel/**", "/klanten","/klant/**","/bestellingen","/bestelling/**","/bezorgadres/**", "/factuuradres/**") 
                .hasAnyAuthority("ROLE_MEDEWERKER","ROLE_BEHEERDER" )
                .antMatchers("/accounts", "/account/**") 
                .hasAuthority("ROLE_BEHEERDER")
                .antMatchers(staticResources).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

}
