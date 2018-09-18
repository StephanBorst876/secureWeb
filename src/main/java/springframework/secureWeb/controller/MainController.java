package springframework.secureWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.Account;
import springframework.secureWeb.domein.NavBar;

/**
 *
 * @author FeniksBV
 */
@Controller
public class MainController {

    private String rol = null;

    @SuppressWarnings("unused")
    private final AccountRepository accountRepo;

    @Autowired
    public MainController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping({"/", "/main"})
    public String Main(Model model, Authentication authentication) {

      //  if (rol == null) {
            String userNaam = authentication.getName();
              Account account = accountRepo.findByuserNaam(userNaam);
              model.addAttribute("account", account);
    
            if (account != null) {
                rol = account.getRol().toString();
            } else {
                // Gaat er iets mis, zet dan maar role=klant
                rol = Account.Rol.klant.toString();
            }
      //  }
        
        model.addAttribute("navbar", new NavBar(rol));
        return "main";
    }

}
