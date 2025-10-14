package org.serratec.aula03.exemplo;

import org.springframework.stereotype.Component;

@Component
public class Consulta {

	public Double calcularConsulta(Double valor) {
		return valor = valor + valor * 0.1;
	}
	
}
