package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Payment;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Dto.OrderDto;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.OrderService;
import fita.vnua.edu.vn.MotelRoomManager.Service.PaymentService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    RoomService roomService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("/deposit-order")
    public String getOrderDeposit(Model model,HttpSession session,
                           @RequestParam("room_id") Integer idRoom,
                           @RequestParam("user_id") Integer idUser,
                           @RequestParam("paymentMode") Integer idPayment,
                           @RequestParam("price") Double price){
        Order order = new Order();
        Payment payment = new Payment();
        Room room = roomService.updateRoomStatus(idRoom, 4);
        order.setRoom(room); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setStatus("1"); // Trạng thái đơn hàng
        payment.setRoom(room);
        payment.setUser(new User(idUser));
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(price);
        payment.setPaymentMethod(idPayment);
        payment.setStatus(1);
        orderService.processOrder(order);
        paymentService.savePayment(payment);
        return "redirect:/clientHome";
    }
    @PostMapping("/rent-order")
    public String getOrderRent(Model model,HttpSession session,
                           @RequestParam("room_id") Integer idRoom,
                           @RequestParam("user_id") Integer idUser,
                           @RequestParam("paymentMode") Integer idPayment,
                           @RequestParam("price") Double price){
        Order order = new Order();
        Payment payment = new Payment();
        Room room = roomService.updateRoomStatus(idRoom, 2);
        order.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setStatus("2"); // Trạng thái đơn hàng
        payment.setRoom(room);
        payment.setUser(new User(idUser));
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(price);
        payment.setPaymentMethod(idPayment);
        payment.setStatus(2);
        orderService.processOrder(order);
        paymentService.savePayment(payment);
        orderService.processOrder(order);
        return "redirect:/clientHome";
    }
}
