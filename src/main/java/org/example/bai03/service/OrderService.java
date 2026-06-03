package org.example.bai03.service;

import org.example.bai03.model.dto.request.OrderRequest;
import org.example.bai03.model.entity.Orders;

import java.util.List;

public interface OrderService {

    Orders createOrder(String username, OrderRequest request);

    List<Orders> getMyOrders(String username);

    List<Orders> getAllOrders();

    Orders updateStatus(Long id, String status);
}