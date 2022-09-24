package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.CartItemId;
import com.example.project_java_springboot.entity.OrderDetail;
import com.example.project_java_springboot.entity.OrderDetailId;
import com.example.project_java_springboot.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    public List<OrderDetail> findByOrderDetail(String orderDetailId){
        return  orderDetailRepository.findByOrderDetail(orderDetailId);
    }

    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

}
