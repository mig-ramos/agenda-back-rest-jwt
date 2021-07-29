package br.com.signote.agenda.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.domain.Usuario;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

	@Transactional(readOnly=true)
	Page<Agenda> findByUsuario(Usuario usuario, Pageable pageRequest);
	Page<Agenda> findByMedico(Medico medico, Pageable pageRequest);
}
