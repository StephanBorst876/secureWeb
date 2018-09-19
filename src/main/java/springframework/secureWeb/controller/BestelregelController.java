package springframework.secureWeb.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttribute;
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

@SessionAttributes({ "bestellingIdLong", "artikelen", "bestelling", "klanten", "bestellingId", "teMuterenBestelregel",
		"bijbehorendeBestellingId" })
@Controller
public class BestelregelController {

	@SuppressWarnings("unused")
	private final BestelregelRepository bestelregelRepo;
	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;
	@SuppressWarnings("unused")
	private final ArtikelRepository artikelRepo;
	@SuppressWarnings("unused")
	private final KlantRepository klantRepo;
	@SuppressWarnings("unused")
	private final AccountRepository accountRepo;

	@Autowired
	public BestelregelController(BestelregelRepository bestrelregelRepo, BestellingRepository bestellingRepo,
			ArtikelRepository artikelRepo, KlantRepository klantRepo, AccountRepository accountRepo) {
		this.bestelregelRepo = bestrelregelRepo;
		this.bestellingRepo = bestellingRepo;
		this.artikelRepo = artikelRepo;
		this.klantRepo = klantRepo;
		this.accountRepo = accountRepo;
	}

	@GetMapping("bestelregels/{bestellingId}")
	public String Bestelregels(Model model, @PathVariable("bestellingId") String bestellingId, Principal principal) {
		try {
			long bestellingIdLong = Long.valueOf(bestellingId);
			List<Bestelregel> bestelregelList = bestelregelRepo.findBestelregelByBestelling(bestellingIdLong);

			Bestelling bestelling = bestellingRepo.findById(bestellingIdLong).get();
			if (heeftToegangTotBestelling(principal, bestelling)) {
				model.addAttribute("bestelregels", bestelregelList);
				model.addAttribute("bestellingId", bestellingId);
				return "bestelregels";
			} else {
				return "redirect:/bestelling";
			}
		}

		catch (NumberFormatException e) {
			return "redirect:/bestelling";
		}
	}

	@GetMapping("/bestelregel/nieuw/{bestellingId}")
	public String bestelregelNieuw(Model model, @PathVariable("bestellingId") String bestellingId,
			Principal principal) {
		try {
			long bestellingIdLong = Long.valueOf(bestellingId);
			Bestelling bestelling = bestellingRepo.findById(bestellingIdLong).get();

			List<Klant> klantList = new ArrayList<>();
			klantRepo.findAll().forEach(i -> klantList.add(i));

			List<Artikel> artikelList = new ArrayList();
			artikelRepo.findAll().forEach(i -> artikelList.add(i));

			if (heeftToegangTotBestelling(principal, bestelling)) {

				model.addAttribute("klanten", klantList);
				model.addAttribute("artikelen", artikelList);
				model.addAttribute("bestellingIdLong", bestellingIdLong);
				model.addAttribute("bestelregel", new Bestelregel());
				model.addAttribute("artikel", new Artikel());
				model.addAttribute("bestelling", bestelling);

				return "bestelregelNieuw";
			}
		} catch (NumberFormatException e) {

		}
		return "redirect:/bestelling";
	}

	@GetMapping("/bestelregel/nieuw")
	public String bestelregelNieuw(Model model, Principal principal) {
		if (!accountRepo.findByuserNaam(principal.getName()).getRol().equals(Account.Rol.klant)) {
			model.addAttribute("klantOptieToevoegen", 1);
		}
		List<Klant> klantList = new ArrayList<>();
		klantRepo.findAll().forEach(i -> klantList.add(i));

		List<Artikel> artikelList = new ArrayList();
		artikelRepo.findAll().forEach(i -> artikelList.add(i));

		model.addAttribute("klanten", klantList);
		model.addAttribute("artikelen", artikelList);
		model.addAttribute("bestelregel", new Bestelregel());
		model.addAttribute("artikel", new Artikel());
		model.addAttribute("bestellingIdLong", 0L);
		model.addAttribute("bestelling", new Bestelling());
		
		return "bestelregelNieuw";
	}

	@PostMapping("/bestelregelForm")
	public String processBestelregel(@Valid Bestelregel bestelregel, Errors bestelregelErrors,
			@ModelAttribute("bestellingIdLong") Long bestellingIdLong, Long klant, Principal principal, Model model) {
		Bestelling bestelling;
		//controleer of er geen fouten in formulier staan
		if (bestelregelErrors.hasFieldErrors()) { //kan in principe met voorraad worden samengevoegd in OR-constructie?
			//als er fouten zijn en de bestelling wel al bestaat
			if(bestellingIdLong==0L) {
				return "redirect:bestelregel/nieuw";
			}
			//als er fouten zijn en de bestelling wel al bestaat
			else {
				return "redirect:bestelregel/nieuw/"+bestellingIdLong;
			}
		}
		//controleer of artikelvoorraad voldoet
		if (!checkOfArtikelVoorraadVoldoet(bestelregel.getArtikel(), bestelregel.getAantal())) {
			//als artikelvoorraad niet voldoet en het ging om een nog niet bestaande bestelling
			if(bestellingIdLong==0L) {
				return "redirect:bestelregel/nieuw";
			}
			//als artikelvoorraad niet voldoet en de bestelling wel al bestaat
			else {
				return "redirect:bestelregel/nieuw/"+bestellingIdLong;
			}
		}
		if (bestellingIdLong == 0) {
			bestelling = new Bestelling();
			bestelling.setPrijs(new BigDecimal("0"));
			if (klant == null) {
				Klant ingelogdeKlant = klantRepo.findKlantByAccount(accountRepo.findByuserNaam(principal.getName()));
				bestelling.setKlant(ingelogdeKlant);
			} else {
				bestelling.setKlant(klantRepo.findById(klant).get());
			}
			bestelregel.setBestelling(bestelling);
		} else {
			bestelling = bestellingRepo.findById(bestellingIdLong).get();
			bestelregel.setBestelling(bestelling);
		}
		bestelregel
				.setPrijs(bestelregel.getArtikel().getPrijs().multiply(new BigDecimal("" + bestelregel.getAantal())));
		wijzigBestellingPrijs(bestelling, bestelregel.getPrijs());
		wijzigArtikelVoorraad(bestelregel.getArtikel(), bestelregel.getAantal());
		bestelregelRepo.save(bestelregel);
		return "redirect:bestelregels/" + bestelregel.getBestelling().getId();
	}

