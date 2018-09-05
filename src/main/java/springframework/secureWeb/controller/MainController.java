package springframework.secureWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.Account;
import springframework.secureWeb.domein.NavBar;

/**
 *
 * @author FeniksBV
 */
@Controller
@SessionAttributes(value = {"accountRole", "klantID", "adresID"})
public class MainController {

    @SuppressWarnings("unused")
    private final AccountRepository accountRepo;

    @ModelAttribute("accountRole")
    public String accountRole() {
        return "";
    }

    @Autowired
    public MainController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping("/main")
    public String Main(@ModelAttribute("accountRole") String rol, Model model, Authentication authentication) {

        String userNaam = authentication.getName();
        Account account = accountRepo.findByuserNaam(userNaam);
        if (account != null) {
            rol = account.getRol().toString();
            model.addAttribute("navbar", new NavBar(rol));
        } else {
            model.addAttribute("navbar", new NavBar(Account.Rol.klant.toString()));
        }
        return "main";
    }

}
