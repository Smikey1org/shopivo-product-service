package com.shopivo.product.utils;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data) {
}