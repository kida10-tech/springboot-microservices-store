package com.shopping.shopping.service;

import com.shopping.customer.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {

    List<InvoiceEntity> findAllInvoice();

    InvoiceEntity getInvoice(Long id);

    InvoiceEntity createInvoice(InvoiceEntity invoice);

    InvoiceEntity updateInvoice(InvoiceEntity invoice);

    InvoiceEntity deleteInvoice(InvoiceEntity invoice);
}
