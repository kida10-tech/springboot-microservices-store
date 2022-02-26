package com.shopping.shopping.service.impl;

import com.shopping.customer.entity.InvoiceEntity;
import com.shopping.shopping.repository.InvoiceItemRepository;
import com.shopping.shopping.repository.InvoiceRepository;
import com.shopping.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceItemRepository itemRepository;

    @Override
    public List<InvoiceEntity> findAllInvoice() {
        return invoiceRepository.findAll();
    }

    @Override
    public InvoiceEntity getInvoice(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public InvoiceEntity createInvoice(InvoiceEntity invoice) {
        InvoiceEntity newInvoice = invoiceRepository.findByInvoiceNumber(invoice.getInvoiceNumber());
        if(newInvoice != null) {
            return newInvoice;
        }
        invoice.setState("Created");
        return invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceEntity updateInvoice(InvoiceEntity invoice) {
        InvoiceEntity found = getInvoice(invoice.getId());
        if(found == null) {
            return null;
        }
        found.setCustomerId(invoice.getCustomerId());
        found.setDescription(invoice.getDescription());
        found.setInvoiceNumber(invoice.getInvoiceNumber());
        found.getItems().clear();
        found.setItems(invoice.getItems());

        return invoiceRepository.save(found);
    }

    @Override
    public InvoiceEntity deleteInvoice(InvoiceEntity invoice) {
        InvoiceEntity found = getInvoice(invoice.getId());
        if(found == null) {
            return null;
        }
        found.setState("Deleted");
        return invoiceRepository.save(found);
    }
}
