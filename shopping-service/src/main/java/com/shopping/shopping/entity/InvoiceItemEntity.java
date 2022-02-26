package com.shopping.shopping.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Table(name = "invoice_item")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class InvoiceItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "Stock must be higher than 0.")
    private Integer quantity;
    private Double price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private Double subTotal;

    public Double getSubTotal() {
        if(price > 0 && quantity > 0) {
            return quantity * price;
        } else return (double) 0;
    }

    public InvoiceItemEntity() {
        this.quantity = 0;
        this.price = (double) 0;
    }
}
