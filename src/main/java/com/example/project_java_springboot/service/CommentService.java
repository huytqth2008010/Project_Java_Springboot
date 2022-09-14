package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Comment;
import com.example.project_java_springboot.entity.dto.CommentDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    public Comment save(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setId_product(commentDTO.getId_product());
        comment.setId_account(commentDTO.getId_account());
        comment.setStatus(EnumStatus.ACTIVE);
        return commentRepository.save(comment);
    }
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment update(int id, Comment commentRequest) {
        Optional<Comment> comment = commentRepository.findById(id);
        Comment comment1 = comment.get();
        comment1.setContent(commentRequest.getContent());
        comment1.setId_product(commentRequest.getId_product());
        comment1.setId_account(commentRequest.getId_account());
        comment1.setStatus(EnumStatus.ACTIVE);
        return commentRepository.save(comment1);
    }
}
