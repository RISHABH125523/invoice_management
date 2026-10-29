//package com.example.invoice.dto;
//
//import jakarta.validation.constraints.*;
//        import java.math.BigDecimal;
//
//public record InvoiceRequest(
//
//        @NotBlank(message = "Item description is required")
//        @Size(max = 255, message = "Description cannot exceed 255 characters")
//        String itemDescription,
//
//        @NotNull(message = "Quantity is required")
//        @Min(value = 1, message = "Quantity must be at least 1")
//        @Max(value = 1000000, message = "Quantity is too large")
//        Integer quantity,
//
//        @NotNull(message = "Unit price is required")
//        @DecimalMin(value = "0.01", message = "Unit price must be positive")
//        @Digits(integer = 10, fraction = 2,
//                message = "Unit price must have at most 2 decimal places")
//        BigDecimal unitPrice,
//
//        @NotNull(message = "GST rate is required")
//        @DecimalMin(value = "0.00", message = "GST rate cannot be negative")
//        @DecimalMax(value = "100.00", message = "GST rate cannot exceed 100")
//        @Digits(integer = 3, fraction = 2,
//                message = "GST rate must have at most 2 decimal places")
//        BigDecimal gstRate
//
//) {}













package com.example.invoice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record InvoiceRequest(

        @NotBlank(message = "Item description is required")
        @Size(max = 255, message = "Description is too long")
        String itemDescription,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        @Max(value = 1000000, message = "Quantity is too large")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.01", message = "Price must be positive")
        @Digits(integer = 10, fraction = 2)
        BigDecimal unitPrice,

        @NotNull(message = "GST rate is required")
        @DecimalMin(value = "0.00", message = "GST cannot be negative")
        @DecimalMax(value = "100.00", message = "GST cannot exceed 100")
        @Digits(integer = 3, fraction = 2)
        BigDecimal gstRate

) {}