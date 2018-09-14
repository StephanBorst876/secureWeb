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
import org.springframework.web.bind.annotation.SessionAttributes;

import springframework.secureWeb.data.ArtikelRepository;
import springframework.secureWeb.data.BestellingRepository;
import springframework.secureWeb.data.BestelregelRepository;
import springframework.secureWeb.domein.Artikel;
import springframework.secureWeb.domein.Bestelling;
import springframework.secureWeb.domein.Bestelregel;

@SessionAttributes({ "bestellingIdLong"})
@Controller
public class BestelregelController {

	@SuppressWarnings("unused")
	private final BestelregelRepository bestelregelRepo;
	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;
	@SuppressWarnings("unused")
	private final ArtikelRepository artikelRepo;

	@Autowired
	public BestelregelController(BestelregelRepository bestrelregelRepo, BestellingRepository bestellingRepo,
			ArtikelRepository artikelRepo) {
		this.bestelregelRepo = bestrelregelRepo;
		this.bestellingRepo = bestellingRepo;
		this.artikelRepo = artikelRepo;
	}

	@RequestMapping("bestelregels/{bestellingId}")
	public String Bestelregels(Model model, @PathVariable("bestellingId") String bestellingId) {
		try {
			long bestellingIdLong = Long.valueOf(bestellingId);
			List<Bestelregel> bestelregelList = bestelregelRepo.findBestelregelByBestelling(bestellingIdLong);

			model.addAttribute("bestelregels", bestelregelList);
			model.addAttribute("bestellingId", bestellingId);

			return "bestelregels";
		} catch (NumberFormatException e) {
			return "redirect:/bestellingen";
		}
	}

	@GetMapping("/bestelregel/nieuw/{bestellingId}")
	public String bestelregelNieuw(Model model, @PathVariable("bestellingId") String bestellingId) {
		long bestellingIdLong = Long.valueOf(bestellingId);
		Bestelling bestelling = bestellingRepo.findById(bestellingIdLong).get();

		List<Artikel> artikelList = new ArrayList();
		artikelRepo.findAll().forEach(i -> artikelList.add(i));

		model.addAttribute("artikelen", artikelList);
		model.addAttribute("bestellingIdLong", bestellingIdLong);
		model.addAttribute("bestelregel", new Bestelregel());
		model.addAttribute("artikel", new Artikel());
		model.addAttribute("prijstekst", " prijs: ");
		model.addAttribute("voorraadtekst", " euro voorraad: ");

		return "bestelregelNieuw";
	}

	@PostMapping("/bestelregelForm")
	public String processBestelregel(Bestelregel bestelregel,
			@ModelAttribute("bestellingIdLong") long bestellingIdLong) {
		Bestelling bestelling = bestellingRepo.findById(bestellingIdLong).get();
		bestelregel.setBestelling(bestelling);
		bestelregel
				.setPrijs(bestelregel.getArtikel().getPrijs().multiply(new BigDecimal("" + bestelregel.getAantal())));
		bestelregelRepo.save(bestelregel);
		wijzigBestellingPrijs(bestelling, bestelregel.getPrijs());
		wijzigArtikelVoorraad(bestelregel.getArtikel(), bestelregel.getAantal());
		return "redirect:/bestelregels/" + bestellingIdLong;
	}

	private void wijzigBestellingPrijs(Bestelling bestelling, BigDecimal prijsNieuweBestelregel) {
		bestelling.setPrijs(bestelling.getPrijs().add(prijsNieuweBestelregel));
		bestellingRepo.save(bestelling);
	}

	private void wijzigArtikelVoorraad(Artikel artikel, int aantal) {
		artikel.setVoorraad(artikel.getVoorraad() - aantal);
		artikelRepo.save(artikel);
	}

}
