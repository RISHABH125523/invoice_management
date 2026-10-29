package com.example.invoice.dto;

import com.example.invoice.entity.invoice;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceResponse(
        Long id,
        String itemDescription,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal gstRate,
        BigDecimal taxableAmount,
        BigDecimal cgst,
        BigDecimal sgst,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
    public static InvoiceResponse from(invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getItemDescription(),
                invoice.getQuantity(),
                invoice.getUnitPrice(),
                invoice.getGstRate(),
                invoice.getTaxableAmount(),
                invoice.getCgst(),
                invoice.getSgst(),
                invoice.getTotalAmount(),
                invoice.getCreatedAt()
        );
    }
}