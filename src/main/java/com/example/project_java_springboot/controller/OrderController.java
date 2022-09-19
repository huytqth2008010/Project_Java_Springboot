package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Comment;
import com.example.project_java_springboot.entity.Order;
import com.example.project_java_springboot.entity.dto.CommentDTO;
import com.example.project_java_springboot.entity.dto.OrderDTO;
import com.example.project_java_springboot.entity.enums.OrderStatus;
import com.example.project_java_springboot.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Order>> findAll(
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String startDate,
            @RequestParam(defaultValue = "") String endDate,
            @RequestParam(defaultValue = "0") Integer status,
            @RequestParam(defaultValue = "") String accountId,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String productName){

        return ResponseEntity.ok(orderService.getPage(pageIndex, pageSize, startDate, endDate, status, accountId, userName, productName));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable String id){
        Optional<Order> optionalOrder = orderService.findById(id);

        if (!optionalOrder.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalOrder.get());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> save(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderDTO));
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public boolean delete(@PathVariable String id) {
        orderService.deleteById(id);
        return true;
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (orderDTO.getStatus() == null) {
            orderDTO.setStatus(String.valueOf(OrderStatus.WAITING));
        }
        Order orderRequest = modelMapper.map(orderDTO, Order.class);
        Order order = orderService.update(id, orderRequest);
        OrderDTO orderResponse = modelMapper.map(order, OrderDTO.class);

        return ResponseEntity.ok().body(orderResponse);
    }
}
