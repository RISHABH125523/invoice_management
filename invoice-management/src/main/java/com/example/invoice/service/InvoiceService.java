
package com.example.invoice.service;

import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.dto.InvoiceResponse;
import com.example.invoice.entity.invoice;
import com.example.invoice.exception.InvoiceNotFoundException;
import com.example.invoice.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InvoiceService {

    private static final int MONEY_SCALE = 2;
    private static final int CALCULATION_SCALE = 10;

    private static final RoundingMode ROUNDING =
            RoundingMode.HALF_UP;

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // CREATE
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {

        invoice invoice = new invoice();

        invoice.setItemDescription(
                request.itemDescription().trim()
        );
        invoice.setQuantity(request.quantity());
        invoice.setUnitPrice(request.unitPrice());
        invoice.setGstRate(request.gstRate());

        calculateInvoiceAmounts(invoice);

        invoice savedInvoice = invoiceRepository.save(invoice);

        return InvoiceResponse.from(savedInvoice);
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(Long id) {

        invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new InvoiceNotFoundException(id));

        return InvoiceResponse.from(invoice);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceResponse::from)
                .toList();
    }

    // UPDATE
    @Transactional
    public InvoiceResponse updateInvoice(
            Long id, InvoiceRequest request) {

        invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new InvoiceNotFoundException(id));

        existingInvoice.setItemDescription(
                request.itemDescription().trim()
        );
        existingInvoice.setQuantity(request.quantity());
        existingInvoice.setUnitPrice(request.unitPrice());
        existingInvoice.setGstRate(request.gstRate());

        // Recalculate GST and total after updating the values.
        calculateInvoiceAmounts(existingInvoice);

        invoice updatedInvoice =
                invoiceRepository.save(existingInvoice);

        return InvoiceResponse.from(updatedInvoice);
    }

    // DELETE
    @Transactional
    public void deleteInvoice(Long id) {

        invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new InvoiceNotFoundException(id));

        invoiceRepository.delete(existingInvoice);
    }

    // COMMON GST CALCULATION
    private void calculateInvoiceAmounts(invoice invoice) {

        BigDecimal taxableAmount = invoice.getUnitPrice()
                .multiply(BigDecimal.valueOf(invoice.getQuantity()))
                .setScale(MONEY_SCALE, ROUNDING);

        BigDecimal halfRate = invoice.getGstRate()
                .divide(
                        BigDecimal.valueOf(2),
                        CALCULATION_SCALE,
                        ROUNDING
                );

        BigDecimal cgst = taxableAmount
                .multiply(halfRate)
                .divide(
                        BigDecimal.valueOf(100),
                        CALCULATION_SCALE,
                        ROUNDING
                )
                .setScale(MONEY_SCALE, ROUNDING);

        BigDecimal sgst = taxableAmount
                .multiply(halfRate)
                .divide(
                        BigDecimal.valueOf(100),
                        CALCULATION_SCALE,
                        ROUNDING
                )
                .setScale(MONEY_SCALE, ROUNDING);

        BigDecimal totalAmount = taxableAmount
                .add(cgst)
                .add(sgst)
                .setScale(MONEY_SCALE, ROUNDING);

        invoice.setTaxableAmount(taxableAmount);
        invoice.setCgst(cgst);
        invoice.setSgst(sgst);
        invoice.setTotalAmount(totalAmount);
    }
}

