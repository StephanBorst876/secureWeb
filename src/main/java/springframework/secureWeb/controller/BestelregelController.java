package springframework.secureWeb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import springframework.secureWeb.data.BestelregelRepository;
import springframework.secureWeb.domein.Bestelregel;

@Controller
public class BestelregelController {
	
	@SuppressWarnings("unused")
	private final BestelregelRepository bestrelregelRepo;
	
	@Autowired
	public BestelregelController (BestelregelRepository bestrelregelRepo) {
		this.bestrelregelRepo=bestrelregelRepo;
	}
	
	@GetMapping("bestelling/bestelregels/{bestellingid}")
	public String Bestelregels(Model model) {
		List<Bestelregel> bestelregelList = new ArrayList<>();
		bestrelregelRepo.findAll().forEach(i -> bestelregelList.add(i));

		model.addAttribute("bestelregels", bestelregelList);

		return "bestelregels";
	}
	
}
