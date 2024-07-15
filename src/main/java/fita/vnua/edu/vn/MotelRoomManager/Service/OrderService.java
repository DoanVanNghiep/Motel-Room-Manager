package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Dto.OrderDto;
import fita.vnua.edu.vn.MotelRoomManager.Mapper.OrderMapper;
import fita.vnua.edu.vn.MotelRoomManager.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    public void processOrder(Order order) {
        orderRepository.save(order);
    }
}
