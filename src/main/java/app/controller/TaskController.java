package app.controller;

import java.util.Date;

import javax.validation.Valid;

//import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.data.Task;
import app.data.User;
import app.repositories.TaskRepositorie;

@Controller
public class TaskController {
	public static User userConnected = null;

	@Autowired
	TaskRepositorie tr;

	@GetMapping({ "/accueil", "/recherche" })
	public String accueilForm(Model model, RedirectAttributes ra) {

		if (userConnected == null) {
			return "redirect:/login";
		} else {
			userConnected.setLstTask(tr.getTasksByUser(userConnected.getIdUser()));
			model.addAttribute("userConnected", userConnected);
			return "accueil";
		}
	}

	@PostMapping(value = "/recherche")
	public String recherche(Model model, @RequestParam(name = "mc") String mc) {

		model.addAttribute("mc", mc);

		if (userConnected == null) {
			return "login";
		} else {
			if (mc.length() == 0 || mc.equals(null))
				userConnected.setLstTask(tr.getTasksByUser(userConnected.getIdUser()));
			else
				userConnected.setLstTask(tr.getTasksByKeyWord(userConnected.getIdUser(), "%" + mc + "%"));

			model.addAttribute("userConnected", userConnected);
			return "accueil";
		}
	}

	// ------------------------

	@GetMapping({ "/addtask" })
	public String createTaskForm(@ModelAttribute("task") Task newTask) {
		newTask.setDate(new Date());//System.currentTimeMillis()
		return "editTaskForm";
	}

	@PostMapping({ "/savetask" })
	public String saveTask(@ModelAttribute("task") @Valid Task newTask, BindingResult bindingRes,
			RedirectAttributes ra) {
		if (userConnected != null) {

			ra.addFlashAttribute("user", userConnected);

			if (newTask != null) {
				if (bindingRes.hasErrors()) {
					String msgErr = "";
					for (ObjectError elt : bindingRes.getAllErrors()) {
						msgErr += elt.toString();
					}
					System.out.println(" les erreurs dans la saisie: " + msgErr);
					ra.addFlashAttribute("task", newTask);
					ra.addFlashAttribute("msgErr", "Les erreurs dans la saisie");
					return "redirect:/addtask";
				}

				newTask.setUser(userConnected);

				tr.save(newTask);
			}
		}

		return "redirect:/accueil";
	}

	// ------------

	@GetMapping({ "/deletetask" })
	public String deleteTask(Model model, @RequestParam(name = "idTask") Long idTask, RedirectAttributes ra) {
		if (idTask != null) {
			tr.deleteById(idTask);
		}
		ra.addFlashAttribute("user", userConnected);
		return "redirect:/accueil";
	}

	@GetMapping({ "/edittask" })
	public String editTask(Model model, @RequestParam(name = "idTask") Long idTask, RedirectAttributes ra) {

		if (idTask != null) {
			Task taskToEdit = tr.getOne(idTask);
			if (taskToEdit != null) {
				model.addAttribute("task", taskToEdit);
				return "editTaskForm";
			} else {
				ra.addFlashAttribute("user", userConnected);
				return "redirect:/accueil";
			}
		} else {
			ra.addFlashAttribute("user", userConnected);
			return "redirect:/accueil";
		}

	}

}
