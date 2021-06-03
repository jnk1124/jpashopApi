package jpabook.jpashop.api;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v2/orderList")
    public Result getOrderList(){

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<orderDto> collect = new ArrayList<>();
        for (Order order : orders) {
            orderDto orderDto = new orderDto(order);
            collect.add(orderDto);
        }

        return new Result(collect, collect.size());
    }

    @Data
    @AllArgsConstructor
    private class Result<T>{
        private T data;
        private int count;
    }

    @Data
    private class orderDto {
        private Long orderId;
        private String custName;
        private LocalDateTime localDateTime;
        private Address address;
        private OrderStatus orderStatus;

        public orderDto(Order order){
            orderId = order.getId();
            custName = order.getMember().getName();
            localDateTime = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
