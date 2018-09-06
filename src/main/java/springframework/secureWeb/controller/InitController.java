package springframework.secureWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.Account;

/**
 *
 * @author FeniksBV
 */
@Controller
public class InitController {

    @SuppressWarnings("unused")
    private final AccountRepository accountRepo;

    @Autowired
    public InitController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping("/init")
    public String Initialize(Model model, Authentication authentication) {

        //
        // Deze controller wordt eenmalig doorlopen, direct na het inloggen
        // Enige taak is het zetten van de sessionAttribute accountRole
        //
        String userNaam = authentication.getName();
        Account account = accountRepo.findByuserNaam(userNaam);
        String rol;
        if (account != null) {
            rol = account.getRol().toString();
        } else {
            // Gaat er iets mis, zet dan maar role=klant
            rol = Account.Rol.klant.toString();
        }
        
        return "redirect:/main";
    }
}
