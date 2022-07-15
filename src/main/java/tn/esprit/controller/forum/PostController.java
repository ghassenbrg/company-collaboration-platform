package tn.esprit.controller.forum;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.forum.Comment;
import tn.esprit.model.forum.Post;
import tn.esprit.payload.ApiResponse;
import tn.esprit.service.forum.PostService;

/**
 * 
 * @author Ghassen Bargougui
 *
 */

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<Post> addUser(@Valid @RequestBody Post post) {
		post = postService.addPost(post);
		return new ResponseEntity<>(post, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long id) {
		Post post = postService.getPostById(id);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, @PathVariable(value = "id") Long id) {
		post = postService.updatePost(post, id);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable(value = "id") Long id) {
		postService.deletePost(id);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "You successfully deleted the post with id: " + id);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{id}/comments")
	public ResponseEntity<ApiResponse> addCommentToPost(@Valid @RequestBody Comment comment,
			@PathVariable(value = "id") Long postId) {
		postService.addCommentToPost(comment, postId);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,
				"You successfully added a comment to the post with id: " + postId);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/comments")
	public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable(value = "id") Long postId) {
		List<Comment> comments = postService.getCommentsByPost(postId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

}