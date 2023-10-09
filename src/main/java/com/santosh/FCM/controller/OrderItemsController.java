package com.santosh.FCM.controller;

import com.santosh.FCM.model.OrderItems;
import com.santosh.FCM.model.OrderStatus;
import com.santosh.FCM.model.Response;
import com.santosh.FCM.repo.OrderItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/orderItems")
public class OrderItemsController {
    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @GetMapping("/all")
    public Iterable<OrderItems> getAllOrderItems() {
        return orderItemsRepo.findAll();
    }

    @GetMapping("/{date}")
    public Optional<OrderItems> getAllOrderItemsByDate(Date date) {
        return orderItemsRepo.findAllByOrderedAt(date);
    }

    @GetMapping("/{status}")
    public Optional<OrderItems> getAllOrderItemsByStatus(OrderStatus status) {
        return orderItemsRepo.findByStatus(status);
    }

    @PostMapping("/add")
    public ResponseEntity<Response<OrderItems>> addOrderItems(OrderItems orderItems) {
        return ResponseEntity.ok(new Response<OrderItems>
                (true, orderItemsRepo.save(orderItems), "Order items added successfully"));
    }

    @PostMapping("/update")
    public ResponseEntity<Response<OrderItems>> updateOrderItems(OrderItems orderItems) {
        return ResponseEntity.ok(new Response<OrderItems>
                (true, orderItemsRepo.save(orderItems), "Order items updated successfully"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Response<OrderItems>> deleteOrderItems(@PathVariable String id) {
        Optional<OrderItems> orderItemsOptional = orderItemsRepo.findById(id);
        if (orderItemsOptional.isPresent()) {
            orderItemsRepo.deleteById(id);
            return ResponseEntity.ok(new Response<OrderItems>
                    (true, orderItemsOptional.get(), "Order items deleted successfully"));
        } else {
            return ResponseEntity.ok(new Response<OrderItems>
                    (false, null, "Order items with this id does not exist"));
        }
    }

    @PostMapping("/update-status/{id}")
    public ResponseEntity<Response<OrderItems>> updateOrderItemsStatus(@PathVariable String id, OrderStatus status) {
        Optional<OrderItems> orderItemsOptional = orderItemsRepo.findById(id);
        if (orderItemsOptional.isPresent()) {
            OrderItems orderItems = orderItemsOptional.get();
            orderItems.setStatus(status);
            return ResponseEntity.ok(new Response<OrderItems>
                    (true, orderItemsRepo.save(orderItems), "Order items status updated successfully"));
        } else {
            return ResponseEntity.ok(new Response<OrderItems>
                    (false, null, "Order items with this id does not exist"));
        }
    }

}
