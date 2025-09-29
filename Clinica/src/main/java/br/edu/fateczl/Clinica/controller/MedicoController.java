package br.edu.fateczl.Clinica.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.Clinica.model.Especialidade;
import br.edu.fateczl.Clinica.model.Medico;
import br.edu.fateczl.Clinica.persistence.EspecialidadeDao;
import br.edu.fateczl.Clinica.persistence.MedicoDao;


@Controller
public class MedicoController {

	@Autowired
	private MedicoDao mDao;
	
	@Autowired
	private EspecialidadeDao eDao;
	
	@RequestMapping(name = "medico", value = "/medico", method = RequestMethod.GET)
	public ModelAndView medicoGet(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String acao = params.get("acao");
		String rg = params.get("rg");
		
		Medico m = new Medico();
		String erro = "";
		List<Medico> medicos = new ArrayList<>();
		List<Especialidade> especialidades = new ArrayList<>();
		
		try {
			especialidades = eDao.listar();
			
			if (acao != null) {
				m.setRg(rg);
				
				if (acao.equalsIgnoreCase("excluir")) {
					mDao.excluir(m);
					medicos = mDao.listar();
					m = null;
				} else {
					m = mDao.buscar(m);
					medicos = null;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("medico", m);
			model.addAttribute("medicos", medicos);
			model.addAttribute("especialidades", especialidades);
		}
		return new ModelAndView("medico");
	}
	
	@RequestMapping(name = "medico", value = "/medico", method = RequestMethod.POST)
	public ModelAndView medicoPost(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String saida = "";
		String erro = "";
		List<Especialidade> especialidades = new ArrayList<>();
		List<Medico> medicos = new ArrayList<Medico>();
		Medico m = new Medico();
		String cmd = "";
		
		try {
			especialidades = eDao.listar(); 
			
			String rg = params.get("rg");
			String nome = params.get("nome");
			String telefone = params.get("telefone");
			String especialidade = params.get("especialidade");
			String turno = params.get("turno");
			
			cmd = params.get("botao");
			
			
			
			if (!cmd.equalsIgnoreCase("Listar")) {
				m.setRg(rg);
			}
			
			if (cmd.equalsIgnoreCase("Inserir") || cmd.equalsIgnoreCase("Atualizar")) {
				m.setNome(nome);
				m.setTelefone(telefone);
				m.setEspecialidade(Integer.parseInt(especialidade));
				m.setTurno(turno);
			}
		
			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = mDao.inserir(m);
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = mDao.atualizar(m);
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				saida = mDao.excluir(m);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				m = mDao.buscar(m);
			}
			if (cmd.equalsIgnoreCase("Listar")) {
				medicos = mDao.listar();
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			saida = "";
			erro = e.getMessage();
			if (erro.contains("input string")) {
				erro = "Preencha os campos corretamente";
			}
		} finally {
			if (!cmd.equalsIgnoreCase("Buscar")) {
				m = null;
			}
			if (!cmd.equalsIgnoreCase("Listar")) {
				medicos = null;
			}

			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("medico", m);
			model.addAttribute("especialidades", especialidades);
			model.addAttribute("medicos", medicos);
		}

		return new ModelAndView("medico");
	}
}
