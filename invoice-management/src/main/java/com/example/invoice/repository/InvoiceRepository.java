package com.example.invoice.repository;

import com.example.invoice.entity.invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository
        extends JpaRepository<invoice, Long> {
}