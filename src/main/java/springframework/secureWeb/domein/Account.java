package springframework.secureWeb.domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Account implements Serializable, UserDetails {

    public enum Rol {
        klant, medewerker, beheerder;

        public static Rol toRol(String rol) {
            String rollowercase = rol.toLowerCase();
            if (rollowercase.equals("beheerder")) {
                return Rol.beheerder;
            }

            if (rollowercase.equals("medewerker")) {
                return Rol.medewerker;
            } else {
                return Rol.klant;
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 45, unique = true)
    @ColumnDefault("null")
    @NotBlank(message = "Usernaam is verplicht!")
    private String userNaam;

    @Column(length = 60)
    @ColumnDefault("null")
    @NotBlank(message = "password is verplicht!")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Rol rol;
    @Transient
    private List<SimpleGrantedAuthority> authorities=new ArrayList<>();;

    
    public Account() {
    	
    }

    /**
     * Creëert een nieuwe account
     *
     * @param userNaam de gewenste gebruikersnaam om mee in te loggen
     * @param password het gewenste wachtwoord om mee in te loggen
     * @param rol de rol die de betreffende gebruiker krijgt, op basis waarvan
     * diens rechten in de app worden bepaald
     */
    public Account(String userNaam, String password, Rol rol) {
        this.userNaam = userNaam;
        this.password = password;
        this.rol = rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserNaam(String userNaam) {
        this.userNaam = userNaam;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getId() {
        return this.id;
    }

    public String getUserNaam() {
        return this.userNaam;
    }

    public String getPassword() {
        return this.password;
    }

    public Rol getRol() {
        return this.rol;
    }

    public boolean equals(Account account) {
        if (this.id != account.getId()) {
            return false;
        }
        if (!this.userNaam.equals(account.getUserNaam())) {
            return false;
        }
        if (!this.password.equals(account.getPassword())) {
            return false;
        }
        if (this.rol != account.getRol()) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userNaam;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

  public void  setGrantedAuthorities(SimpleGrantedAuthority drol){
	  this.authorities.add(drol);
  }



	
}
