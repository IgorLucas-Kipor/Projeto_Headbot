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

/**
 * The PostController class means to control activities related to posts, returning the many models
 * used to deliver information to HTML pages in their many endpoints.
 * Being a controller class, PostController aims to have as little operational logic as possible,
 * the majority of which is delegated to the Service classes.
 * @author Igor Lucas
 *
 */
@Controller
public class PostController {
	
	/**
	 * Injects a PostService in the class, so that it may be responsible for managing
	 * most of the implemented logic.
	 */
	@Autowired
	private PostService postService;

	/**
	 * Set's the page to be accessed when opening the application at the localhost.
	 * @return A string with the name of the page to be accessed.
	 */
	@GetMapping("/")
	public String mainPage() {
	    return "listagem";
	}
	
	/**
	 * Lists all posts made by a given user in a HTML page, at the 'list' endpoint
	 * @param userId The id of the user who created the desired posts, taken from the input field at the HTML form.
	 * @return The posts made by user in the shape of a ModelAndView, to be formatted in the HTML
	 */
	@PostMapping("**/list")
	public ModelAndView listPosts(@RequestParam("userId") String userId ){
		
		List<Post> posts = postService.createList(userId);
		
		ModelAndView modelAndView = new ModelAndView("listagem");
		
		modelAndView.addObject("posts", posts);
		return modelAndView;
	}

}
