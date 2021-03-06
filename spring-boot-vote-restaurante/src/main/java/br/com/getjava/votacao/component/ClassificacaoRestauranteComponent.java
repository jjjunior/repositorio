package br.com.getjava.votacao.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.com.getjava.votacao.classificacao.CalculadoraDeClassificacao;
import br.com.getjava.votacao.classificacao.ClassificaGanhador;
import br.com.getjava.votacao.classificacao.ClassificaPerdedor;
import br.com.getjava.votacao.classificacao.Classificacao;
import br.com.getjava.votacao.domain.ClassificacaoRestaurante;
import br.com.getjava.votacao.domain.DiferencaClassificacao;
import br.com.getjava.votacao.domain.Restaurante;
import br.com.getjava.votacao.service.ClassificacaoRestauranteService;
import br.com.getjava.votacao.service.DiferencaClassificacaoService;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClassificacaoRestauranteComponent {
	
	private ClassificacaoRestaurante		classificacaoRestauranteDireita;
	
	private ClassificacaoRestaurante		classificacaoRestauranteEsquerda;

	@Autowired
	private ClassificacaoRestauranteService	classificacaoRestauranteService;

	@Autowired
	private DiferencaClassificacaoService	diferencaClassificacaoService;

	private DiferencaClassificacao			diferencaClassificacao;

	public void buscarClassificacaoRestaurantes(Restaurante direito, Restaurante esquerdo) {
		this.classificacaoRestauranteDireita = classificacaoRestauranteService.buscarClassificacaoPorRestaurante(direito);
		this.classificacaoRestauranteEsquerda = classificacaoRestauranteService.buscarClassificacaoPorRestaurante(esquerdo);
	}

	public void buscarDiferencaClassificacao() {
		int diferenca = 0;
		if (classificacaoRestauranteDireita.getClassificacaoAnterior().intValue() >= classificacaoRestauranteEsquerda.getClassificacaoAnterior().intValue()) {
			diferenca = classificacaoRestauranteDireita.getClassificacaoAnterior().intValue() - classificacaoRestauranteEsquerda.getClassificacaoAnterior().intValue();
		} else {
			diferenca = classificacaoRestauranteEsquerda.getClassificacaoAnterior().intValue() - classificacaoRestauranteDireita.getClassificacaoAnterior().intValue();
		}
		this.diferencaClassificacao = buscarDiferencaDeClassificacao(diferenca);
	}
	
	public DiferencaClassificacao buscarDiferencaDeClassificacao(int diferenca){
		return this.diferencaClassificacaoService.buscarClassificacaoPorDiferencaEntreValor(diferenca);
	}
	
	
	public List<ClassificacaoRestaurante> listaClassificacao(){
		return classificacaoRestauranteService.buscarTodasClassificacoes();
	}

	public void calcularClassificacaoRestauranteGanhador(Restaurante restaurante) {		
		Classificacao classificacao = ClassificaGanhador.newIntance();
		CalculadoraDeClassificacao calculadoraDeClassificacao = CalculadoraDeClassificacao.newIntance();

		if (restaurante.getId() == classificacaoRestauranteDireita.getRestaurante().getId()) {
			Integer valorClassificacao = calculadoraDeClassificacao.realizarCalculo(this.diferencaClassificacao, classificacaoRestauranteDireita, classificacao);
			classificacaoRestauranteDireita.setClassificacaoAtual(valorClassificacao);
			classificacaoRestauranteDireita.setClassificacaoAnterior(valorClassificacao);
			classificacaoRestauranteService.incluirClassificacao(classificacaoRestauranteDireita);			
		} else {
			Integer valorClassificacao = calculadoraDeClassificacao.realizarCalculo(this.diferencaClassificacao, classificacaoRestauranteEsquerda, classificacao);
			classificacaoRestauranteEsquerda.setClassificacaoAtual(valorClassificacao);
			classificacaoRestauranteEsquerda.setClassificacaoAnterior(valorClassificacao);
			classificacaoRestauranteService.incluirClassificacao(classificacaoRestauranteEsquerda);			
		}
	}

	public void calcularClassificacaoRestaurantePerdedor(Restaurante restaurante) {
		CalculadoraDeClassificacao calculadoraDeClassificacao = CalculadoraDeClassificacao.newIntance();
		Classificacao classificacao = ClassificaPerdedor.newIntance();

		if (restaurante.getId() == classificacaoRestauranteDireita.getRestaurante().getId()) {
			Integer valorClassificacao = calculadoraDeClassificacao.realizarCalculo(this.diferencaClassificacao, classificacaoRestauranteDireita, classificacao);
			classificacaoRestauranteDireita.setClassificacaoAtual(valorClassificacao);
			classificacaoRestauranteDireita.setClassificacaoAnterior(valorClassificacao);
			classificacaoRestauranteService.incluirClassificacao(classificacaoRestauranteDireita);			
		} else {
			Integer valorClassificacao = calculadoraDeClassificacao.realizarCalculo(this.diferencaClassificacao, classificacaoRestauranteEsquerda, classificacao);
			classificacaoRestauranteEsquerda.setClassificacaoAtual(valorClassificacao);
			classificacaoRestauranteEsquerda.setClassificacaoAnterior(valorClassificacao);
			classificacaoRestauranteService.incluirClassificacao(classificacaoRestauranteEsquerda);			
		}
	}
}
	