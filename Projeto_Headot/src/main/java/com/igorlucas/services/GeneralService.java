package com.igorlucas.services;

import org.springframework.stereotype.Service;

/**
 * A classe GeneralService é responsável por implementar lógicas de uso geral para diversas classes, não estando vinculada
 * a uma operação específica ou outra, mas contendo métodos que podem ser usados por várias classes e entidades diferentes.
 * As regras de negócio gerais implementadas no projeto são trabalhadas aqui e na classe PostService, deixando
 * um mínimo de lógica operacional sob os cuidados de PostController.
 * @author Igor Lucas
 *
 */
@Service
public class GeneralService {
	
	public static boolean isNumeric(String givenString) {
	    try {
	        Long l = Long.valueOf(givenString);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}

}
