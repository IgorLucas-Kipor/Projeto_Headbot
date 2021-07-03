package com.igorlucas.controllers;

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
	public ModelAndView listagem() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("listagem");
	    return modelAndView;
	}
	
	@PostMapping("**/listar")
	public ModelAndView listarPosts(@RequestParam("idredator") String idredator ){
		
		Long id = Long.valueOf(idredator);
		
		List<Post> postagens = postService.listarPosts(id);
		
		ModelAndView modelAndView = new ModelAndView("listagem");
		
		modelAndView.addObject("posts", postagens);
		return modelAndView;
	}

}
