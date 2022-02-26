package com.shopping.shopping.service.impl;

import com.shopping.shopping.entity.InvoiceEntity;
import com.shopping.shopping.repository.InvoiceItemsRepository;
import com.shopping.shopping.repository.InvoiceRepository;
import com.shopping.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceItemsRepository invoiceItemsRepository;

    @Override
    public List<InvoiceEntity> findAllInvoices() {
        return  invoiceRepository.findAll();
    }


    @Override
    public InvoiceEntity createInvoice(InvoiceEntity invoice) {
        InvoiceEntity invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");
        return invoiceRepository.save(invoice);
    }


    @Override
    public InvoiceEntity updateInvoice(InvoiceEntity invoice) {
        InvoiceEntity invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public InvoiceEntity deleteInvoice(InvoiceEntity invoice) {
        InvoiceEntity invoiceFound = getInvoice(invoice.getId());
        if (invoiceFound == null){
            return  null;
        }
        invoiceFound.setState("DELETED");
        return invoiceRepository.save(invoiceFound);
    }

    @Override
    public InvoiceEntity getInvoice(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}