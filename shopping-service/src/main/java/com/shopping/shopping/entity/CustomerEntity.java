package com.shopping.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Id number must not be empty.")
    @Size(min = 8, max = 8, message = "Size must contain 8 digits.")
    @Column(name = "number_id", unique = true, length = 8, nullable = false)
    private String numberId;

    @NotEmpty(message = "Firstname must not be empty.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Lastname must not be empty.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "Wrong email address.")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "Region must not be null.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private RegionEntity region;

    private String state;
}
