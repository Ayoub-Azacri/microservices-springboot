package com.ayoub.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayoub.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{


    Optional<Inventory> findBySkuCodeIn(List<String> skuCode);

}
