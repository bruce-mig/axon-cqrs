package com.github.bruce_mig.cmd_service.command.api.controller;


import com.github.bruce_mig.cmd_service.command.api.commands.CreateProductCommand;
import com.github.bruce_mig.cmd_service.command.api.commands.DeleteProductCommand;
import com.github.bruce_mig.cmd_service.command.api.commands.UpdateProductCommand;
import com.github.bruce_mig.axon_cqrs.commons.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commands/products")
public class ProductCommandController {

    @Autowired
    private transient CommandGateway commandGateway;


    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRestModel productRestModel){
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        String result = commandGateway.sendAndWait(createProductCommand);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId,@RequestBody ProductRestModel productRestModel){

        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(productId)
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        String result = commandGateway.sendAndWait(updateProductCommand);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId){

        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
                .productId(productId)
                .build();
        String result = commandGateway.sendAndWait(deleteProductCommand);

        return ResponseEntity.ok(result);
    }
}
