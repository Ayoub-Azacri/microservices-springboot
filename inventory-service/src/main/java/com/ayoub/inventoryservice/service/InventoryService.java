package com.ayoub.inventoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayoub.inventoryservice.dto.InventoryResponse;
import com.ayoub.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    //?inject InventoryRepositry
    private final  InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows //! don't do this in production code
    public List<InventoryResponse> isInStock(List<String> skuCode){
        //?Stumilate slow behavior
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        //?Query the inventory Repositry
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                                    .map(inventory ->
                                        InventoryResponse.builder()
                                                .skuCode(inventory.getSkuCode())
                                                .isInStock(inventory.getQuantity() > 0) // check if the quantiy is in stock
                                                .build()
                                    ).collect(Collectors.toList());
    }
}
