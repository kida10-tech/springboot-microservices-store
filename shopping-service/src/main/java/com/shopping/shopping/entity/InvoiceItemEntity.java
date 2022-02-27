package com.shopping.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopping.shopping.model.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "invoice_item")
public class InvoiceItemEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "Stock must be higher than 0.")
    private Integer quantity;
    private Double  price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private Double subTotal;

    @Transient
    private Product product;

    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }
    public InvoiceItemEntity(){
        this.quantity = 0;
        this.price = (double) 0;
    }
}