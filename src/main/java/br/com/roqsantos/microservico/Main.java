package br.com.roqsantos.microservico;

import static spark.Spark.*;

import br.com.roqsantos.modelo.NumeroArabico;
import br.com.roqsantos.modelo.NumeroRomano;
import br.com.roqsantos.modelo.transformer.Json;

public class Main {

	public synchronized static void main(String[] args) {
		get("/converter/:numero/para-romano", (request, response) -> {
			Integer numero = Integer.valueOf(request.params("numero"));
			
		    return new NumeroArabico(numero).converter();
		}, new Json());
		
		get("/converter/:numero/para-arabico", (request, response) -> {
			String numero = request.params("numero");
			
		    return new NumeroRomano(numero).converter();
		}, new Json());
		
		exception(Exception.class, (exception, request, response) -> {
			response.body(exception.getMessage());
		});
	}
}