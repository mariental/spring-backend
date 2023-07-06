package kk.server.converters;

import kk.server.dtos.PostDto;
import kk.server.entities.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class PostConverter implements Converter<Post, PostDto>{

    @Override
    public PostDto convert(Post source) {
                return PostDto.builder()
                .title(source.getTitle())
                .content(source.getContent())
                .likes(source.getLikes())
                .publishDate(source.getPublishDate())
                .email(source.getUser().getEmail())
                .build();
    }

}
