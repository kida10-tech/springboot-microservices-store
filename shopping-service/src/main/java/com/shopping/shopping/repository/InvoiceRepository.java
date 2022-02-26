package com.shopping.shopping.repository;

import com.shopping.shopping.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    List<InvoiceEntity> findByCustomerId(Long customerId );
    InvoiceEntity findByNumberInvoice(String numberInvoice);
}
