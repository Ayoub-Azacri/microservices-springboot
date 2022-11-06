package com.ayoub.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ayoub.productservice.dto.ProductRequest;
import com.ayoub.productservice.dto.ProductResponse;
import com.ayoub.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    //? Inject ProductService into
    private final ProductService productService;

    //? method to create products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        //? to create the product inside the db we need to do the logic inside the service
        //? not in the controller
        productService.createProduct(productRequest);
    }

    //? method to get All the products
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        //?create method inside productService to get all products
        return productService.getAllProducts();
    }
}

