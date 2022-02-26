package com.shopping.shopping.service;


import com.shopping.shopping.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {

    List<InvoiceEntity> findAllInvoices();
    InvoiceEntity createInvoice(InvoiceEntity invoice);
    InvoiceEntity updateInvoice(InvoiceEntity invoice);
    InvoiceEntity deleteInvoice(InvoiceEntity invoice);
    InvoiceEntity getInvoice(Long id);
}
