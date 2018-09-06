package springframework.secureWeb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import springframework.secureWeb.data.AdresRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Adres;
import springframework.secureWeb.domein.Klant;

/**
 *
 * @author FeniksBV
 */
@Controller
@SessionAttributes({"klantID", "adresID"})
public class KlantController {

    @SuppressWarnings("unused")
    private final KlantRepository klantRepo;

    @SuppressWarnings("unused")
    private final AdresRepository adresRepo;

    @Autowired
    public KlantController(KlantRepository klantRepo, AdresRepository adresRepo) {
        this.klantRepo = klantRepo;
        this.adresRepo = adresRepo;
    }

    @GetMapping("/klanten")
    public String Klanten(Model model) {
        List<Klant> klantList = new ArrayList<>();
        klantRepo.findAll().forEach(i -> klantList.add(i));

        model.addAttribute("klanten", klantList);

        return "klanten";
    }

    @RequestMapping(value = "/klant/muteer/{klantId}")
    public String KlantMuteer(Model model, @PathVariable("klantId") String klantId) {
        //
        // Het omzetten van het klantID doe ik hier, want anders geeft Spring Boot
        // (or Thymeleaf) een Exception wanneer er iets wordt aangeboden als:
        // http://.../klant/muteer/abc, met abc geen Long format
        //
        try {
            Long id = Long.valueOf(klantId);
            Optional<Klant> optionalKlant = klantRepo.findById(id);
            if (optionalKlant.isPresent()) {
                Klant mutKlant = optionalKlant.get();
                if (mutKlant != null) {
                    model.addAttribute("klant", mutKlant);

                    // Zet de sessionAttribute
                    model.addAttribute("klantID", mutKlant.getId());

                    boolean postAdresAdded = false;
                    List<Adres> adresList = adresRepo.findAdresByKlant(id);
                    for (int i = 0; i < adresList.size(); i++) {
                        if (adresList.get(i).getAdresType().equals(Adres.AdresType.POSTADRES)) {
                            postAdresAdded = true;
                            model.addAttribute("postadres", adresList.get(i));

                            // Zet ook de sessionAttribute
                            model.addAttribute("adresID", adresList.get(i).getId());

                        } else if (adresList.get(i).getAdresType().equals(Adres.AdresType.BEZORGADRES)) {
                            model.addAttribute("bezorgadres", adresList.get(i));
                        } else if (adresList.get(i).getAdresType().equals(Adres.AdresType.FACTUURADRES)) {
                            model.addAttribute("factuuradres", adresList.get(i));
                        }
                    }
                    if (!postAdresAdded) {
                        // reset ook de sessionattribute
                        model.addAttribute("adresID", 0L);
                        model.addAttribute("postadres", new Adres(Adres.AdresType.POSTADRES));
                    }

                    return "klant";
                }
            }

        } catch (NumberFormatException ex) {

        }

        return "redirect:/klanten";
    }

    @PostMapping("/klant/verwijder")
    public String verwijderKlant(@SessionAttribute("klantID") Long klantID) {

        klantRepo.deleteById(klantID);

        // Nu terug naar de Get op /klanten om de gehele lijst te tonen
        return "redirect:/klanten";
    }

    @PostMapping("/klant/verwijder/{klantId}")
    public String verwijderKlant(@PathVariable("klantId") String klantId) {

        Long id = Long.valueOf(klantId);
        klantRepo.deleteById(id);

        // Nu terug naar de Get op /klanten om de gehele lijst te tonen
        return "redirect:/klanten";
    }

    @GetMapping("/klant/nieuw")
    public String KlantNieuw(Model model) {

        // SHIT happens||
        // Klant en adres hebben beide een id, dit geeft problemen
        // die met onderstaande code worden opgelost
        Adres postAdres = new Adres(Adres.AdresType.POSTADRES);
        postAdres.setId(0L);

        Klant klant = new Klant();
        klant.setId(0L);
        model.addAttribute("klant", klant);
        model.addAttribute("postadres", postAdres);

        // Zet adresID in sessionAttribute
        model.addAttribute("adresID", 0L);
        return "klantNieuw";
    }

    @PostMapping("/klantForm")
    public String processKlant(Model model, @Valid Klant klant, Errors errors, @Valid Adres postAdres,
            @SessionAttribute("adresID") Long adresID) {

        if (errors.hasErrors()) {
            model.addAttribute("klant", klant);
            model.addAttribute("postadres", postAdres);
            if (klant.getId() == null) {
                return "klantNieuw";
            } else {
                return "klant";
            }
        }

        Klant klantDB = klantRepo.save(klant);

        postAdres.setId(adresID); // Bewaard in sessionAttr
        postAdres.setKlant(klantDB);
        adresRepo.save(postAdres);

        // Nu terug naar de Get op /klanten om de gehele lijst te tonen
        return "redirect:/klanten";

    }
}
