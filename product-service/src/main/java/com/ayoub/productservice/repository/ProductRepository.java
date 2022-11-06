package com.ayoub.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ayoub.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

    
}
