package jpabook.jpashop.repository.displayQuery;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderSimpleDto {
    private Long orderId;
    private String custName;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;
    private Address address;

}
