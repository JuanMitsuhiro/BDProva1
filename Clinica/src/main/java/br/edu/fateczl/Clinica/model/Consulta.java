package br.edu.fateczl.Clinica.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
	private int codigo;
	private String clienteRg;
	private int especialidade;
	private String nomeEspecialidade;
	private LocalDate data;
	private LocalTime hora;
	private String dtConsulta;
	private String tipo;
	private String categoria;
	private int medico;

	
}
