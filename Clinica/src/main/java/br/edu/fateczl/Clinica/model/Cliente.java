package br.edu.fateczl.Clinica.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	private String rg;
	private String nome;
	private LocalDate nascimento;
	private String dtNasc;
	private String telefone;
	private String senha;
}
