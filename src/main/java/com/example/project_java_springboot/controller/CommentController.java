package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.Comment;
import com.example.project_java_springboot.entity.dto.CommentDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import com.example.project_java_springboot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> findAll() {
//        List<Comment> objList = new ArrayList<>();
//
//        List<Comment> obj = commentService.findAll();
//        for (Comment objs: obj) {
//            if (objs.getStatus() == EnumStatus.ACTIVE){
//                objList.add(objs);
//            }
//        }
        return ResponseEntity.ok(commentService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Comment> save(@RequestBody CommentDTO commentDTO) {
        commentDTO.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(commentDTO));
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Integer id, @RequestBody CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentService.findById(id);
        if (!optionalComment.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Comment commentRequest = modelMapper.map(commentDTO, Comment.class);
        Comment comment = commentService.update(id, commentRequest);
        CommentDTO commentResponse = modelMapper.map(comment, CommentDTO.class);

        return ResponseEntity.ok().body(commentResponse);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Comment> findById(@PathVariable Integer id) {
        Optional<Comment> optionalComment = commentService.findById(id);

        if (!optionalComment.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalComment.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        commentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
