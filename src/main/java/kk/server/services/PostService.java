package kk.server.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kk.server.dtos.PostDto;
import kk.server.entities.Post;
import kk.server.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    ModelMapper modelMapper = new ModelMapper();
   
    
    public List<PostDto> getPostList() {
        modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.LOOSE);
        
        return postRepository.findAll().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    } 
        
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
        
    public PostDto addPost(Post post) {
        modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.LOOSE);
                
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }
    
    public PostDto updatePost(Post post) {
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }
    
    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
