package springframework.secureWeb.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springframework.secureWeb.data.ArtikelRepository;
import springframework.secureWeb.data.BestellingRepository;
import springframework.secureWeb.data.BestelregelRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Artikel;
import springframework.secureWeb.domein.Bestelling;
import springframework.secureWeb.domein.Bestelregel;

@Controller
public class BestellingController {

	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;
	private final KlantRepository klantRepo;
	private final BestelregelRepository bestelregelRepo;
	private final ArtikelRepository artikelRepo;

	@Autowired
	public BestellingController(BestellingRepository bestellingRepo, KlantRepository klantRepo, BestelregelRepository bestelregelRepo, ArtikelRepository artikelRepo) {
		this.bestellingRepo = bestellingRepo;
		this.klantRepo = klantRepo;
		this.bestelregelRepo=bestelregelRepo;
		this.artikelRepo = artikelRepo;
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
			List<Bestelling> bestellingList = bestellingRepo.findBestellingByKlant(klantIdLong);

			model.addAttribute("bestellingen", bestellingList);

			return "bestellingen";
		} catch (NumberFormatException e) {
			return "redirect:/main";
		}
	}

	@RequestMapping("/bestelling/nieuw")
	public String nieuweBestelling() {
		Bestelling bestelling = new Bestelling();
		bestelling.setKlant(klantRepo.findById(206L).get());
		bestelling.setPrijs(new BigDecimal("0"));
		Bestelling savedBestelling = bestellingRepo.save(bestelling);
		long bestellingIdLong = savedBestelling.getId();
		return "redirect:/bestelregel/nieuw/" + bestellingIdLong;
	}
	
	@PostMapping("/bestelling/verwijder/{bestellingId}")
	public String bestellingVerwijder(@PathVariable ("bestellingId") String bestellingId) {
		long bestellingIdLong=Long.valueOf(bestellingId);
		bestellingRepo.deleteById(bestellingIdLong);
		
		List<Bestelregel> bestelregelList = bestelregelRepo.findBestelregelByBestelling(bestellingIdLong);
		for (Bestelregel bestelregel : bestelregelList) {
			wijzigArtikelVoorraad(bestelregel.getArtikel(), bestelregel.getAantal());
		}
		return "redirect:/bestelling";
	}
	
	private void wijzigArtikelVoorraad(Artikel artikel, int aantal) {
		artikel.setVoorraad(artikel.getVoorraad() + aantal);
		artikelRepo.save(artikel);
	}

}
