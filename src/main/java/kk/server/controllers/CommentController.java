package kk.server.controllers;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kk.server.dtos.CommentDto;
import kk.server.entities.Comment;
import kk.server.entities.Post;
import kk.server.entities.User;
import kk.server.model.CommentRequest;
import kk.server.services.CommentService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
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
    public List<CommentDto> getAll(){
        return commentService.getCommentList();
    }
    
    @PostMapping("/add/{id}")
    public ResponseEntity addComment(@PathVariable Long id, @Valid @RequestBody CommentRequest comment, BindingResult binding){
        if(binding.hasErrors()){
            return ResponseEntity.status(400).body(getErrors(binding));
        }
        Optional<User> user = userService.getUserByEmail(comment.getEmail());  
        Optional<Post> post = postService.getPostById(id);  
        Comment commentToSave = new Comment();
        commentToSave.setComment(comment.getComment());
        commentToSave.setPublishDate(LocalDateTime.now()); 
        commentToSave.setPost(post.get()); 
        commentToSave.setUser(user.get());
        return ResponseEntity.ok(commentService.addComment(commentToSave));
    }
        
    @PutMapping("edit/{id}")
    public void editComment(@PathVariable Long id,@RequestBody String content){
        var findComment = commentService.getCommentById(id);
        if(findComment.isPresent()){
            Comment updateComment = findComment.get();
            updateComment.setComment(content);
            updateComment.setPublishDate(LocalDateTime.now()); 
            commentService.updateComment(updateComment);
        }
    }
    
    @DeleteMapping("delete/{id}")
    public void deleteComment(@PathVariable Long id){
        var findComment = commentService.getCommentById(id);
        if(findComment.isPresent()){
            Comment deleteComment = findComment.get();
            commentService.deleteComment(deleteComment);
        }
    }
}
