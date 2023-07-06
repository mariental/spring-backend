package kk.server.controllers;

import jakarta.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kk.server.dtos.PostDto;
import kk.server.entities.Post;
import kk.server.entities.User;
import kk.server.model.PostRequest;
import kk.server.services.PostService;
import kk.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")    
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    public List<String> getErrors (BindingResult binding){
        List<String> errors =  new ArrayList<>();
        binding.getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return errors;
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public List<PostDto> getAll(){
        return postService.getPostList();
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public ResponseEntity addPost(@Valid @RequestBody PostRequest post, BindingResult binding) throws SQLException{
        if(binding.hasErrors()){
            return ResponseEntity.status(400).body(getErrors(binding));
        }
        Optional<User> user = userService.getUserByEmail(post.getEmail());  
        Post postToSave = new Post();
        postToSave.setTitle(post.getTitle());
        postToSave.setContent(post.getContent());
        postToSave.setLikes(0);
        postToSave.setPublishDate(LocalDateTime.now()); 
        postToSave.setUser(user.get());
        return ResponseEntity.ok(postService.addPost(postToSave));
    }
        
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("edit/{id}")
    public void editPost(@PathVariable Long id, @RequestBody Post post){
        var findPost = postService.getPostById(id);
        if(findPost.isPresent()){
            Post updatePost = findPost.get();
            updatePost.setTitle(post.getTitle());
            updatePost.setContent(post.getContent());
            updatePost.setPublishDate(LocalDateTime.now());
            postService.updatePost(updatePost);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("addLike/{id}")
    public void editPostLikes(@PathVariable Long id,@RequestBody Integer likes){
        var findPost = postService.getPostById(id);
        if(findPost.isPresent()){
            Post updatePost = findPost.get();
            updatePost.setLikes(likes);
            postService.updatePost(updatePost);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("delete/{id}")
    public void deletePost(@PathVariable Long id){
        var findPost = postService.getPostById(id);
        if(findPost.isPresent()){
            Post deletePost = findPost.get();
            postService.deletePost(deletePost);
        }
    }
}
