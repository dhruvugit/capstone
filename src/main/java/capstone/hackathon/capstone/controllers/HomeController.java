package capstone.hackathon.capstone.controllers;

import org.springframework.stereotype.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/home")
public class HomeController {

	@GetMapping("/greet")
	public String processGreet(Model model) {
		String greetingMessage = "Hello Everyone, Welcome to Incedo!!!";
		model.addAttribute("greeting", greetingMessage);
		return "welcomePage";
	}
}

