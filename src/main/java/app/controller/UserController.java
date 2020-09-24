package app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.data.User;
import app.repositories.UserRepositorie;
import app.controller.*;

@Controller
public class UserController {
	
	@Autowired
	UserRepositorie ur;
	
	@GetMapping({"/","/login"})
	public String getLoginForm(@ModelAttribute("user") User curUser) {
		TaskController.userConnected=null;
		return "loginForm";
	}
	
	@PostMapping({"/login"})
	public String validateLogin(Model model, @ModelAttribute("user") User curUser, RedirectAttributes ra) {
		
		TaskController.userConnected=null;
		if (curUser!=null) {		
			List<User> users=ur.getUserByMail(curUser.getEmail());
			for (User elt : users) {
				if (elt.getPassword().equals(curUser.getPassword())) {
					TaskController.userConnected=elt;					
					break;
				}
			}
			if (TaskController.userConnected!=null) {
				ra.addFlashAttribute("user", TaskController.userConnected);
				return "redirect:/accueil";
			}
			else
				System.out.println("user pas trouvé");
				model.addAttribute("user", curUser);
				model.addAttribute("msgErr", "Utilisateur n'est pas trouvé");				
				return "loginForm";			
		}
		else
			return "loginForm";					
	}
	
	@GetMapping({"/createuser"})
	public String getUserForm(@ModelAttribute("user") User curUser) {					
		return "createUserForm";		
	}
	
	@PostMapping({"/createuser"})
	public String saveUser(@ModelAttribute("user") @Valid User curUser, BindingResult bindingRes, RedirectAttributes ra) {
		
		if (bindingRes.hasErrors()) {
			String msgErr="";
			for (ObjectError elt : bindingRes.getAllErrors()) {
				msgErr+=elt .toString();
			}
			System.out.println(" les erreurs dans la saisie: " + msgErr);
			return "createUserForm";			
		}
		
		List<User> users=ur.getUserByMail(curUser.getEmail());
		if (users.size()>0) {
			System.out.println("Utilisateur avec cet email exist déjà");
			ra.addFlashAttribute("user", curUser);
			ra.addFlashAttribute("msgErr", "Utilisateur avec cet email exist déjà");				
			return "redirect:/createuser";		
		}
		ur.save(curUser);
		TaskController.userConnected=curUser;
		ra.addFlashAttribute("user", curUser);
		return "redirect:/accueil";
	}
}
