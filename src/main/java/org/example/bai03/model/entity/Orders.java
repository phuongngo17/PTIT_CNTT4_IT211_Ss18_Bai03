package org.example.bai03.model.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime createdDate;

    private String status;

    private BigDecimal totalMoney;

    @OneToMany(mappedBy = "orders",
            cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}