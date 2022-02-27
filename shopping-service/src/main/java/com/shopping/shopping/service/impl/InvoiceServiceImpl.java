package com.shopping.shopping.service.impl;

import com.shopping.shopping.client.CustomerClient;
import com.shopping.shopping.client.ProductClient;
import com.shopping.shopping.entity.InvoiceEntity;
import com.shopping.shopping.entity.InvoiceItemEntity;
import com.shopping.shopping.model.Customer;
import com.shopping.shopping.model.Product;
import com.shopping.shopping.repository.InvoiceItemsRepository;
import com.shopping.shopping.repository.InvoiceRepository;
import com.shopping.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceItemsRepository invoiceItemsRepository;

    private final ProductClient productClient;

    private final CustomerClient customerClient;

    @Override
    public List<InvoiceEntity> findAllInvoices() {
        return  invoiceRepository.findAll();
    }


    @Override
    public InvoiceEntity createInvoice(InvoiceEntity invoice) {
        InvoiceEntity newInvoice = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (newInvoice !=null){
            return newInvoice;
        }
        invoice.setState("CREATED");
        newInvoice = invoiceRepository.save(invoice);
        newInvoice.getItems().forEach(invoiceItemEntity -> {
            productClient.updateStockProduct(invoiceItemEntity.getProductId(), invoiceItemEntity.getQuantity() * -1);
        });
        return newInvoice;
    }


    @Override
    public InvoiceEntity updateInvoice(InvoiceEntity invoice) {
        InvoiceEntity found = getInvoice(invoice.getId());
        if (found == null){
            return  null;
        }
        found.setCustomerId(invoice.getCustomerId());
        found.setDescription(invoice.getDescription());
        found.setNumberInvoice(invoice.getNumberInvoice());
        found.getItems().clear();
        found.setItems(invoice.getItems());
        return invoiceRepository.save(found);
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

        InvoiceEntity invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice != null) {
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItemEntity> itemList = invoice.getItems().stream().map(invoiceItemEntity -> {
                Product product = productClient.getProduct(invoiceItemEntity.getProductId()).getBody();
                invoiceItemEntity.setProduct(product);
                return invoiceItemEntity;
            }).collect(Collectors.toList());
            invoice.setItems(itemList);
        }
        return invoice;
    }
}