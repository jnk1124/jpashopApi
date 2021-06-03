package jpabook.jpashop.service;

import jpabook.jpashop.domains.Delivery;
import jpabook.jpashop.domains.Member;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderItem;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        // CasCade해서 order만 save함. (orderItem, Delivery)
        // 다른곳에서 참조하지 않는것들을 CasCade로 처리하여야함.
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel(); //비지니스 매소드
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
            return orderRepository.findAllByString(orderSearch);
    }

}
