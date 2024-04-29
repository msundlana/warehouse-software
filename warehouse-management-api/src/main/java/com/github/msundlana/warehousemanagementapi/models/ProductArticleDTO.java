package com.github.msundlana.warehousemanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductArticleDTO {

    private Long id;

    private Long productId;

    private Long articleId;

    private int quantity;
}
