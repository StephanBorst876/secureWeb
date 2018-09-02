package springframework.secureWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author FeniksBV
 */
@Controller
public class MainController {
 
    @GetMapping("/main")
    public String Main() {
        return "main";
    }

}
