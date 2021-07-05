package com.igorlucas.services;

import org.springframework.stereotype.Service;

/**
 * The GeneralService class is responsible for handling all general logic for the API, logic that may be used by
 * many different classes, with no specific destination.
 * The business rules implemented in the project are present here and in the PostService class, letting
 * as few as possible of the operational logic under the care of Post Controller.
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
