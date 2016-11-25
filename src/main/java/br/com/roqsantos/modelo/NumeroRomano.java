package br.com.roqsantos.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class NumeroRomano implements Numero<Integer> {
	
	private final String numero;
	
	private final static Map<String, Integer> mapaRomanosArabicos = new HashMap<String, Integer>();
	
	private final static LoadingCache<String, Integer> cache;

	static {
		mapaRomanosArabicos.put("I", 1);
		mapaRomanosArabicos.put("II", 2);
		mapaRomanosArabicos.put("III", 3);
		mapaRomanosArabicos.put("IV", 4);
		mapaRomanosArabicos.put("V", 5);
		mapaRomanosArabicos.put("VI", 6);
		mapaRomanosArabicos.put("VII", 7);
		mapaRomanosArabicos.put("VIII", 8);
		mapaRomanosArabicos.put("IX", 9);
		mapaRomanosArabicos.put("X", 10);
		mapaRomanosArabicos.put("L", 50);
		mapaRomanosArabicos.put("C", 100);
		mapaRomanosArabicos.put("D", 500);
		mapaRomanosArabicos.put("M", 1000);
		
		cache = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<String, Integer>() {
    		public Integer load(String romano) {
    			return paraArabico(romano);
    		}
    	});
	}
	
	public NumeroRomano(final String numero) {
		if (numero == null || numero.isEmpty()) {
            throw new NumberFormatException("Uma string vazia não define um numeral romano.");
		}

		this.numero = numero.toUpperCase();
	}
	
	@Override
	public Integer converter() throws ExecutionException {
		return cache.get(numero);
	}
	
	private static Integer paraArabico(String romano) {
		Integer arabico = mapaRomanosArabicos.get(romano);

		if (arabico == null) {
			arabico = 0;

			validarNumerosRomanos(romano);
			
			int i = 0;

			while (i < romano.length()) {
				String romanoAtual = new Character(romano.charAt(i)).toString();

				if (!mapaRomanosArabicos.containsKey(romanoAtual)) { 
					throw new NumberFormatException("Os numeros romanos inseridos nao sao validos.");
				}
				
				int numeroAtual = mapaRomanosArabicos.get(romanoAtual);
				
				i++;
				
				if (i == romano.length()) {
					arabico += numeroAtual;
				} else {
					String romanoProximo = new Character(romano.charAt(i)).toString();
					
					int numeroProximo = mapaRomanosArabicos.get(romanoProximo);
					
					if (numeroProximo > numeroAtual) {
						arabico += (numeroProximo - numeroAtual);
						i++;
					} else {
						arabico += numeroAtual;
					}
				}
			}
		}
		
		if (arabico > 3999) {
            throw new NumberFormatException("Numerais romanos devem ter valor 3999 ou menos.");
		}
		
		return arabico;
	}
	
	private static void validarNumerosRomanos(String romano) {
		Pattern pattern = Pattern.compile("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
		Matcher matcher = pattern.matcher(romano);
		
		if (!matcher.matches()) {
			throw new NumberFormatException("Os numeros romanos inseridos nao sao validos.");
		}
	}
}