package jpabook.jpashop.repository.displayQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDtoRepository {

    private final EntityManager em;

    public List<OrderSimpleDto> orderList4(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.displayQuery.OrderSimpleDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleDto.class
                ).getResultList();
    }

}
