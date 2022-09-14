package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Reply;
import com.example.project_java_springboot.entity.dto.ReplyDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.service.ReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/replies")
public class ReplyController {
    private final ReplyService replyService;
    @Autowired
    private final ModelMapper modelMapper;

    public ReplyController(ReplyService replyService, ModelMapper modelMapper) {
        this.replyService = replyService;
        this.modelMapper = modelMapper;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Reply>> findAll() {
        return ResponseEntity.ok(replyService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Reply> save(@RequestBody ReplyDTO replyDTO) {
        replyDTO.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(replyService.save(replyDTO));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<ReplyDTO> update(@PathVariable Integer id, @RequestBody ReplyDTO replyDTO) {
        Optional<Reply> optionalObj = replyService.findById(id);

        if (!optionalObj.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Reply reply = new Reply();
        Reply objRequest = modelMapper.map(replyDTO, Reply.class);
        Reply obj = replyService.update(id, objRequest);
        ReplyDTO objResponse = modelMapper.map(obj, ReplyDTO.class);
        objResponse.setStatus(EnumStatus.ACTIVE.name());
        return ResponseEntity.ok().body(objResponse);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Reply> findById(@PathVariable Integer id) {
        Optional<Reply> optionalObj = replyService.findById(id);

        if (!optionalObj.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalObj.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public boolean delete(@PathVariable Integer id) {
        replyService.deleteById(id);
        return true;
    }

}
