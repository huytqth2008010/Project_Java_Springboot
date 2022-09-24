package com.example.project_java_springboot.repository;

import com.example.project_java_springboot.entity.OrderDetail;
import com.example.project_java_springboot.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM order_detail WHERE order_detail.order_id =:order_id",nativeQuery = true)
    List<OrderDetail> findByOrderDetail(@Param("order_id") String order_id) ;

}
