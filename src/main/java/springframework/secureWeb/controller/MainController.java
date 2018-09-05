package springframework.secureWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springframework.secureWeb.domein.NavBar;

/**
 *
 * @author FeniksBV
 */
@Controller
public class MainController {

    @GetMapping("/main")
    public String Main(Model model) {

        model.addAttribute("navbar", new NavBar());

        return "main";
    }

}
