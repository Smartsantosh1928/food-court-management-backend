package com.santosh.FCM.controller;

import com.santosh.FCM.model.MenuList;
import com.santosh.FCM.model.Response;
import com.santosh.FCM.repo.MenuListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/menuList")
public class MenuListController {
    @Autowired
    private MenuListRepo menuListRepo;

    @PostMapping("/add")
    public ResponseEntity<Response<MenuList>> addMenuList(@RequestBody MenuList menuList){
        Optional<MenuList> menuListOptional = menuListRepo.findByName(menuList.getName());

        if (menuListOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<MenuList>(false,null,"Menu list with this name already exists"));
        } else {
            MenuList menu = menuListRepo.save(menuList);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response<MenuList>(true,menu,"New Menu list created successfully"));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Response<MenuList>> updateMenuList(@RequestBody MenuList menuList){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<MenuList>(true,menuListRepo.save(menuList),"Menu list updated successfully"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<MenuList>> getMenuList(@PathVariable String id){
        Optional<MenuList> menuListOptional = menuListRepo.findById(id);
        return menuListOptional.map(menuList -> ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(true, menuList, "Menu list fetched successfully"))).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, null, "Menu list with this id does not exist")));
    }

    @GetMapping("/getall")
    public Iterable<MenuList> getAllMenuList(){
        return menuListRepo.findAll();
    }

    @GetMapping("/get-today-menu")
    public ResponseEntity<Response<MenuList>> getTodayMenuList(){
        Optional<MenuList> menuListOptional = menuListRepo.findByIsActiveToday(true);
        return menuListOptional.map(menuList -> ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(true, menuList, "Today's menu list fetched successfully"))).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, null, "Today's menu list does not exist")));
    }

    @PostMapping("/set-today-menu/{id}")
    public ResponseEntity<Response<MenuList>> setTodayMenuList(@PathVariable String id){
        Optional<MenuList> menuListOptional = menuListRepo.findById(id);
        Optional<MenuList> oldActive = menuListRepo.findByIsActiveToday(true);
        if(oldActive.isPresent()){
            MenuList oldActiveMenuList = oldActive.get();
            oldActiveMenuList.setActiveToday(false);
            menuListRepo.save(oldActiveMenuList);
        }
        if(menuListOptional.isPresent()){
            MenuList menuList = menuListOptional.get();
            menuList.setActiveToday(true);
            menuListRepo.save(menuList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<MenuList>(true,menuList,"Today's menu list set successfully"));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<MenuList>(false,null,"Menu list with this id does not exist"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<MenuList>> deleteMenuList(@PathVariable String id){
        Optional<MenuList> menuListOptional = menuListRepo.findById(id);
        if(menuListOptional.isPresent()){
            menuListRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<MenuList>(true,menuListOptional.get(),"Menu list deleted successfully"));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<MenuList>(false,null,"Menu list with this id does not exist"));
        }
    }
}