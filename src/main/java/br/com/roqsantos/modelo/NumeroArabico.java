package br.com.roqsantos.modelo;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class NumeroArabico implements Numero<String> {

	private final Integer numero;
	
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
    
	private final static LoadingCache<Integer, String> cache;
    
    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
        
        cache = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<Integer, String>() {
    		public String load(Integer arabico) {
    			return paraRomano(arabico);
    		}
    	});
	}
    
	public NumeroArabico(final Integer numero) {
		if (numero < 1) {
			throw new NumberFormatException("Valor de NumeroArabico deve ser positivo.");
		}
		if (numero > 3999) {
			throw new NumberFormatException("Valor de NumeroArabico deve ser 3999 ou menos.");
		}
		this.numero = numero;
	}

	@Override
	public String converter() throws ExecutionException {
		return cache.get(numero);
	}
	
	private final static String paraRomano(int arabico) {
		int chave = map.floorKey(arabico);

		if (arabico == chave) {
			return map.get(arabico);
		}
		
		return map.get(chave) + paraRomano(arabico - chave);
	}
}