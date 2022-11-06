package com.ayoub.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ayoub.productservice.dto.ProductRequest;
import com.ayoub.productservice.dto.ProductResponse;
import com.ayoub.productservice.model.Product;
import com.ayoub.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    //? Inject productRepository into Productservice class
    private final ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest){
        //? 1)create the object type of product
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        //? 2)Save the object into the Database
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }


    public List<ProductResponse> getAllProducts() {
        //?read all the products  inside  database and store it
        List<Product> products = productRepository.findAll();

        //?Map List of Product to List of ProductResponse
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product){
        //? Create Object of ProductResponse and return it back to getAllProducts()
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
