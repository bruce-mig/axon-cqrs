package com.github.bruce_mig.cmd_service.command.api.aggregate;

import com.github.bruce_mig.cmd_service.command.api.commands.CreateProductCommand;
import com.github.bruce_mig.cmd_service.command.api.commands.DeleteProductCommand;
import com.github.bruce_mig.cmd_service.command.api.commands.UpdateProductCommand;
import com.github.bruce_mig.axon_cqrs.commons.events.ProductCreatedEvent;
import com.github.bruce_mig.axon_cqrs.commons.events.ProductDeletedEvent;
import com.github.bruce_mig.axon_cqrs.commons.events.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductsAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductsAggregate(CreateProductCommand createProductCommand) {
        // Perform validations + create event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    public ProductsAggregate() {
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.quantity = event.getQuantity();
        this.productId = event.getProductId();
        this.price = event.getPrice();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand){
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event){
        this.quantity = event.getQuantity();
        this.productId = event.getProductId();
        this.price = event.getPrice();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand){
        ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
        BeanUtils.copyProperties(deleteProductCommand, productDeletedEvent);
        AggregateLifecycle.apply(productDeletedEvent);
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event){
        this.productId = event.getProductId();
    }
}
