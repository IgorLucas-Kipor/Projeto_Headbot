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
 * The PostService class is responsible for handling all the operational logic involving posts in the API.
 * The business rules implemented in the project are present here and in the GeneralService class, letting
 * as few as possible of the operational logic under the care of Post Controller.
 * @author Igor Lucas
 *
 */
@Service
public class PostService {

	/**
	 * Lists all posts founds at the URI using the given userId, returning a error message if the list is empty.
	 * @param id The userId from which to collect posts.
	 * @return The list of posts associated with the given userId.
	 */
	public List<Post> listPosts(Long id) {

		UriComponents uri = generateUri("https", "jsonplaceholder.typicode.com/", "posts/");

		List<Post> tempList = generateList(uri);

		List<Post> desiredList = filterList(tempList, id);

		if (desiredList.isEmpty()) {
			desiredList.add(errorMessage("Empty list",
					"The list does not have the posts made by the given id. Please, try another value."));
		}

		return desiredList;

	}

	// --------------------Métodos Auxiliares---------------------------

	/**
	 * Creates a URI using informations of scheme, host and path from the address.
	 * @param scheme The address scheme
	 * @param host The address host
	 * @param path The address path
	 * @return The generated URI
	 */
	public UriComponents generateUri(String scheme, String host, String path) {
		return UriComponentsBuilder
				.newInstance()
				.scheme(scheme)
				.host(host)
				.path(path)
				.build();
	}

	/**
	 * Creates a list of posts using the JSON given by the page.
	 * First, access the URI using a RestTemplate, taking informations using a Array, and then
	 * converts it to a list using Arrays.asList
	 * @param uri The address from which the information will be taken
	 * @return A list with as posts in the address
	 */
	public List<Post> generateList(UriComponents uri) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Post[]> posts = restTemplate.getForEntity(uri.toUriString(), Post[].class);

		return Arrays.asList(posts.getBody());
	}

	/**
	 * Roams the list, identifying the user that possess the given id and returning a list made only
	 * by this user's posts.
	 * @param list The list to be searched
	 * @param id The id of the user to be found
	 * @return A list made only by posts of this user
	 */
	public List<Post> filterList(List<Post> list, Long id) {
		return list
				.stream()
				.filter(post -> post.getUserId() == id)
				.collect(Collectors.toList());
	}

	/**
	 * Generates a custom error message. The message is shown as a post with userId e Id of -1, containing
	 * as a title and message specifications about the error.
	 * @param title What error occurred
	 * @param message A message detailing what led to the error and what actions to take
	 * @return A post containing the error message
	 */
	public Post errorMessage(String title, String message) {
		Post error = new Post(-1L, -1L, title, message);
		return error;
	}
	
	/**
	 * Verifies if the given user id is valid and generates a list according to each case,
	 * generating a list that either contains all this user's posts, or a list containing
	 * a single error message.
	 * @param userId The id of the user from which the posts are desired
	 * @return A list of posts containing either all of the user's posts or a single error message
	 */
	public List<Post> createList(String userId) {
		
		boolean validValue = GeneralService.isNumeric(userId);
		
		List<Post> posts = new ArrayList<>();
		
		if (validValue == true) {
			Long id = Long.valueOf(userId);
			posts = listPosts(id);
		} else if (userId.isEmpty()) {
			posts.add(errorMessage("No value", "There was given no id to search. "
					+ " Please, insert a valid id.."));
		} else {
			posts.add(errorMessage("Invalid value", "The given id is invalid."
					+ " Please, try another value."));
		}
		
		return posts;
	}

}
