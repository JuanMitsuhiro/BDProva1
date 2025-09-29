package br.edu.fateczl.Clinica.controller;

import java.sql.SQLException;
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
public class LoginController {
    
    @Autowired
    private ClienteDao cDao;
    
    @RequestMapping(name = "login", value = "/login", method = RequestMethod.GET)
    public ModelAndView loginGet(ModelMap model) {
        return new ModelAndView("login");
    }
    
    @RequestMapping(name = "login", value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam Map<String, String> params, ModelMap model) {
        
        
        String saida = "";
        String erro = "";
        String rg = params.get("rg");
        String senha = params.get("senha");
        Cliente c = new Cliente();
        String cmd = "";
        
        try {
        	cmd = params.get("botao");
        	
        	c.setRg(rg);
        	c.setSenha(senha);
        	
        	if(cmd.equalsIgnoreCase("Entrar")) {
        		if (cDao.validarLogin(c)) {
        			return new ModelAndView("redirect:/consulta");
        		} else {
        			return new ModelAndView("login");
        		}
        		
        	}
//            
        } catch (SQLException | ClassNotFoundException e) {
        	saida = "";
			erro = e.getMessage();
			if (erro.contains("input string")) {
				erro = "Preencha os campos corretamente";
			}
        } finally {
        	model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("consulta", c);
        }

        return new ModelAndView("login");

    }
}