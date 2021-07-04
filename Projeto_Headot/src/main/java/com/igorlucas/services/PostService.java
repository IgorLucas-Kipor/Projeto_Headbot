package com.igorlucas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.igorlucas.entities.Post;

/**
 * A classe PostService é responsável por cuidar da lógica operacional envolvendo o funcionamento de posts na API.
 * As regras de negócio gerais implementadas no projeto são trabalhadas aqui e na classe GeneralService, deixando
 * um mínimo de lógica operacional sob os cuidados de PostController.
 * @author Igor Lucas
 *
 */
@Service
public class PostService {

	/**
	 * Lista todos os posts encontrados na URI a partir do userId passado, retornando uma mensagem de erro caso a lista
	 * seja vazia.
	 * @param id O userId do qual se deseja os posts
	 * @return A lista dos posts associados ao userId passado
	 */
	public List<Post> listarPosts(Long id) {

		UriComponents uri = criarUri("https", "jsonplaceholder.typicode.com/", "posts/");

		List<Post> listaTemporaria = gerarLista(uri);

		List<Post> listaDesejada = filtrarLista(listaTemporaria, id);

		if (listaDesejada.isEmpty()) {
			listaDesejada.add(mensagemErro("Lista vazia",
					"A lista não possui o id requisitado." + " Por favor, tente outro valor."));
		}

		return listaDesejada;

	}

	// --------------------Métodos Auxiliares---------------------------

	/**
	 * Cria uma URI a partir das informações de esquema, hospedeiro e caminho do endereço
	 * @param esquema O esquema do endereço
	 * @param hospedeiro O hospedeiro do endereço
	 * @param caminho O caminho do endereço
	 * @return A uri gerada
	 */
	public UriComponents criarUri(String esquema, String hospedeiro, String caminho) {
		return UriComponentsBuilder
				.newInstance()
				.scheme(esquema)
				.host(hospedeiro)
				.path(caminho)
				.build();
	}

	/**
	 * Cria uma lista de posts a partir dos JSON fornecidos pela página.
	 * Primeiramente acessa a URI através de um RestTemplate, retirando as informações da mesma na forma de Array
	 * e depois os convertendo em lista através da função Arrays.asList
	 * @param uri O endereço do qual serão retiradas as informações
	 * @return Uma lista com todos os posts contidos no endereço
	 */
	public List<Post> gerarLista(UriComponents uri) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Post[]> posts = restTemplate.getForEntity(uri.toUriString(), Post[].class);

		return Arrays.asList(posts.getBody());
	}

	/**
	 * Percorre a lista, identificando todos usuários que possuem o id passado e retornando uma lista composta
	 * apenas pelos posts feitos por esse usuário
	 * @param list A lista a percorrer
	 * @param id O id do usuário a ser buscado
	 * @return Uma lista formada apenas pelos posts desse usuário
	 */
	public List<Post> filtrarLista(List<Post> list, Long id) {
		return list
				.stream()
				.filter(post -> post.getUserId() == id)
				.collect(Collectors.toList());
	}

	/**
	 * Cria uma mensagem de erro customizada. A mensagem é exibida como um post com userId e id igual a -1,
	 * contendo como título e mensagem as especificações do erro
	 * @param titulo O erro que ocorreu
	 * @param mensagem Mensagem detalhando o que causou o erro e que ações tomar
	 * @return O post contendo a mensagem de erro
	 */
	public Post mensagemErro(String titulo, String mensagem) {
		Post erro = new Post(-1L, -1L, titulo, mensagem);
		return erro;
	}
	
	/**
	 * Verifica se o id do redator recebido é válido e montar uma lista conforme o caso,
	 * gerando uma lista que ou contém todos os posts desse redator, ou uma lista que contém
	 * uma única mensagem de erro.
	 * @param idRedator O id do redator do qual se deseja obter os posts
	 * @return Uma lista de posts contendo todos os posts do redator passado ou uma mensagem de erro
	 */
	public List<Post> montarLista(String idRedator) {
		
		boolean valorValido = GeneralService.isNumeric(idRedator);
		
		List<Post> postagens = new ArrayList<>();
		
		if (valorValido == true) {
			Long id = Long.valueOf(idRedator);
			postagens = listarPosts(id);
		} else if (idRedator.isEmpty()) {
			postagens.add(mensagemErro("Sem valor", "Não foi inserido id a pesquisar. "
					+ " Por favor, insira um id válido."));
		} else {
			postagens.add(mensagemErro("Valor inválido", "O id inserido é inválido."
					+ " Por favor, tente inserir outro valor."));
		}
		
		return postagens;
	}

}
