
package com.example.invoice.controller;

import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.dto.InvoiceResponse;
import com.example.invoice.service.InvoiceService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // CREATE - Create a new invoice
    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(
            @Valid @RequestBody InvoiceRequest request) {

        InvoiceResponse response =
                invoiceService.createInvoice(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // READ - Get invoice by ID
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                invoiceService.getInvoiceById(id));
    }

    // READ - Get all invoices
    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {

        return ResponseEntity.ok(
                invoiceService.getAllInvoices());
    }

    // UPDATE - Update an existing invoice
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequest request) {

        InvoiceResponse response =
                invoiceService.updateInvoice(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE - Delete an invoice
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable Long id) {

        invoiceService.deleteInvoice(id);

        return ResponseEntity.noContent().build();
    }
}









