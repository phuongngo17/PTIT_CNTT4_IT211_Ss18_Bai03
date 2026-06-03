package org.example.bai03.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bai03.model.dto.request.OrderItemRequest;
import org.example.bai03.model.dto.request.OrderRequest;
import org.example.bai03.model.entity.OrderItem;
import org.example.bai03.model.entity.Orders;
import org.example.bai03.model.entity.Product;
import org.example.bai03.reponsitory.OrderRepository;
import org.example.bai03.reponsitory.ProductRepository;
import org.example.bai03.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Override
    public Orders createOrder(
            String username,
            OrderRequest request) {

        List<OrderItem> orderItems =
                new ArrayList<>();

        BigDecimal total =
                BigDecimal.ZERO;

        Orders orders = Orders.builder()
                .username(username)
                .createdDate(LocalDateTime.now())
                .status("PENDING")
                .build();

        for (OrderItemRequest item :
                request.getItems()) {

            Product product =
                    productRepository.findById(
                                    item.getProductId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Không tìm thấy sản phẩm"));

            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()));

            total = total.add(itemTotal);

            OrderItem orderItem =
                    OrderItem.builder()
                            .orders(orders)
                            .product(product)
                            .quantity(item.getQuantity())
                            .priceBuy(product.getPrice())
                            .build();

            orderItems.add(orderItem);
        }

        orders.setOrderItems(orderItems);
        orders.setTotalMoney(total);

        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> getMyOrders(
            String username) {

        return orderRepository
                .findByUsername(username);
    }

    @Override
    public List<Orders> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public Orders updateStatus(
            Long id,
            String status) {

        Orders orders =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy đơn hàng"));

        orders.setStatus(status);

        return orderRepository.save(orders);
    }
}
