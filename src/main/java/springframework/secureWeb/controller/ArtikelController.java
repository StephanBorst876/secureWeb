package springframework.secureWeb.controller;

import springframework.secureWeb.data.ArtikelRepository;
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
import springframework.secureWeb.domein.Artikel;

/**
 *
 * @author FeniksBV
 */
@Controller
public class ArtikelController {

    @SuppressWarnings("unused")
    private final ArtikelRepository artikelRepo;

    @Autowired
    public ArtikelController(ArtikelRepository artikelRepo) {
        this.artikelRepo = artikelRepo;
    }

    @GetMapping("/artikelen")
    public String Artikelen(Model model) {
        List<Artikel> artikelList = new ArrayList<>();
        artikelRepo.findAll().forEach(i -> artikelList.add(i));

        model.addAttribute("artikelen", artikelList);

        return "artikelen";
    }

    @RequestMapping(value = "/artikel/muteer/{artikelId}")
    public String ArtikelMuteer(Model model, @PathVariable("artikelId") String artikelId) {
        //
        // Het omzetten van het artikelId doe ik hier, want anders geeft Spring Boot
        // (or Thymeleaf) een Exception wanneer er iets wordt aangeboden als:
        // http://.../artikel/muteer/abc, met abc geen Long format
        //
        try {
            Long id = Long.valueOf(artikelId);
            Optional<Artikel> optionalArt = artikelRepo.findById(id);
            if (optionalArt.isPresent()) {
                Artikel artikel = optionalArt.get();
                if (artikel != null) {
                    model.addAttribute("artikel", artikel);
                    return "artikel";
                }
            }

        } catch  (NumberFormatException ex) {

        }

        return "redirect:/artikelen";

    }

    @GetMapping("/artikel/nieuw")
    public String ArtikelNieuw(Model model) {

        model.addAttribute("artikel", new Artikel());

        return "artikel";
    }

    @PostMapping("/artikelForm")
    public String processArtikel(@Valid Artikel artikel, Errors errors) {
        if (errors.hasErrors()) {
            return "artikel";
        }
        artikelRepo.save(artikel);

        // Nu terug naar de Get op /artikelen om de gehele lijst te tonen
        return "redirect:/artikelen";
    }
}
