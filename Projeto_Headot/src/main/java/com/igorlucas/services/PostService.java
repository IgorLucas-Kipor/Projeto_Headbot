package com.igorlucas.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.igorlucas.entities.Post;

@Service
public class PostService {

	public List<Post> listarPosts(Long id) {

		UriComponents uri = criarUri("https", "jsonplaceholder.typicode.com/", "posts/");

		List<Post> tempList = gerarLista(uri);

		List<Post> listaDesejada = filtrarLista(tempList, id);

		if (listaDesejada.isEmpty()) {
			listaDesejada.add(mensagemErro(listaDesejada, "Lista vazia.",
					"A lista não possui o id requisitado." + " Por favor, tente outro valor."));
		}

		return listaDesejada;

	}

	// --------------------Métodos Auxiliares---------------------------

	public UriComponents criarUri(String scheme, String host, String path) {
		return UriComponentsBuilder
				.newInstance()
				.scheme(scheme)
				.host(host)
				.path(path)
				.build();
	}

	public List<Post> gerarLista(UriComponents uri) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Post[]> posts = restTemplate.getForEntity(uri.toUriString(), Post[].class);

		return Arrays.asList(posts.getBody());
	}

	public List<Post> filtrarLista(List<Post> list, Long id) {
		return list
				.stream()
				.filter(post -> post.getUserId() == id)
				.collect(Collectors.toList());
	}

	public Post mensagemErro(List<Post> list, String titulo, String mensagem) {
		Post erro = new Post(0L, 0L, titulo, mensagem);
		return erro;
	}

}
