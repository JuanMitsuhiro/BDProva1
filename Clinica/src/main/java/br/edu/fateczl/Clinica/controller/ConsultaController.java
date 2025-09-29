package br.edu.fateczl.Clinica.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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

import br.edu.fateczl.Clinica.model.Consulta;
import br.edu.fateczl.Clinica.model.Especialidade;
import br.edu.fateczl.Clinica.model.Medico;
import br.edu.fateczl.Clinica.persistence.ConsultaDao;
import br.edu.fateczl.Clinica.persistence.EspecialidadeDao;
import br.edu.fateczl.Clinica.persistence.MedicoDao;



@Controller
public class ConsultaController {

	@Autowired
	private ConsultaDao cDao;
	
	@Autowired
	private EspecialidadeDao eDao;
	
	@Autowired
	private MedicoDao mDao;
	
	@RequestMapping(name = "consulta", value = "/consulta", method = RequestMethod.GET)
	public ModelAndView consultaGet(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String acao = params.get("acao");
		String codigo = params.get("codigo");
		
		Consulta c = new Consulta();
		String erro = "";
		List<Consulta> consultas = new ArrayList<>();
		List<Especialidade> especialidades = new ArrayList<>();
		List<Medico> medicos = new ArrayList<>();
		
		try {
			especialidades = eDao.listar();
			medicos = mDao.listar();
			if (acao != null) {
				c.setCodigo(Integer.parseInt(codigo));
				
				if (acao.equalsIgnoreCase("excluir")) {
					cDao.excluir(c);
					consultas = cDao.listar();
					c = null;
				} else {
					c = cDao.buscar(c);
					consultas = null;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("consulta", c);
			model.addAttribute("consultas", consultas);
			model.addAttribute("especialidades", especialidades);
			model.addAttribute("medicos", medicos);
		}
		return new ModelAndView("consulta");
	}
	
	@RequestMapping(name = "consulta", value = "/consulta", method = RequestMethod.POST)
	public ModelAndView consultaPost(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String saida = "";
		String erro = "";
		List<Especialidade> especialidades = new ArrayList<>();
		List<Medico> medicos = new ArrayList<>();
		List<Consulta> consultas = new ArrayList<Consulta>();
		Consulta c = new Consulta();
		String cmd = "";
		
		try {
			String codigo = params.get("codigo");
			String cliente = params.get("clienteRg");
			String especialidade = params.get("especialidade");
			String data = params.get("data");
			String hora = params.get("hora");
			String tipo =  params.get("tipo");
			String categoria =  params.get("categoria");
			cmd = params.get("botao");
			
			if (!cmd.equalsIgnoreCase("Listar") && !cmd.equalsIgnoreCase("Agendar")) {
				c.setCodigo(Integer.parseInt(codigo));
			}
			if (cmd.equalsIgnoreCase("Agendar") || cmd.equalsIgnoreCase("Atualizar")) {
				c.setClienteRg(cliente);
				c.setEspecialidade(Integer.parseInt(especialidade));
				c.setData(LocalDate.parse(data));
				c.setHora(LocalTime.parse(hora));
				c.setTipo(tipo);
				c.setCategoria(categoria);
			}
		
			if (cmd.equalsIgnoreCase("Agendar")) {
				saida = cDao.inserir(c);
				model.addAttribute("consulta", c);
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = cDao.atualizar(c);
			}
			if (cmd.equalsIgnoreCase("Cancelar")) {
				saida = cDao.excluir(c);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				c = cDao.buscar(c);
			}
			if (cmd.equalsIgnoreCase("Listar")) {
				consultas = cDao.listar();
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			saida = "";
			erro = e.getMessage();
			if (erro.contains("input string")) {
				erro = "Preencha os campos corretamente";
			}
		} finally {
			if (!cmd.equalsIgnoreCase("Buscar")) {
				c = null;
			}
			if (!cmd.equalsIgnoreCase("Listar")) {
				consultas = null;
			}
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("consulta", c);
			model.addAttribute("especialidades", especialidades);
			model.addAttribute("consultas", consultas);
		}

		return new ModelAndView("consulta");
	}
}
