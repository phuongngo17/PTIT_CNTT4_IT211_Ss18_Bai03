package org.example.bai03.reponsitory;

import org.example.bai03.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUsername(String username);
}
