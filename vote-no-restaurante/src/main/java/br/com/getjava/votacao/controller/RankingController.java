package br.com.getjava.votacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RankingController {
	
	@RequestMapping(value ="/ranking" , method = RequestMethod.GET)
	public ModelAndView mostrarRanking() {		
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
}
