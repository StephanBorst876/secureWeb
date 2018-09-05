package springframework.secureWeb.domein;

/**
 *
 * @author FeniksBV
 */
public class NavBar {
   
    private boolean accounts;
    
    public NavBar() {
        this.accounts = true;
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
}
