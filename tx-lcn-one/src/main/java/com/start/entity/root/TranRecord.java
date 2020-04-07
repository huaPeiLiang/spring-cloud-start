package com.start.entity.root;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tranRecord")
public class TranRecord implements Serializable {

    private static final long serialVersionUID = 3510465491044226854L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "accountId")
    private Integer accountId;

    @Column(name = "changeAmount")
    private Double changeAmount;
}
