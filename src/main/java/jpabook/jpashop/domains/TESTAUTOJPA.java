package jpabook.jpashop.domains;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "JPASHOP.TESTAUTOJPA")
public class TESTAUTOJPA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUST_NO", nullable = false)
    private String custNo;

    @Column(name = "CUST_NM")
    private String custNm;

    @Column(name = "BDAY")
    private String BDAY;

    @Column(name = "POINT")
    private BigDecimal POINT;

}
