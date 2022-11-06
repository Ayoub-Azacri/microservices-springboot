package com.ayoub.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ayoub.orderservice.dto.InventoryResponse;
import com.ayoub.orderservice.dto.OrderLineItemsDto;
import com.ayoub.orderservice.dto.OrderRequest;
import com.ayoub.orderservice.model.Order;
import com.ayoub.orderservice.model.OrderLineItems;
import com.ayoub.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class OrderService {

    //? inject OrderRepository to Orderservice with constructor injectiion RequiredArgsContru
    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    //?Create our own spanID
    private final Tracer tracer;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        //? map orderlineitems to orderlinitems
        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        //? Collect all the skuCodes from Order Object
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                                    .map(OrderLineItems::getSkuCode)
                                    .collect(Collectors.toList());


        //?Configure our own  tracer
        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try(Tracer.SpanInScope spanInScope=tracer.withSpan(inventoryServiceLookup.start())){
            //?call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)//? read the data from webclient response
                .block();//!by default webclient makes asynchronous request


        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                                    .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock) {
        //? save orders into data base
            orderRepository.save(order);
            return "Order Placed Successfully";
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


        } finally {
            inventoryServiceLookup.end();
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
