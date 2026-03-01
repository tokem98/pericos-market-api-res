package supermercado.servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServidorApplication {

	// Metodo principal que arranca el servidor Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(ServidorApplication.class, args);
		System.out.println("Servidor arrancado en puerto 12345");
	}
}
