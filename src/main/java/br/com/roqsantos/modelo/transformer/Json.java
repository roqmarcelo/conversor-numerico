package br.com.roqsantos.modelo.transformer;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class Json implements ResponseTransformer {
	
	private final Gson gson;
	
	public Json() {
		gson = new Gson();
	}
	
	@Override
	public String render(Object model) throws Exception {
		return gson.toJson(model);
	}
	
}