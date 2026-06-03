package org.example.bai03.controller;

import lombok.RequiredArgsConstructor;
import org.example.bai03.model.dto.request.OrderRequest;
import org.example.bai03.model.dto.response.ApiDataResponse;
import org.example.bai03.model.entity.Orders;
import org.example.bai03.service.OrderService;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ApiDataResponse<Orders> createOrder(
            @RequestBody OrderRequest request,
            Authentication authentication) {

        return new ApiDataResponse<>(
                true,
                "Đặt hàng thành công",
                orderService.createOrder(
                        authentication.getName(),
                        request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ApiDataResponse<List<Orders>> myOrders(
            Authentication authentication) {

        return new ApiDataResponse<>(
                true,
                "Lấy lịch sử đơn hàng thành công",
                orderService.getMyOrders(
                        authentication.getName()),
                HttpStatus.OK
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ApiDataResponse<List<Orders>> allOrders() {

        return new ApiDataResponse<>(
                true,
                "Lấy tất cả đơn hàng thành công",
                orderService.getAllOrders(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ApiDataResponse<Orders> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return new ApiDataResponse<>(
                true,
                "Cập nhật trạng thái thành công",
                orderService.updateStatus(
                        id,
                        status),
                HttpStatus.OK
        );
    }
}
