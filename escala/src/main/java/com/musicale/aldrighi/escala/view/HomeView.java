package com.musicale.aldrighi.escala.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeView {

	@GetMapping("/")
	public String home() {
		return "home";
	}

}
