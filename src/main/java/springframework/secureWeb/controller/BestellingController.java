package springframework.secureWeb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import springframework.secureWeb.data.BestellingRepository;
import springframework.secureWeb.domein.Artikel;
import springframework.secureWeb.domein.Bestelling;

@Controller
public class BestellingController {

	@SuppressWarnings("unused")
	private final BestellingRepository bestellingRepo;

	@Autowired
	public BestellingController(BestellingRepository bestellingRepo) {
		this.bestellingRepo = bestellingRepo;
	}

	@GetMapping("/bestellingen")
	public String Bestellingen(Model model) {
		List<Bestelling> bestellingList = new ArrayList<>();
		bestellingRepo.findAll().forEach(i -> bestellingList.add(i));

		model.addAttribute("bestellingen", bestellingList);

		return "bestellingen";
	}
	
	

}
