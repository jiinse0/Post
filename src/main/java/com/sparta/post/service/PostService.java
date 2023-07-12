package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public PostResponseDto createPost(PostRequestDto requestDto) {
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

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = findById(id);

        if (post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new PostResponseDto(post);
    }

    public String deletePost(Long id, PostRequestDto requestDto) {
        Post post = findById(id);

        if (post.getPassword().equals(requestDto.getPassword())) {
            repository.delete(post);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "삭제가 완료되었습니다.";
    }

    private Post findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
    }
}