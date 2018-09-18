package springframework.secureWeb.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import springframework.secureWeb.data.AccountRepository;
import springframework.secureWeb.domein.NavBar;


@Controller
public class MyErrorController implements ErrorController{
 

private final AccountRepository accountRepo;

@Autowired
public MyErrorController(AccountRepository accountRepo) {
    this.accountRepo = accountRepo;
}
	
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest httpRequest, Principal principal) {
    
       
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
    
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 403: {
                errorMsg = "Http Error Code: 403. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
      
        
        model.addAttribute("navbar", new NavBar(accountRepo.findByuserNaam(principal.getName()).getRol().toString()));
        model.addAttribute("errorMsg", errorMsg);
        return "error";
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
}