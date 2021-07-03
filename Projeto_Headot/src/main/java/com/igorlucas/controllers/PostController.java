package com.igorlucas.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.igorlucas.entities.Post;
import com.igorlucas.services.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;

	@GetMapping("/")
	public String listagem() {
	    return "listagem";
	}
	
	@PostMapping("**/listar")
	public ModelAndView listarPosts(@RequestParam("idredator") String idRedator ){
		
		boolean validValue = isNumeric(idRedator);
		
		List<Post> postagens = new ArrayList<>();
		
		if (validValue == true) {
			Long id = Long.valueOf(idRedator);
			postagens = postService.listarPosts(id);
		} else {
			postagens.add(postService.mensagemErro(postagens, "Valor inválido.", "O id inserido é inválido."
					+ " Por favor, tente inserir outro valor."));
		}
		
		ModelAndView modelAndView = new ModelAndView("listagem");
		
		modelAndView.addObject("posts", postagens);
		return modelAndView;
	}
	
	
	//-----------------------Métodos Auxiliares------------------------
	
	public static boolean isNumeric(String givenString) {
	    if (givenString == null) {
	        return false;
	    }
	    try {
	        Long l = Long.valueOf(givenString);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}

}
