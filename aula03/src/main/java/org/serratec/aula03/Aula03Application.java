package org.serratec.aula03;

import org.serratec.aula03.exemplo.Consulta;
import org.serratec.aula03.exemplo.Exame;
import org.serratec.aula03.exemplo.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aula03Application implements CommandLineRunner {

	@Autowired
	private Pagamento pagamento;
	
	public static void main(String[] args) {
		SpringApplication.run(Aula03Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Total a pagar: " 
		    + pagamento.calcularProcedimento(200.0, 80.0));
	}

}
