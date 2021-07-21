package br.com.signote.agenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.signote.agenda.domain.HoraDisponivel;

@Repository
public interface HoraDisponivelRepository extends JpaRepository<HoraDisponivel, Integer> {

}
