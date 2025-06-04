package com.github.bruce_mig.cmd_service.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductCommand {

    @TargetAggregateIdentifier
    private String productId;
}
