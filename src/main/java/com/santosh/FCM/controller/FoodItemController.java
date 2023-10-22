package com.santosh.FCM.controller;

import com.santosh.FCM.model.FoodItem;
import com.santosh.FCM.model.Response;
import com.santosh.FCM.repo.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/fooditem")
public class FoodItemController {
    @Autowired
    private FoodItemRepo foodItemRepo;

    //write add new and edit and delete food item

    @PostMapping("/add")
    public ResponseEntity<Response<FoodItem>> addFoodItem(@RequestBody FoodItem foodItem){
        Optional<FoodItem> existingItem = foodItemRepo.findByItemName(foodItem.getItemName());

        if (existingItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<FoodItem>(false,null,"A FoodItem with this name already exists"));
        } else {
            FoodItem item = foodItemRepo.save(foodItem);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response<FoodItem>(true,item,"Food Item Added Successfully"));
        }
    }

    @GetMapping("/getbypage/{page}/{size}")
    public ResponseEntity<Page<FoodItem>> getItems(@PathVariable int page, @PathVariable int size) {
        Page<FoodItem> items = foodItemRepo.findAll(PageRequest.of(page, size));
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/get/{category}")
    public Iterable<FoodItem> getFoodItem(@PathVariable String category){
        return foodItemRepo.findAllByCategory(category).get();
    }

    @GetMapping("/getById/{id}")
    public FoodItem getFoodItemById(@PathVariable String id){
        return foodItemRepo.findById(id).get();
    }

    @GetMapping("/getall")
    public Iterable<FoodItem> getAllFoodItem(){
        return foodItemRepo.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<FoodItem>> deleteFoodItem(@PathVariable String id){
        FoodItem foodItem = foodItemRepo.findById(id).get();
        foodItemRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<FoodItem>(true,foodItem,"Food Item Deleted Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<FoodItem>> updateFoodItemById(@PathVariable String id, @RequestBody FoodItem foodItem){
        foodItem.setId(id);
        foodItemRepo.save(foodItem);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<FoodItem>(true,foodItem,"Food Item Updated Successfully"));
    }

}
