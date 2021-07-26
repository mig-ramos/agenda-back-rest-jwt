package br.com.signote.agenda.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Especialidade;
import br.com.signote.agenda.domain.HoraDisponivel;
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.domain.Paciente;
import br.com.signote.agenda.domain.TipoConsulta;
import br.com.signote.agenda.domain.Usuario;
import br.com.signote.agenda.domain.enums.Perfil;
import br.com.signote.agenda.repositories.AgendaRepository;
import br.com.signote.agenda.repositories.EspecialidadeRepository;
import br.com.signote.agenda.repositories.HoraDisponivelRepository;
import br.com.signote.agenda.repositories.MedicoRepository;
import br.com.signote.agenda.repositories.PacienteRepository;
import br.com.signote.agenda.repositories.TipoConsultaRepository;
import br.com.signote.agenda.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EspecialidadeRepository especialidadeRepository;	
	@Autowired
	private HoraDisponivelRepository horaRepository;	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private MedicoRepository medicoRepository; 	
	@Autowired
	private TipoConsultaRepository tipoConsultaRepository;
	@Autowired
	private AgendaRepository agendaRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy");
		
		Especialidade esp1 = new Especialidade(null,"Pediatra", "Descrição do Pediatra");
		Especialidade esp2 = new Especialidade(null,"Clínico Geral", "Descrição do Clínico Geral");
		
		Usuario usu1 = new Usuario(null,"Miguel Arcanjo Ramos", "admin.ephemeris@gmail.com", pe.encode("123"),"123456", sdf1.parse("14/05/1959 09:45"), true);
		usu1.addPerfil(Perfil.ADMIN);
		
		Paciente pac1 = new Paciente(null, "Paciente da Silva","p.ephemeris@gmail.com", pe.encode("123"),"123456",sdf1.parse("10/08/1959 13:40"), true, sdf2.parse("14/05/1959")) ;
		
		Medico med1 = new Medico(null, "Médico da Silva", "m.ephemeris@gmail.com", pe.encode("123"),"123456",sdf1.parse("04/05/1959 10:45"), true, 123456, sdf2.parse("14/05/1959"));
		
		Medico med2 = new Medico(null, "Miguel A Ramos", "mig.ramos10@gmail.com", pe.encode("123"),"789123",sdf1.parse("14/10/1959 12:45"), true, 435465, sdf2.parse("14/05/1959"));
		
		HoraDisponivel hr1 = new HoraDisponivel(null, LocalTime.of(8, 0));
		HoraDisponivel hr2 = new HoraDisponivel(null, LocalTime.of(9, 0));
		HoraDisponivel hr3 = new HoraDisponivel(null, LocalTime.of(10, 0));
		HoraDisponivel hr4 = new HoraDisponivel(null, LocalTime.of(11, 0));
		HoraDisponivel hr5 = new HoraDisponivel(null, LocalTime.of(13, 0));
		HoraDisponivel hr6 = new HoraDisponivel(null, LocalTime.of(14, 0));
		HoraDisponivel hr7 = new HoraDisponivel(null, LocalTime.of(15, 0));
		HoraDisponivel hr8 = new HoraDisponivel(null, LocalTime.of(16, 0));	
		
		Especialidade esp3 = new Especialidade(null,"Otorrino", "Descrição do Otorrino");
		Especialidade esp4 = new Especialidade(null,"Geriatra", "Descrição do Geriatra");
		Especialidade esp5 = new Especialidade(null,"Dermatologista", "Descrição do Dermatologista");
		Especialidade esp6 = new Especialidade(null,"Gastro", "Descrição do Gastro");
		Especialidade esp7 = new Especialidade(null,"Psiquiatra", "Descrição do Psiquiatra");			
		
		usuarioRepository.saveAll(Arrays.asList(usu1));		
		pacienteRepository.save(pac1);
		
		esp1.getMedicos().addAll(Arrays.asList(med1));
		esp2.getMedicos().addAll(Arrays.asList(med1));
		med1.getEspecialidades().addAll(Arrays.asList(esp1, esp2));			
		
		medicoRepository.saveAll(Arrays.asList(med1,med2));		
		especialidadeRepository.saveAll(Arrays.asList(esp1, esp2, esp3, esp4, esp5, esp6, esp7));
		horaRepository.saveAll(Arrays.asList(hr1, hr2, hr3, hr4, hr5, hr6, hr7, hr8));
		
		TipoConsulta tpCon1 = new TipoConsulta(null, "Primeira Consulta");
		TipoConsulta tpCon2 = new TipoConsulta(null, "Retorno Consulta");
	
		tipoConsultaRepository.saveAll(Arrays.asList(tpCon1, tpCon2));
		
		Agenda age1 = new Agenda(null, esp2, med1, LocalDate.of(2021, 07, 24), hr1, tpCon1, pac1, null, null, null);
		Agenda age2 = new Agenda(null, esp1, med1, LocalDate.of(2021, 07, 25), hr2, tpCon2, pac1, null, "Paciente sem anomalia", null);
		
		agendaRepository.saveAll(Arrays.asList(age1,age2));
	}

}
