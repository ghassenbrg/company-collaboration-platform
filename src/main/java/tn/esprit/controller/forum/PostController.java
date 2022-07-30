package tn.esprit.controller.forum;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.forums.Comment;
import tn.esprit.model.forums.Post;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.forums.PostService;
import tn.esprit.service.user.UserService;

/**
 * 
 * @author Mohamed Marzougui
 *
 */

@RestController
@RequestMapping("/posts")
@PreAuthorize("hasRole('USER')")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Post> addPost(@Valid @RequestBody Post post, @CurrentUser UserPrincipal currentUser) {
		User user = userService.getCurrentUserEntity(currentUser);
		post.setUser(user);
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
			@PathVariable(value = "id") Long postId, @CurrentUser UserPrincipal currentUser) {
		User user = userService.getCurrentUserEntity(currentUser);
		comment.setUser(user);
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