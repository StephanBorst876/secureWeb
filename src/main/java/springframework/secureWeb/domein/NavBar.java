package springframework.secureWeb.domein;

import springframework.secureWeb.domein.Account.Rol;

/**
 *
 * @author FeniksBV
 */
public class NavBar {

    private boolean accounts;
    private boolean klanten;
    private boolean artikelen;

    public NavBar(String rol) {

        accounts = false;
        klanten = false;
        artikelen = false;
        System.out.println("De rol = " + rol);
        Rol accountRol = Account.Rol.toRol(rol);
        
        if (accountRol.equals(Account.Rol.beheerder)) {
            accounts = true;
        }
        
        if (accountRol.equals(Account.Rol.beheerder) || accountRol.equals(Account.Rol.medewerker)) {
            klanten = true;
            artikelen = true;
        }
        
    }

    /**
     * @return the accounts
     */
    public boolean isAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(boolean accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the klanten
     */
    public boolean isKlanten() {
        return klanten;
    }

    /**
     * @param klanten the klanten to set
     */
    public void setKlanten(boolean klanten) {
        this.klanten = klanten;
    }

    /**
     * @return the artikelen
     */
    public boolean isArtikelen() {
        return artikelen;
    }

    /**
     * @param artikelen the artikelen to set
     */
    public void setArtikelen(boolean artikelen) {
        this.artikelen = artikelen;
    }

}
