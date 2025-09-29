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
import br.edu.fateczl.Clinica.persistence.EspecialidadeDao;

@Controller
public class EspecialidadeController {
	
	@Autowired
	private EspecialidadeDao eDao;
	
	@RequestMapping(name = "especialidade", value = "/especialidade", method = RequestMethod.GET)
	public ModelAndView produtoGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		String codigo = params.get("codigo");
		
		Especialidade esp = new Especialidade();
		String erro = "";
		List<Especialidade> especialidades = new ArrayList<>();
		
		try {
			if (acao != null) {
				esp.setCodigo(Integer.parseInt(codigo));
				
				if (acao.equalsIgnoreCase("excluir")) {
					eDao.excluir(esp);
					especialidades = eDao.listar();
					esp = null;
				} else {
					esp = eDao.buscar(esp);
					especialidades = null;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("especialidade", esp);
			model.addAttribute("especialidades", especialidades);
			
		}
		
		return new ModelAndView("especialidade");
	}

	@RequestMapping(name = "especialidade", value = "/especialidade", method = RequestMethod.POST)
	public ModelAndView produtoPost(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String saida = "";
		String erro = "";
		List<Especialidade> especialidades = new ArrayList<Especialidade>();
		Especialidade esp = new Especialidade();
		String cmd = "";
		
		try {
			String codigo = params.get("codigo");
			String nome = params.get("nome");
			cmd = params.get("botao");
			
			if (!cmd.equalsIgnoreCase("Listar")) {
				esp.setCodigo(Integer.parseInt(codigo));
			}
			if (cmd.equalsIgnoreCase("Inserir") || cmd.equalsIgnoreCase("Atualizar")) {
				esp.setNome(nome);

			}
		
			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = eDao.inserir(esp);
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = eDao.atualizar(esp);
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				saida = eDao.excluir(esp);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				esp = eDao.buscar(esp);
			}
			if (cmd.equalsIgnoreCase("Listar")) {
				especialidades = eDao.listar();
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			saida = "";
			erro = e.getMessage();
			if (erro.contains("input string")) {
				erro = "Preencha os campos corretamente";
			}
		} finally {
			if (!cmd.equalsIgnoreCase("Buscar")) {
				esp = null;
			}
			if (!cmd.equalsIgnoreCase("Listar")) {
				especialidades = null;
			}
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("especialidade", esp);
			model.addAttribute("especialidades", especialidades);
		}
		
		return new ModelAndView("especialidade");
	}

}
