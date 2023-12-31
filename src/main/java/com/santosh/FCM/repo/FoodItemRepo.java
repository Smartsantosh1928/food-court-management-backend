package com.santosh.FCM.repo;

import com.santosh.FCM.model.FoodItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FoodItemRepo extends MongoRepository<FoodItem, String>{
    Optional<List<FoodItem>> findAllByCategory(String category);
    Optional<FoodItem> findByItemName(String itemName);
    @NotNull
    Page<FoodItem> findAll(@NotNull Pageable pageable);

}
