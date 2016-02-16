package br.com.bluesoft.votacao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bluesoft.votacao.domain.PossivelEscolha;
import br.com.bluesoft.votacao.enumeration.RestauranteEnum;
import br.com.bluesoft.votacao.repository.PossivelEscolhaRepository;

@Controller
@RequestMapping("/")
public class VotacaoController {
	
}
	@Autowired
	private PossivelEscolhaRepository escolhaRepository;
	
	@Autowired
	private DadoMestreRepository dadoMestreRepository;

	private Integer contadorInicial = 1;
	private Integer contadorFinal = 1;
	private Integer contador = 1;
	private boolean inicializou = false;
	private PossivelEscollha escolha;	

	@RequestMapping("/")
	public ModelAndView execute() {
		if(!inicializou){
			contadorInicial = this.escolhaRepository.buscarMenorEscolha();
			if (contadorInicial == 0) {
				dadoMestreRepository.carregar();
				contadorInicial = this.escolhaRepository.buscarMenorEscolha();
			} 
			contadorFinal = contadorInicial + 10;
			contador = contadorInicial;
			inicializou = true;
		}
		// consultar banco de dados
		escolha = escolhaRepository.buscarPossivelEscolhaPorIndice(contador);
		escolha.setPathImagemLadoDireito(escolha.getRestauranteLadoDireito().getPathImagem());
		escolha.setPathImagemLadoEsquerdo(escolha.getRestauranteLadoEsquerdo().getPathImagem());
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("escolha", escolha);
		if (contador <= contadorFinal) {

		return modelAndView;
	}


	@RequestMapping("/votacaoEsquerda")
	public String votacaoEsquerda() {
		if (contador >= contadorFinal) {
			contador = contadorInicial;			
			return "redirect:/formulario";
		}
		return "redirect:/";

	}

	@RequestMapping("/votacaoDireita")
	public String votacaoDireita() {
		if (contador >= contadorFinal) {
			contador = contadorInicial;			
			return "redirect:/formulario";
		}
		return "redirect:/";	
	}
}
