package com.github.bruce_mig.query_service.query.api.events;

import com.github.bruce_mig.axon_cqrs.commons.events.ProductCreatedEvent;
import com.github.bruce_mig.axon_cqrs.commons.events.ProductDeletedEvent;
import com.github.bruce_mig.axon_cqrs.commons.events.ProductUpdatedEvent;
import com.github.bruce_mig.query_service.query.api.data.Product;
import com.github.bruce_mig.query_service.query.api.data.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
@Slf4j
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);
        log.info("Created query product: {}", product);
    }

    @EventHandler
    public void on(ProductUpdatedEvent event){
        String productId = event.getProductId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        product.setName(event.getName());
        product.setPrice(event.getPrice());
        product.setQuantity(event.getQuantity());

        productRepository.save(product);
        log.info("Updated query product to : {}", product);
    }

    @EventHandler
    public void on(ProductDeletedEvent event){
        String productId = event.getProductId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productRepository.delete(product);
        log.info("Deleted query product with id : {}", productId);
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        log.error(exception.getMessage());
        throw exception;
    }
}
