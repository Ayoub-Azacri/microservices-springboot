package com.ayoub.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ayoub.inventoryservice.dto.InventoryResponse;
import com.ayoub.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;

    //? url Pathvariable : http://localhost:8082/api/inventory/iPhone-13,iPhone13-red
    //? url RequestParam : http://localhost:8082/api/inventory/skuCode=iPhone-13&skuCode=iPhone13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //? this function will take in skuCode (product) and it's going to verify
    //?whether it's in the stock or not
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
        //? We're taking List of string as RequestParameteres, and we're passing
        //? this skuCode list of string to isInstock
        //? and Query the Repository to find out all the inventory objects for the givin skuCode
        //? then mapping the inventory object to InventoryResponse object
        //? and finally we're sending the list of InventoryResponse  object as the response
    }

}
