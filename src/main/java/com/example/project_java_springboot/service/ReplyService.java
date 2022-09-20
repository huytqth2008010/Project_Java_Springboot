package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Customer;
import com.example.project_java_springboot.entity.Rating;
import com.example.project_java_springboot.entity.Reply;
import com.example.project_java_springboot.entity.dto.ReplyDTO;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.repository.ReplyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<Reply> findAll() {
        return replyRepository.findAll(Sort.by("id"));
    }

    public Optional<Reply> findById(Integer id) {
        return replyRepository.findById(id);
    }

    public Reply save(ReplyDTO objDTO) {
        Reply obj = new Reply();
        obj.setContent(objDTO.getContent());
        obj.setId_comment(objDTO.getId_comment());
        obj.setId_account(objDTO.getId_account());
        obj.setId_product(objDTO.getId_product());
        obj.setStatus(EnumStatus.ACTIVE);
        return replyRepository.save(obj);
    }
    public Reply update(int id, Reply objRequestUpdate) {
        Optional<Reply> obj = replyRepository.findById(id);
        Reply objGet = obj.get();
        objGet.setId_account(objRequestUpdate.getId_account());
        objGet.setId_product(objRequestUpdate.getId_product());
        objGet.setId_comment(objRequestUpdate.getId_comment());
        objGet.setContent(objRequestUpdate.getContent());
        objGet.setStatus(objRequestUpdate.getStatus());
        return replyRepository.save(objGet);
    }
    public void deleteById(Integer id) {
        replyRepository.deleteById(id);
    }
}
