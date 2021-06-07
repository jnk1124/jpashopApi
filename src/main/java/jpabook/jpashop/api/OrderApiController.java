package jpabook.jpashop.api;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderItem;
import jpabook.jpashop.domains.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.displayQuery.OrderDtoRepository;
import jpabook.jpashop.repository.displayQuery.OrderSimpleDto;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderDtoRepository orderDtoRepository;

    @GetMapping("/api/v2/orderList")
    public Result getOrderList(){

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<orderDto1> collect = new ArrayList<>();
        for (Order order : orders) {
            orderDto1 orderDto = new orderDto1(order);
            collect.add(orderDto);
        }

        return new Result(collect, collect.size());
    }

    @GetMapping("/api/v3/orderList")
    public Result getOrderList3(){

        List<Order> orders = orderRepository.orderList();

        List<orderDto1> collect = new ArrayList<>();
        for (Order order : orders) {
            orderDto1 orderDto = new orderDto1(order);
            collect.add(orderDto);
        }

        return new Result(collect, collect.size());
    }

    @GetMapping("/api/v4/orderList")
    public Result getOrderList4(){

        List<OrderSimpleDto> orderSimpleDtos = orderDtoRepository.orderList4();

        return new Result(orderSimpleDtos, orderSimpleDtos.size());
    }

    @GetMapping("/api/v3/orders")
    public Result ordersV3(){

        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order);
            orderDtoList.add(orderDto);
        }

        return new Result(orderDtoList, orderDtoList.size());
    }

    @GetMapping("/api/v3.1/orders")
    public Result ordersV3_1(@RequestParam(value = "offset", defaultValue = "0") int offset,
                             @RequestParam(value = "limit", defaultValue = "100") int limit){

        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order);
            orderDtoList.add(orderDto);
        }

        return new Result(orderDtoList, orderDtoList.size());
    }

    @Data
    @AllArgsConstructor
    private class Result<T>{
        private T data;
        private int count;
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

    @Data
    static class orderDto1 {
        private Long orderId;
        private String custName;
        private LocalDateTime localDateTime;
        private Address address;
        private OrderStatus orderStatus;

        public orderDto1(Order order){
            orderId = order.getId();
            custName = order.getMember().getName();
            localDateTime = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
