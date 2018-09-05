package springframework.secureWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	 @Autowired
    private CustomAuthenticationProvider  authProvider;
	 
//	private  final String ENCODED_PASSWORD = passwordEncoder().encode("piet");
	
    String[] staticResources = {
        "/styles.css",
        "/images/**",};

   @Override
    protected void configure( final AuthenticationManagerBuilder auth) throws Exception {
	   
        auth.authenticationProvider(authProvider);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
          	.antMatchers(staticResources).permitAll()
          	.anyRequest().authenticated()
      
            .and()
            .formLogin()
            .loginPage("/login")
            . defaultSuccessUrl("/main")
            .permitAll()
            .and() 
           // .httpBasic()
            .logout()
            .permitAll();
         
    }
    
    /*
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    */
 /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                . defaultSuccessUrl("/main.html")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

   @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user
                = User.withDefaultPasswordEncoder()
                        .username("boer")
                        .password("piet")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
    */
}
