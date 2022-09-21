package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.*;
import com.example.project_java_springboot.entity.dto.OrderDTO;
import com.example.project_java_springboot.entity.enums.CartItemStatus;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.OrderStatus;
import com.example.project_java_springboot.entity.search.SearchCriteria;
import com.example.project_java_springboot.entity.search.SearchCriteriaOperator;
import com.example.project_java_springboot.repository.CartItemRepository;
import com.example.project_java_springboot.repository.OrderRepository;
import com.example.project_java_springboot.repository.ProductRepository;
import com.example.project_java_springboot.repository.ShoppingCartRepository;
import com.example.project_java_springboot.specifications.OrderSpecification;
import com.example.project_java_springboot.until.CurrentUser;
import com.example.project_java_springboot.until.DateTimeHelper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    private final ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthenticationService authenticationService;


    public OrderService(ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, AuthenticationService authenticationService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.authenticationService = authenticationService;
        this.modelMapper = new ModelMapper();
    }
    public List<Order> findAll() {
        return orderRepository.findAll(Sort.by("id"));
    }
    public Page<Order> getPage(Integer pageIndex, Integer pageSize, String startDate, String endDate,Integer status, String accountId, String userName, String productName) {
        Specification<Order> specification = Specification.where(null);

        if (status != null){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("status", SearchCriteriaOperator.EQUALS, status));
            specification = specification.and(specification);
        }

        if (!accountId.isEmpty()){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("accountId", SearchCriteriaOperator.EQUALS, accountId));
            specification = specification.and(specification);
        }

        if (!startDate.isEmpty()){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("createdAt", SearchCriteriaOperator.GREATER_THAN_OR_EQUALS, DateTimeHelper.convertStringToLocalDateTime(startDate)));
            specification = specification.and(specification);
        }

        if (!endDate.isEmpty()){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("createdAt", SearchCriteriaOperator.LESS_THAN_OR_EQUALS, DateTimeHelper.convertStringToLocalDateTime(endDate)));
            specification = specification.and(specification);
        }

        if (!userName.isEmpty()){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("account", SearchCriteriaOperator.JOIN, userName));
            specification = specification.and(specification);
        }

        if (!productName.isEmpty()){
            OrderSpecification spec = new OrderSpecification(new SearchCriteria("product", SearchCriteriaOperator.JOIN, productName));
            specification = specification.and(specification);
        }

        return orderRepository.findAll(specification, PageRequest.of(pageIndex - 1, pageSize));
    }

    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    public Order save(OrderDTO orderDTO) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByAccount_UserName(CurrentUser.getCurrentUser().getName());
        if (!shoppingCartOptional.isPresent()){
            return null;
        }
        ShoppingCart existShoppingCart = shoppingCartOptional.get();

        Order order = modelMapper.map(orderDTO, Order.class);

        order.setAccountId(existShoppingCart.getAccountId());

        Set<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails == null){
            orderDetails = new HashSet<>();
        }

        for (CartItem cartItem:
                existShoppingCart.getCartItems()) {
            if (cartItem.getStatus() != CartItemStatus.ACTIVE.getValue()){
                continue;
            }

            Optional<Product> product = productRepository.findById(cartItem.getId().getProductId());

            if (!product.isPresent()){
                break;
            }

            Product existProduct = product.get();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(new OrderDetailId(cartItem.getId().getProductId(), order.getId()));
            orderDetail.setProduct(existProduct);
            orderDetail.setOrder(order);
            orderDetail.setUnitPrice(cartItem.getUnitPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetails.add(orderDetail);

            Optional<CartItem> optionalCartItem = cartItemRepository.findById(new CartItemId(existShoppingCart.getId(), existProduct.getId()));

            if (!optionalCartItem.isPresent()){
                continue;
            }

            CartItem existCartItem = optionalCartItem.get();
            existCartItem.setQuantity(0);
            existCartItem.setStatus(CartItemStatus.DELETED.getValue());
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(existShoppingCart.getTotalPrice());
        order.setStatus(OrderStatus.WAITING);
        order.setCreatedBy(authenticationService.getCurrentUser().getUser().getId());
        order.setUpdatedBy(authenticationService.getCurrentUser().getUser().getId());

        existShoppingCart.setTotalPrice(new BigDecimal(0));
        shoppingCartRepository.save(existShoppingCart);

        return orderRepository.save(order);
    }

    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }
    public Order update(String id, Order orderRequest) {
        Optional<Order> order = orderRepository.findById(id);
        Order order1 = order.get();
        order1.setShipAddress(orderRequest.getShipAddress());
        order1.setShipName(orderRequest.getShipName());
        order1.setShipNote(orderRequest.getShipNote());
        order1.setShipPhone(orderRequest.getShipPhone());
        order1.setStatus(orderRequest.getStatus());
        return orderRepository.save(order1);
    }
}
