package com.github.bruce_mig.axon_cqrs.commons.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDeletedEvent {
    private String productId;
}
