package com.start.entity.root;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="account")
public class Account implements Serializable {

    private static final long serialVersionUID = 6966048019066578901L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

}
