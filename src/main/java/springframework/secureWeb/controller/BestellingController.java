package springframework.secureWeb.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import springframework.secureWeb.data.BestellingRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Artikel;
import springframework.secureWeb.domein.Bestelling;
import springframework.secureWeb.domein.Bestelregel;

@Controller
public class BestellingController {

	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;
	private final KlantRepository klantRepo;

	@Autowired
	public BestellingController(BestellingRepository bestellingRepo, KlantRepository klantRepo) {
		this.bestellingRepo = bestellingRepo;
		this.klantRepo=klantRepo;
	}

	@GetMapping("/bestelling")
	public String Bestellingen(Model model) {
		List<Bestelling> bestellingList = new ArrayList<>();
		bestellingRepo.findAll().forEach(i -> bestellingList.add(i));

		model.addAttribute("bestellingen", bestellingList);

		return "bestellingen";
	}
	
	@GetMapping("/bestelling/{klantId}")
	public String Bestelregels(Model model, @PathVariable("klantId") String klantId) {
		try {
			long klantIdLong = Long.valueOf(klantId);
			List <Bestelling> bestellingList = bestellingRepo.findBestellingByKlant(klantIdLong);

			model.addAttribute("bestellingen", bestellingList);

			return "bestellingen";
		} catch (NumberFormatException e) {
			return "redirect:/main";
		}
	}
	
	@RequestMapping("/bestelling/nieuw")
	public String nieuweBestelling() {
		System.out.println("check bestelling nieuw inhoud");
		Bestelling bestelling = new Bestelling();
		bestelling.setKlant(klantRepo.findById(206L).get());
		bestelling.setPrijs(new BigDecimal("0"));
		Bestelling savedBestelling=bestellingRepo.save(bestelling);
		long bestellingIdLong=savedBestelling.getId();
		return "redirect:/bestelregel/nieuw/"+bestellingIdLong;
	}
	
	

}
