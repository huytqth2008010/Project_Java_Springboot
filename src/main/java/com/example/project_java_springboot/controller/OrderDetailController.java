package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.OrderDetail;
import com.example.project_java_springboot.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<OrderDetail> findById(int id){
        Optional<OrderDetail> optionalOrderDetail = orderDetailService.findById(id);

        if (!optionalOrderDetail.isPresent()) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok(optionalOrderDetail.get());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrderDetail> save(@RequestBody OrderDetail orderDetail){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailService.save(orderDetail));
    }
}
