package com.santosh.FCM.repo;

import com.santosh.FCM.model.OrderItems;
import com.santosh.FCM.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;

public interface OrderItemsRepo extends MongoRepository<OrderItems,String> {
    Optional<OrderItems> findByUserId(String userId);
    Optional<OrderItems> findAllByOrderedAt(Date orderedAt);
    Optional<OrderItems> findByStatus(OrderStatus status);
}
