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
 * A classe PostController tem como interesse fazer o controle das atividades relativas ao post, ou seja,
 * retornar os diversos modelos que são usados para a distribuição das informações nas páginas HTML em seus diversos endpoints.
 * Sendo uma classe controladora, PostController inclui um mínimo de lógica operacional, sendo a maior parte dessa
 * delegada às classes de Service.
 * @author Igor Lucas
 *
 */
@Controller
public class PostController {
	
	/**
	 * Faz a injeção de um PostService na classe, para que esse PostService seja responsável
	 * por gerenciar a maior parte da lógica implementada.
	 */
	@Autowired
	private PostService postService;

	/**
	 * Determina a página a ser aberta ao se acessar a aplicação no localhost
	 * @return Um string do nome da página ao ser aberta inicialmente
	 */
	@GetMapping("/")
	public String paginaInicial() {
	    return "listagem";
	}
	
	/**
	 * Lista todos os posts feitos por um determinado redator em uma página html, no endpoint 'listar'
	 * @param idRedator O id do redator dos posts desejados, colhido a partir do campo input do formulário HTML
	 * @return Os posts do redator na forma de um ModelAndView, devidamente formatados no HTML
	 */
	@PostMapping("**/listar")
	public ModelAndView listarPosts(@RequestParam("idredator") String idRedator ){
		
		List<Post> postagens = postService.montarLista(idRedator);
		
		ModelAndView modelAndView = new ModelAndView("listagem");
		
		modelAndView.addObject("posts", postagens);
		return modelAndView;
	}

}
