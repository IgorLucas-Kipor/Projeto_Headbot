package com.igorlucas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.igorlucas.entities.Post;

@Service
public class PostService {
	
	public List<Post> listarPosts(Long id){
		RestTemplate restTemplate = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("jsonplaceholder.typicode.com/")
				.path("posts/")
				.build();
		
		ResponseEntity<Post[]> posts =  restTemplate.getForEntity(uri.toUriString(), Post[].class);

		List<Post> tempList = Arrays.asList(posts.getBody());
		
		List<Post> postList = new ArrayList<>();
		
		for (Post p : tempList) {
			if (p.getUserId() == id) {
				postList.add(p);
			}
		}
		
		return postList;
		
	}

}
