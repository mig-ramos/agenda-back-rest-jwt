package br.com.signote.agenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.signote.agenda.domain.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

}
