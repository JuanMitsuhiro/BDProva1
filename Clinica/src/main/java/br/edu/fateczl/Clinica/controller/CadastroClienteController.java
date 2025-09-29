package br.edu.fateczl.Clinica.controller;

import java.sql.SQLException;
import java.time.LocalDate;
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

import br.edu.fateczl.Clinica.model.Cliente;
import br.edu.fateczl.Clinica.persistence.ClienteDao;

@Controller
public class CadastroClienteController {
	
	@Autowired
	private ClienteDao cDao;
	
	@RequestMapping(name = "cadastro", value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView produtoGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		String rg = params.get("rg");
		
		Cliente c = new Cliente();
		String erro = "";
		List<Cliente> clientes = new ArrayList<>();
		
		try {
			if (acao != null) {
				c.setRg(rg);
				
				if (acao.equalsIgnoreCase("excluir")) {
					cDao.excluir(c);
					clientes = cDao.listar();
					c = null;
				} else {
					c = cDao.buscar(c);
					clientes = null;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("cliente", c);
			model.addAttribute("clientes", clientes);
		}
		
		return new ModelAndView("cadastro");
	}

	@RequestMapping(name = "cadastro", value = "/cadastro", method = RequestMethod.POST)
	public ModelAndView produtoPost(
			@RequestParam Map<String, String> params,
			ModelMap model) {
		String saida = "";
		String erro = "";
		List<Cliente> clientes = new ArrayList<Cliente>();
		Cliente c = new Cliente();
		String cmd = "";
		
		try {
			String rg = params.get("rg");
			String nome = params.get("nome");
			String nascimento = params.get("nascimento");
			String telefone =  params.get("telefone");
			String senha = params.get("senha");
			cmd = params.get("botao");
			
			if (!cmd.equalsIgnoreCase("Listar")) {
				c.setRg((rg));
			}
			if (cmd.equalsIgnoreCase("Cadastrar") || cmd.equalsIgnoreCase("Atualizar")) {
				c.setNome(nome);
				c.setNascimento(LocalDate.parse(nascimento));
				c.setTelefone(telefone);
				c.setSenha(senha);
			}
		
			if (cmd.equalsIgnoreCase("Cadastrar")) {
				saida = cDao.inserir(c);
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = cDao.atualizar(c);
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				saida = cDao.excluir(c);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				c = cDao.buscar(c);
			}
			if (cmd.equalsIgnoreCase("Listar")) {
				clientes = cDao.listar();
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
				clientes = null;
			}
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("cliente", c);
			model.addAttribute("clientes", clientes);
		}

		return new ModelAndView("cadastro");
	}

}
