package com.github.bruce_mig.axon_cqrs.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRestModel {
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
