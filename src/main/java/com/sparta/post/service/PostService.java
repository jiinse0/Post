package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public PostResponseDto creatPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);

        // DB 저장
        repository.save(post);

        return new PostResponseDto(post);
    }

    public PostResponseDto getOnePost(Long id) {
        Post post = findById(id);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPost() {
        return repository.findAllByOrderByModifiedAtDesc().stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = findById(id);

        post.update(requestDto);

        return new PostResponseDto(post);
    }

    public String deletePost(Long id, PostRequestDto requestDto) {
        Post post = findById(id);

        repository.delete(post);

        return "삭제가 완료되었습니다.";
    }

    private Post findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
    }
}