package com.ait.app.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

```
Orders findOrderById(Long id);

Page<Orders> findByUserId(
        Long userId,
        Pageable pageable);

Page<Orders> findByUserIdAndStatus(
        Long userId,
        String status,
        Pageable pageable);

Page<Orders> findByUserIdAndCreatedAtBetween(
        Long userId,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        Pageable pageable);

Page<Orders> findByUserIdAndStatusAndCreatedAtBetween(
        Long userId,
        String status,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        Pageable pageable);

Page<Orders> findByUserIdAndCreatedAtGreaterThanEqual(
        Long userId,
        LocalDateTime fromDate,
        Pageable pageable);

Page<Orders> findByUserIdAndCreatedAtLessThanEqual(
        Long userId,
        LocalDateTime toDate,
        Pageable pageable);

Page<Orders> findByUserIdAndStatusAndCreatedAtGreaterThanEqual(
        Long userId,
        String status,
        LocalDateTime fromDate,
        Pageable pageable);

Page<Orders> findByUserIdAndStatusAndCreatedAtLessThanEqual(
        Long userId,
        String status,
        LocalDateTime toDate,
        Pageable pageable);
```

}
