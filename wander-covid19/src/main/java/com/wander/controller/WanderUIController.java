package com.wander.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WanderUIController {

	@GetMapping("/")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@GetMapping("/signup")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signup");
		return modelAndView;
	}

	@GetMapping("/covidupdates")
	public ModelAndView covidUpdates() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("covid-data");
		return modelAndView;
	}

	@GetMapping("/forgotPassword")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgot-password");
		return modelAndView;
	}
	
	@GetMapping("/resetPassword")
	public ModelAndView resetPassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("reset-password");
		return modelAndView;
	}

}
