package br.com.roqsantos.modelo;

import java.util.concurrent.ExecutionException;

public interface Numero<T> {

	public T converter() throws ExecutionException;
}