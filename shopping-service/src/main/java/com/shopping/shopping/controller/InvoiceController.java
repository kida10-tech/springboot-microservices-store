package com.shopping.shopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.shopping.entity.InvoiceEntity;
import com.shopping.shopping.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceEntity>> listAllInvoices() {
        List<InvoiceEntity> invoices = invoiceService.findAllInvoice();
        if(invoices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceEntity> getInvoice(@PathVariable("id") Long id) {
        log.info("Fetching Invoice with id {}", id);
        InvoiceEntity invoice = invoiceService.getInvoice(id);
        if(invoice == null) {
            log.error("Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<InvoiceEntity> createInvoice(@Valid @RequestBody InvoiceEntity invoice, BindingResult result) {

        log.info("Creating Invoice: {}", invoice);

        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        InvoiceEntity newInvoice = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceEntity> updateCustomer(@PathVariable("id") Long id, @RequestBody InvoiceEntity invoice) {

        log.info("Updating Invoice with id {}", id);

        InvoiceEntity invoiceFound = invoiceService.updateInvoice(invoice);
        if(invoiceFound == null) {
            log.error("Unable to update. Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceEntity> deleteInvoice(@PathVariable("id") Long id) {

        log.info("Fetching & Deleting Invoice with id {}", id);
        InvoiceEntity invoice = invoiceService.getInvoice(id);
        if(invoice == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;})
                .collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;

    }
}
