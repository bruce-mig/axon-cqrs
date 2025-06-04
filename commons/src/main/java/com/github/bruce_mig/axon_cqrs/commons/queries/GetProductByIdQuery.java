package com.github.bruce_mig.axon_cqrs.commons.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductByIdQuery {
    private String productId;
}
