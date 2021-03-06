package br.com.getjava.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.getjava.votacao.domain.PossivelEscolha;
import br.com.getjava.votacao.domain.Restaurante;

public interface PossivelEscolhaRepository extends JpaRepository<PossivelEscolha, Integer> {

	@Query("select min(p.id) from PossivelEscolha p")
	public Integer buscarMenorEscolha();

	public List<PossivelEscolha> findByRestauranteLadoEsquerdoNotInAndRestauranteLadoDireitoNotIn(List<Restaurante> restauranteEsquerdo,List<Restaurante> restauranteDireito);
}
