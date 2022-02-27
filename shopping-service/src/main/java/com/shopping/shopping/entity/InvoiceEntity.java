package com.shopping.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopping.shopping.model.Customer;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "invoice")
@Entity
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "create_at")
    private LocalDate createAt;



    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItemEntity> items;

    private String state;

    @Transient
    private Customer customer;

    public InvoiceEntity(){
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

}
