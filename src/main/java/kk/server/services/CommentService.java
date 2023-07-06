package kk.server.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kk.server.dtos.CommentDto;
import kk.server.entities.Comment;
import kk.server.repositories.CommentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    ModelMapper modelMapper = new ModelMapper();

    
    public List<CommentDto> getCommentList() {
        modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.LOOSE);
        
        return commentRepository.findAll().stream()
                .map(post -> modelMapper.map(post, CommentDto.class))
                .collect(Collectors.toList());
    } 
    
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
        
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
