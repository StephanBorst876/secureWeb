package springframework.secureWeb.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.data.AdresRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Account;
import springframework.secureWeb.domein.Adres;
import springframework.secureWeb.domein.Adres.AdresType;
import springframework.secureWeb.domein.Klant;

/**
 *
 * @author FeniksBV
 */
@Controller
@SessionAttributes({"klantID", "adresID", "bezorgadresID", "factuuradresID"})
public class AdresController {

    @SuppressWarnings("unused")
    private final KlantRepository klantRepo;

    @SuppressWarnings("unused")
    private final AdresRepository adresRepo;
    
    @SuppressWarnings("unused")
    private final AccountRepository accountRepo;

    @Autowired
    public AdresController(KlantRepository klantRepo, AdresRepository adresRepo, AccountRepository accountRepo) {
        this.klantRepo = klantRepo;
        this.adresRepo = adresRepo;
        this.accountRepo = accountRepo;
    }

    @GetMapping("/mijnAdres")
    public String adresGegevens(Model model, Principal principal) {
    	
    	   Klant klant = klantRepo.findKlantByAccount(accountRepo.findByuserNaam(principal.getName()));

              
               Adres adres = findAdres(AdresType.BEZORGADRES, klant.getId());
               model.addAttribute("adres", adres);
               model.addAttribute("bezorgadresID", adres.getId());
               Adres factuuradres = findAdres(AdresType.FACTUURADRES, klant.getId());
    		   model.addAttribute("factuuradres",factuuradres); 
    		   model.addAttribute("factuuradresID", factuuradres.getId());
    	   	   model.addAttribute("klantID",klant.getId() );
        return "mijnAdres";
    }

    
    @RequestMapping(value = "/bezorgadres/{klantId}")
    public String BezorgAdres(Model model, @PathVariable("klantId") String klantId) {

        try {
            Long id = Long.valueOf(klantId);
            Adres adres = findAdres(AdresType.BEZORGADRES, id);
            model.addAttribute("adres", adres);

            model.addAttribute("klant_id", id);
            // 
            // en de sessionattributes
            model.addAttribute("adresID", adres.getId());
            model.addAttribute("klantID", id);

            return "adres";
        } catch (NumberFormatException ex) {

        }

        return "redirect:/klanten";

    }

    @RequestMapping(value = "/factuuradres/{klantId}")
    public String FactuurAdres(Model model, @PathVariable("klantId") String klantId) {
        
        try {
            Long id = Long.valueOf(klantId);
            Adres adres = findAdres(AdresType.FACTUURADRES, id);
            model.addAttribute("adres", adres);

            model.addAttribute("klant_id", id);
            // 
            // en de sessionattributes
            model.addAttribute("adresID", adres.getId());
            model.addAttribute("klantID", id);

            return "adres";
        } catch (NumberFormatException ex) {

        }

        return "redirect:/klanten";
    }

    @PostMapping("/adresForm")
    public String processAdres(@ModelAttribute("adres")@Valid Adres adres, Errors errors, @ModelAttribute("klantID") Long klantID,
            Model model, Principal principal) {

        if (errors.hasErrors()) {
        		model.addAttribute("adres",adres) 	;
        		model.addAttribute("klant_id", klantID);
            if (adres.getAdresType().equals(AdresType.BEZORGADRES)) {
            
                return "adres";
            } else {
                return "adres";
            }
        }

        Klant klant = klantRepo.findById(klantID).get();
        adres.setKlant(klant);
        adresRepo.save(adres);
        
     
        
        // Nu terug naar de klant
        if (accountRepo.findByuserNaam(principal.getName()).getRol().equals(Account.Rol.beheerder)) {
        return "redirect:/klant/muteer/" + klantID;
        }
        else {
        	return "redirect:/main";
        }
    }
        
    @PostMapping("/adres/verwijder")
    public String verwijderAdres(@ModelAttribute("adresID") Long adresID,
            @ModelAttribute("klantID") Long klantID) {

        if (adresID != 0L) {
            adresRepo.deleteById(adresID);
        }

        // Nu terug naar de klant
        return "redirect:/klant/muteer/" + klantID;
    }

    protected Adres findAdres(AdresType adresType, Long klantId) {

        List<Adres> adresList = adresRepo.findAdresByKlant(klantId);
        for (Adres a : adresList) {
            if (a.getAdresType().equals(adresType)) {
                return a;
            }
        }
        Adres adres = new Adres();
        adres.setId(0L);
        adres.setAdresType(adresType);
        return adres;
    }
    
    @PostMapping("/bezorgadresGegevensForm")
    public String processAdresGegevens(@ModelAttribute("adres")@Valid Adres adres, Errors errors, @ModelAttribute("klantID") Long klantID,
            Model model, Principal principal) {

        if (errors.hasErrors()) {
        		model.addAttribute("adres",adres) 	;
        		model.addAttribute("klant_id", klantID);
            if (adres.getAdresType().equals(AdresType.BEZORGADRES)) {
            
                return "mijnAdres";
            } else {
                return "mijnAdres";
            }
        }

        
        Klant klant = klantRepo.findById(klantID).get();
        adres.setKlant(klant);
        adresRepo.save(adres);
        
        return "redirect:/mijnKlantGegevens";
    }
    
    
    @PostMapping("/factuuradresGegevensForm")
    public String processFactuurAdresGegevens(@ModelAttribute("factuuradres")@Valid Adres factuuradres, Errors factuurerrors,@ModelAttribute("klantID") Long klantID,
            Model model, Principal principal) {
    
        if (factuurerrors.hasErrors()) {
    		model.addAttribute("factuuradres",factuuradres) 	;
    		model.addAttribute("klant_id", klantID);
        if (factuuradres.getAdresType().equals(AdresType.FACTUURADRES)) {
        
            return "mijnAdres";
        } else {
            return "mijnAdres";
        }
    }

        Klant klant = klantRepo.findById(klantID).get();
    factuuradres.setKlant(klant);
    adresRepo.save(factuuradres);
    
        // Nu terug naar de klantgegevens
    
        	return "redirect:/mijnKlantGegevens";
        
    }
    @PostMapping("/bezorgadresgegevensverwijder")
    public String verwijderAdresgegevens(@ModelAttribute("bezorgadresID") Long adresID,
            @ModelAttribute("klantID") Long klantID) {

        if (adresID != 0L) {
            adresRepo.deleteById(adresID);
        }

        // Nu terug naar de klant
        return "redirect:/mijnKlantGegevens";
    }
    
    @PostMapping("/factuuradresgegevensverwijder")
    public String verwijderFactuurAdresgegevens(@ModelAttribute("factuuradresID") Long adresID,
            @ModelAttribute("klantID") Long klantID) {

        if (adresID != 0L) {
            adresRepo.deleteById(adresID);
        }

        // Nu terug naar de klant
        return "redirect:/mijnKlantGegevens";
    }
}
