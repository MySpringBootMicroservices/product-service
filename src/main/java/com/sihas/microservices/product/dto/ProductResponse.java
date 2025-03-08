package com.sihas.microservices.product.dto;

import lombok.Data;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, BigDecimal price) {
}
