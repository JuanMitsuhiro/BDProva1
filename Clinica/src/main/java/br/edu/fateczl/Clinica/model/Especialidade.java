package br.edu.fateczl.Clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Especialidade {
	private int codigo;
	private String nome;
}
