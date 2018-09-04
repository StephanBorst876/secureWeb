package springframework.secureWeb.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
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

    @GetMapping("/bezorgadres")
    public String BezorgAdres(@SessionAttribute("klantID") Klant klant,
            @SessionAttribute("adresID") Adres sessionAdres, Model model) {

        Klant modelKlant = klantRepo.findById(klant.getId()).get();
        model.addAttribute("klant", modelKlant);

        Adres adres = findAdres(AdresType.BEZORGADRES, klant.getId());
        model.addAttribute("adres", adres);

        sessionAdres.setId(adres.getId());

        return "adres";
    }

    @GetMapping("/factuuradres")
    public String FactuurAdres(@SessionAttribute("klantID") Klant klant,
            @SessionAttribute("adresID") Adres sessionAdres, Model model) {

        Klant modelKlant = klantRepo.findById(klant.getId()).get();
        model.addAttribute("klant", modelKlant);

        Adres adres = findAdres(AdresType.FACTUURADRES, klant.getId());
        model.addAttribute("adres", adres);

        sessionAdres.setId(adres.getId());

        return "adres";
    }

    @PostMapping("/adresForm")
    public String processAdres(@Valid Adres adres, @SessionAttribute("klantID") Klant klant,
            Model model, Errors errors) {

        if (errors.hasErrors()) {
            if (adres.getAdresType().equals(AdresType.BEZORGADRES)) {
                return "redirect:/bezorgadres";
            } else {
                return "redirect:/factuuradres";
            }
        }

        adres.setKlant(klant);
        adresRepo.save(adres);

        // Nu terug naar de klant
        return "redirect:/klant/muteer/" + klant.getId();
    }

    @PostMapping("/adres/verwijder")
    public String verwijderAdres(@SessionAttribute("adresID") Adres adres,
            @SessionAttribute("klantID") Klant klant) {

        if (adres.getId() != null) {
            adresRepo.deleteById(adres.getId());
        }

        // Nu terug naar de klant
        return "redirect:/klant/muteer/" + klant.getId();
    }

    protected Adres findAdres(AdresType adresType, Long klantId) {

        List<Adres> adresList = adresRepo.findAdresByKlant(klantId);
        for (Adres a : adresList) {
            if (a.getAdresType().equals(adresType)) {
                return a;
            }
        }
        Adres adres = new Adres();
        adres.setAdresType(adresType);
        return adres;
    }
}
