package kk.server.converters;

import kk.server.dtos.PostDto;
import kk.server.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostMapper {
    @Mapping(target="email", source="post.user.email")
    PostDto mapPostToPostDto(Post Post);
}