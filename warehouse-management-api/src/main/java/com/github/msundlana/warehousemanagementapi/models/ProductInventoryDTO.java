package com.github.msundlana.warehousemanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryDTO {
    private Long id;
    private String name;
    private double price;
    private int availableStock;
}
