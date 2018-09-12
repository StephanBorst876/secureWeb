package springframework.secureWeb.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.Account;


@Controller
public class AccountController {

    @SuppressWarnings("unused")
    private final AccountRepository accountRepo;

    @Autowired
    public AccountController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }
	
    @GetMapping("/accounts")
    public String Accounts(Model model) {
        List<Account> accountList = new ArrayList<>();
        accountRepo.findAll().forEach(i -> accountList.add(i));

        model.addAttribute("accounts", accountList);

        return "accounts";
    }
    
    @RequestMapping(value = "/account/muteer/{accountId}")
    public String AccountMuteer(Model model, @PathVariable("accountId") String accountId, @ModelAttribute("message") String message) {
        //
        // Het omzetten van het artikelId doe ik hier, want anders geeft Spring Boot
        // (or Thymeleaf) een Exception wanneer er iets wordt aangeboden als:
        // http://.../account/muteer/abc, met abc geen Long format
        //
        try {
            Long id = Long.valueOf(accountId);
            Optional<Account> optionalArt = accountRepo.findById(id);
            if (optionalArt.isPresent()) {
                Account account = optionalArt.get();
                if (account != null) {
                    model.addAttribute("account", account);
                    return "accountmuteer";
                }
            }

        } catch  (NumberFormatException ex) {

        }

        return "redirect:/accounts";

    }

    @GetMapping("/account/nieuw")
    public String AccountNieuw(Model model) {
    	
        model.addAttribute("account", new Account());

        return "accountNieuw";
    }

    @PostMapping("/accountNieuwForm")
    public String processAccountNieuw(Model model, @Valid Account account, Errors errors) {
        if (errors.hasErrors()) {
            return "accountNieuw";
        }
       
        try {
            account.setPassword(BCrypt.hashpw(account.getPassword(),BCrypt.gensalt(12)));
            accountRepo.save(account);
            }
            catch(Exception ex) {
            	String message = "usernaam bestaat al kies een andere!";
            	model.addAttribute("message", message);
            	  return "accountNieuw";
            }
        // Nu terug naar de Get op /accounts om de gehele lijst te tonen
        return "redirect:/accounts";
    }
    
    @PostMapping("/accountMuteerForm")
    public String processAccountMuteer(Model model, @Valid Account account, Errors errors) {
        if (errors.hasErrors()) {
        	model.addAttribute("account", account);
            return "accountmuteer";
        }
       
    //    if(accountRepo.existsByuserNaam(account.getUserNaam())) {
 //       	return "accountmuteer";
  //      }
        try {
        account.setPassword(BCrypt.hashpw(account.getPassword(),BCrypt.gensalt(12)));
        accountRepo.save(account);
        }
        catch(Exception ex) {
        	String message = "usernaam bestaat al kies een andere!";
        	model.addAttribute("message", message);
        	  return "accountmuteer";
        }
        // Nu terug naar de Get op /accounts om de gehele lijst te tonen
        return "redirect:/accounts";
    }
    
    @PostMapping("/account/verwijder/{accountId}")
    public String verwijderAccount(@PathVariable("accountId") String accountId) {

        Long id = Long.valueOf(accountId);
        accountRepo.deleteById(id);

        // Nu terug naar de Get op /klanten om de gehele lijst te tonen
        return "redirect:/accounts";
    }

	
}
