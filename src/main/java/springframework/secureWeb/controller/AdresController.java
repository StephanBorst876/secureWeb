package springframework.secureWeb.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import springframework.secureWeb.data.AdresRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Adres;
import springframework.secureWeb.domein.Adres.AdresType;
import springframework.secureWeb.domein.Klant;

/**
 *
 * @author FeniksBV
 */
@Controller
@SessionAttributes({"klantID", "adresID"})
public class AdresController {

    @SuppressWarnings("unused")
    private final KlantRepository klantRepo;

    @SuppressWarnings("unused")
    private final AdresRepository adresRepo;

    @Autowired
    public AdresController(KlantRepository klantRepo, AdresRepository adresRepo) {
        this.klantRepo = klantRepo;
        this.adresRepo = adresRepo;
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
    public String processAdres(@Valid Adres adres, @ModelAttribute("klantID") Long klantID,
            Model model, Errors errors) {

        if (errors.hasErrors()) {
            if (adres.getAdresType().equals(AdresType.BEZORGADRES)) {
                return "redirect:/bezorgadres";
            } else {
                return "redirect:/factuuradres";
            }
        }

        Klant klant = klantRepo.findById(klantID).get();
        adres.setKlant(klant);
        adresRepo.save(adres);

        // Nu terug naar de klant
        return "redirect:/klant/muteer/" + klantID;
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
}
