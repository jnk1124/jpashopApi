package jpabook.jpashop.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.Member;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderStatus;
import jpabook.jpashop.domains.item.Book;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        Member member = createMember();

        Book book = createBook("시골JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);


        assertEquals( OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");


    }

    @Test
    public void 주문취소(){
        Member member = createMember();
        Book book = createBook("시골JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "줏문취소상태");
        assertEquals(10, book.getStockQuantity(), "재고는 10개여야함.");
    }

    public void 상품주문_재고수량초과(){
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //when

        //then
        NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
        assertEquals(ex.getMessage(), "need more Stock");
    }

    private Book createBook(String bookName, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(bookName);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }


}