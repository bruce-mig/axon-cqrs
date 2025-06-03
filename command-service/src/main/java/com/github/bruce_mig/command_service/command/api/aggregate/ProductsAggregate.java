package com.github.bruce_mig.command_service.command.api.aggregate;

import com.github.bruce_mig.command_service.command.api.commands.CreateProductCommand;
import com.github.bruce_mig.command_service.command.api.events.ProductCreatedEvent;
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
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.quantity = productCreatedEvent.getQuantity();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.name = productCreatedEvent.getName();
    }
}
