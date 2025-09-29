package br.edu.fateczl.Clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {
	private String rg;
	private String nome;
	private String telefone;
	private int especialidade;
	private String nomeEspecialidade;
	private String turno;
}
