package com.santosh.FCM.repo;

import com.santosh.FCM.model.MenuList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MenuListRepo extends MongoRepository<MenuList,String> {
    Optional<MenuList> findByName(String name);
    Optional<MenuList> findByIsActiveToday(boolean isActiveToday);
}
