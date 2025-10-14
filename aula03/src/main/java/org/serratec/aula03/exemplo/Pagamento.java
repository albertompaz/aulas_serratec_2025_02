package org.serratec.aula03.exemplo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Pagamento {

	@Autowired
	private Consulta consulta;
	
	@Autowired
	private Exame exame;
	
	/*
	public Pagamento(Consulta consulta, Exame exame) {
		this.consulta = consulta;
		this.exame = exame;
	}
	*/
	
	public Double calcularProcedimento(Double valorConsulta, Double valorExame) {
		valorConsulta = consulta.calcularConsulta(valorConsulta);
		valorExame = exame.calcularExame(valorExame);
		return valorConsulta + valorExame;
	}
	
}
