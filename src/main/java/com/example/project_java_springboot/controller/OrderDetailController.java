package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.OrderDetail;
import com.example.project_java_springboot.entity.OrderDetailId;
import com.example.project_java_springboot.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OrderDetail>> findAll(){
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public List<OrderDetail> findById(@PathVariable String id){
        return orderDetailService.findByOrderDetail(id);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrderDetail> save(@RequestBody OrderDetail orderDetail){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailService.save(orderDetail));
    }
}