	@GetMapping("bestelregel/muteer/{bestelregelId}")
	public String BestelregelMuteer(Model model, @PathVariable("bestelregelId") String bestelregelId,
			Principal principal) {
		try {
			Long id = Long.valueOf(bestelregelId);
			Bestelregel bestelregel = bestelregelRepo.findById(id).get();

			if (heeftToegangTotBestelling(principal, bestelregel.getBestelling())) {

				model.addAttribute("teMuterenBestelregel", bestelregel);
				model.addAttribute("artikel", new Artikel());

				List<Artikel> artikelList = new ArrayList();
				artikelRepo.findAll().forEach(i -> artikelList.add(i));
				model.addAttribute("artikelen", artikelList);
				model.addAttribute("bijbehorendeBestellingId", bestelregel.getBestelling().getId());

				return "bestelregelMuteer";
			}
		} catch (NumberFormatException ex) {
			return "redirect:/main";
		}
		return "redirect:/main";
	}

	@PostMapping("/bestelregel/verwijder/{bestelregelId}")
	public String bestellingVerwijder(@PathVariable("bestelregelId") String bestelregelId, Principal principal) {
		long bestelregelIdLong = Long.valueOf(bestelregelId);
		Bestelregel bestelregel = bestelregelRepo.findById(bestelregelIdLong).get();
		Bestelling bestelling = bestelregel.getBestelling();

		if (heeftToegangTotBestelling(principal, bestelling)) {
				// artikelvoorraad verhogen met verwijderd aantal artikelen
				wijzigArtikelVoorraad(bestelregel.getArtikel(), -bestelregel.getAantal());
				// prijs bestelling verlagen met prijs te verwijderen bestelregel
				wijzigBestellingPrijs(bestelling, bestelregel.getPrijs().multiply(new BigDecimal("-1")));
				bestellingRepo.save(bestelling);
				bestelregelRepo.deleteById(bestelregelIdLong);
				return "redirect:/bestelling";
			}
			
		return "redirect:/main";
	}

	@PostMapping("/bestelregelMuteerForm")
	public String processBestelregelMuteer(Model model, @Valid Bestelregel bestelregel, Errors bestelregelErrors,
			@SessionAttribute("bijbehorendeBestellingId") Long bestellingId,
			@SessionAttribute("teMuterenBestelregel") Bestelregel oudeBestelregel) {
		if(bestelregelErrors.hasErrors()) {
			System.out.println("er zijn errors");
			return "bestelregelMuteer";
		}
		
		Bestelling bestelling = bestellingRepo.findById(bestellingId).get();
		bestelregel.setBestelling(bestelling);

		bestelregel
				.setPrijs(bestelregel.getArtikel().getPrijs().multiply(new BigDecimal("" + bestelregel.getAantal())));
		if(checkOfArtikelVoorraadVoldoet(bestelregel.getArtikel(), bestelregel.getAantal())) {

		wijzigBestellingPrijs(bestelling, oudeBestelregel.getPrijs().multiply(new BigDecimal("-1")));
		wijzigBestellingPrijs(bestelling, bestelregel.getPrijs());
		wijzigArtikelVoorraad(oudeBestelregel.getArtikel(), oudeBestelregel.getAantal() * -1);
		wijzigArtikelVoorraad(bestelregel.getArtikel(), bestelregel.getAantal());

		bestelregelRepo.save(bestelregel);
		return "redirect:bestelregels/" + bestelregel.getBestelling().getId();
		}
		else {
			//terug naar bestelregel muteren, aangezien aantal niet matcht met voorraad
			return "redirect:bestelregel/muteer/"+bestelregel.getBestelling().getId();
		}
		
	
	}

	private void wijzigBestellingPrijs(Bestelling bestelling, BigDecimal prijsNieuweBestelregel) {
		bestelling.setPrijs(bestelling.getPrijs().add(prijsNieuweBestelregel));
		bestellingRepo.save(bestelling);
	}

	private void wijzigArtikelVoorraad(Artikel artikel, int aantal) {
		artikel.setVoorraad(artikel.getVoorraad() - aantal);
		artikelRepo.save(artikel);
	}

	private boolean checkOfArtikelVoorraadVoldoet(Artikel artikel, int aantal) {
		if (aantal > artikel.getVoorraad()) {
			return false;
		} else
			return true;
	}

	private boolean heeftToegangTotBestelling(Principal principal, Bestelling bestelling) {
		// als ingelogde account een klant is, dan is toegang afhankelijk of de
		// bestelling ook op diens naam staat
		if (accountRepo.findByuserNaam(principal.getName()).getRol().equals(Account.Rol.klant)) {
			Klant ingelogdeKlant = klantRepo.findKlantByAccount(accountRepo.findByuserNaam(principal.getName()));
			Klant klantOpWieBestellingStaat = bestelling.getKlant();
			return ingelogdeKlant.equals(klantOpWieBestellingStaat);
		} else
			//andere rollen dan klanten hebben altijd toegang tot de bestelling
			return true;

	}

}
