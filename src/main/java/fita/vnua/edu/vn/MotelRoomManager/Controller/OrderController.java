package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.*;
import fita.vnua.edu.vn.MotelRoomManager.Service.*;
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
    RenterService renterService;
    @Autowired
    RentalContractService rentalContractService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @PostMapping("/deposit-order")
    public String getOrderDeposit(Model model,HttpSession session,
                           @RequestParam("room_id") Integer idRoom,
                           @RequestParam("user_id") Integer idUser,
                           @RequestParam("paymentMode") Integer idPayment,
                           @RequestParam("price") Double price){
        Order order = new Order();
        Payment payment = new Payment();
        Renter renter = new Renter();

        ///
        RentalContract rentalContract = new RentalContract();
        User user = userService.getUser(idUser);
        renter.setName(user.getFullName());
        renter.setUser(new User(idUser));
        renter.setRoom(new Room(idRoom));
        renter.setEmail(user.getEmail());
        renter.setPhone(user.getPhone());
        renterService.createRenter(renter);


        ///
        String idRental = UUID.randomUUID().toString().substring(0,10);
        rentalContract.setContractCode(idRental);
        rentalContract.setTenantCode(idUser);
        rentalContract.setRoomCode(idRoom);
        rentalContract.setRentalStartDate(new Date().toInstant());
        rentalContract.setDeposit(price);
        rentalContractService.createRenter(rentalContract);

        /////
        Room room = roomService.updateRoomStatus(idRoom, 5);
        order.setRoom(room); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setTotalCost(price);
        order.setStatus("1"); // Trạng thái đơn hàng
        orderService.processOrder(order);
        /////
        payment.setRoom(room);
        payment.setUser(new User(idUser));
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(price);
        payment.setPaymentMethod(idPayment);
        payment.setStatus(1);
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
        Room r = roomService.getRoom(idRoom);
        Room room = roomService.updateRoomStatus(idRoom, 5);
        //////
        Renter renter = new Renter();

        RentalContract rentalContract = new RentalContract();
        User user = userService.getUser(idUser);
        renter.setName(user.getFullName());
        renter.setUser(new User(idUser));
        renter.setRoom(new Room(idRoom));
        renter.setEmail(user.getEmail());
        renter.setPhone(user.getPhone());
        renterService.createRenter(renter);
        //////


        String idRental = UUID.randomUUID().toString().substring(0,10);
        rentalContract.setContractCode(idRental);
        rentalContract.setTenantCode(idUser);
        rentalContract.setRoomCode(idRoom);
        rentalContract.setRentalStartDate(new Date().toInstant());
        if (price == r.getPrice()){
            rentalContract.setDaysRemaining(30);
        }
        if(price == r.getPrice() * 2 * 0.98){
            rentalContract.setDaysRemaining(60);
        }
        if(price == r.getPrice() * 6 * 0.95){
            rentalContract.setDaysRemaining(180);
        }
        rentalContract.setMonthlyRent(r.getPrice());
        rentalContract.setDeposit(price);
        rentalContractService.createRenter(rentalContract);

        /////
        order.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setStatus("2"); // Trạng thái đơn hàng
        order.setTotalCost(price);

        ////
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
