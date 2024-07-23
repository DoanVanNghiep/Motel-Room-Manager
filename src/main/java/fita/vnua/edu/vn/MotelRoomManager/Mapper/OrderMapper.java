package fita.vnua.edu.vn.MotelRoomManager.Mapper;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderMapper implements EntityMapper<Order, OrderDto>{
    @Autowired
    RoomMapper roomMapper;

    @Autowired
    LoginMapper loginMapper;

    @Override
    public Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .checkInDate(orderDto.getCheckInDate())
                .orderDate(orderDto.getOrderDate())
                .room(roomMapper.toEntity(orderDto.getRoom()))
                .user(loginMapper.toEntity(orderDto.getUser()))
                .totalCost(orderDto.getTotalCost())
                .status(orderDto.getStatus())
                .statusOrder(orderDto.getStatusOrder())
                .image(orderDto.getImage())
                .method(orderDto.getMethod())
                .build();
    }

    @Override
    public List<Order> toEntity(List<OrderDto> d) {
        List<Order> list = new ArrayList<>();
        d.forEach(orderDto -> {
            list.add(toEntity(orderDto));
        });
        return list;
    }

    @Override
    public OrderDto toDTO(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .checkInDate(order.getCheckInDate())
                .orderDate(order.getOrderDate())
                .room(roomMapper.toDTO(order.getRoom()))
                .user(loginMapper.toDTO(order.getUser()))
                .totalCost(order.getTotalCost())
                .status(order.getStatus())
                .statusOrder(order.getStatusOrder())
                .image(order.getImage())
                .method(order.getMethod())
                .build();
    }

    @Override
    public List<OrderDto> toDTO(List<Order> e) {
        List<OrderDto> list = new ArrayList<>();
        e.forEach(order -> {
            list.add(toDTO(order));
        });
        return list;
    }
}
