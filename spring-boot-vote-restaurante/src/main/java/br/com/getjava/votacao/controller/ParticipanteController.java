package br.com.getjava.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.getjava.votacao.component.RestauranteComponent;
import br.com.getjava.votacao.domain.Usuario;
import br.com.getjava.votacao.service.UsuarioService;
import br.com.getjava.votacao.validation.UsuarioValidation;

@Controller
public class ParticipanteController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RestauranteComponent restauranteComponent;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
	}

	@RequestMapping(value = "/cadastroParticipante", method = RequestMethod.GET)
	public ModelAndView form(Usuario usuario) {
		// consultar banco de dados
		ModelAndView modelAndView = new ModelAndView("cadastro_participante");
		return modelAndView;
	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public ModelAndView cadastrarUsuario(@Validated Usuario usuario, BindingResult result) {				
		if (result.hasErrors()) {
			return form(usuario);
		}
		usuarioService.incluirUsuario(usuario);		
		
		ModelAndView modelAndView = new ModelAndView("lista_ranking");
		
		modelAndView.addObject("rankingParticipante", restauranteComponent.getClassificacaoParticipante());
		
		modelAndView.addObject("rankingGlobal", restauranteComponent.getClassificacaoGlobal());		
		
		return modelAndView;
	}
}
