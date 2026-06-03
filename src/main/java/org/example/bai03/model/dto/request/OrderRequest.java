package org.example.bai03.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<OrderItemRequest> items;
}