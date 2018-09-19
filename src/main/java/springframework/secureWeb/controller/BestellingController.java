package springframework.secureWeb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.data.ArtikelRepository;
import springframework.secureWeb.data.BestellingRepository;
import springframework.secureWeb.data.BestelregelRepository;
import springframework.secureWeb.data.KlantRepository;
import springframework.secureWeb.domein.Account;
import springframework.secureWeb.domein.Artikel;
import springframework.secureWeb.domein.Bestelling;
import springframework.secureWeb.domein.Bestelregel;
import springframework.secureWeb.domein.Klant;

@Controller
@SessionAttributes({ "bestellingId" })
public class BestellingController {

	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;
	private final KlantRepository klantRepo;
	private final BestelregelRepository bestelregelRepo;
	private final ArtikelRepository artikelRepo;
	private final AccountRepository accountRepo;

	@Autowired
	public BestellingController(BestellingRepository bestellingRepo, KlantRepository klantRepo,
			BestelregelRepository bestelregelRepo, ArtikelRepository artikelRepo, AccountRepository accountRepo) {
		this.bestellingRepo = bestellingRepo;
		this.klantRepo = klantRepo;
		this.bestelregelRepo = bestelregelRepo;
		this.artikelRepo = artikelRepo;
		this.accountRepo = accountRepo;
	}

	@GetMapping("/bestelling")
	public String Bestellingen(Model model, Principal principal) {
		List<Bestelling> bestellingList;
		if (!ingelogdeAccountIsKlant(principal)) {
			bestellingList = new ArrayList<>();
			bestellingRepo.findAll().forEach(i -> bestellingList.add(i));

			model.addAttribute("bestellingId");
		} else {
			Klant klant = klantRepo.findKlantByAccount(accountRepo.findByuserNaam(principal.getName()));
			bestellingList = bestellingRepo.findBestellingByKlant(klant.getId());
		}

		model.addAttribute("bestellingen", bestellingList);

		return "bestellingen";
	}

	@GetMapping("/bestelling/nieuw")
	public String nieuweBestelling() {
		return "redirect:/bestelregel/nieuw/";
	}

	@PostMapping("/bestelling/verwijder/{bestellingId}")
	public String bestellingVerwijder(@PathVariable("bestellingId") String bestellingId, Principal principal) {
		long bestellingIdLong = Long.valueOf(bestellingId);
		Bestelling bestelling = bestellingRepo.findById(bestellingIdLong).get();

		if (heeftToegangTotBestelling(principal, bestelling)) {
			List<Bestelregel> bestelregelList = bestelregelRepo.findBestelregelByBestelling(bestellingIdLong);
			for (Bestelregel bestelregel : bestelregelList) {
				wijzigArtikelVoorraad(bestelregel.getArtikel(), bestelregel.getAantal());
			}
			bestellingRepo.deleteById(bestellingIdLong);
			return "redirect:/bestelling";
		}
		return "redirect:/main";
	}

	private void wijzigArtikelVoorraad(Artikel artikel, int aantal) {
		artikel.setVoorraad(artikel.getVoorraad() + aantal);
		artikelRepo.save(artikel);
	}

	private boolean ingelogdeAccountIsKlant(Principal principal) {
		return accountRepo.findByuserNaam(principal.getName()).getRol().equals(Account.Rol.klant);
	}

	private boolean heeftToegangTotBestelling(Principal principal, Bestelling bestelling) {
		// als ingelogde account een klant is, dan is toegang afhankelijk of de
		// bestelling ook op diens naam staat
		if (accountRepo.findByuserNaam(principal.getName()).getRol().equals(Account.Rol.klant)) {
			Klant ingelogdeKlant = klantRepo.findKlantByAccount(accountRepo.findByuserNaam(principal.getName()));
			Klant klantOpWieBestellingStaat = bestelling.getKlant();
			return ingelogdeKlant.equals(klantOpWieBestellingStaat);
		} else
			return true;

	}

}
