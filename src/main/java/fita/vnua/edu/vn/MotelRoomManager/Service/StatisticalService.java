package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Repository.OrderRepository;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RoomRepository;
import fita.vnua.edu.vn.MotelRoomManager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatisticalService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;
    public List<Object[]> getRevenueByMonth(int month, int year) {
        return orderRepository.getRevenueByMonthYear(month, year);
    }

    public Long calculateTotalRevenue() {
        return orderRepository.calculateTotalRevenue() != null ?orderRepository.calculateTotalRevenue():0;
    }


    public int countUser() {
        return userRepository.countUsersByRoleId(2);
    }

    public int countOrder() {
        return orderRepository.countOrderById();
    }
}
