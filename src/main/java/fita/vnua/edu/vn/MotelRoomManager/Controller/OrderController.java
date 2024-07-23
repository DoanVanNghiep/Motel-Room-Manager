package fita.vnua.edu.vn.MotelRoomManager.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fita.vnua.edu.vn.MotelRoomManager.Domain.*;
import fita.vnua.edu.vn.MotelRoomManager.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
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
    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/deposit-order")
    public String getOrderDeposit(Model model,HttpSession session,
                           @RequestParam("room_id") Integer idRoom,
                           @RequestParam("user_id") Integer idUser,
                           @RequestParam("paymentMode") Integer idPayment,
                           @RequestParam("price") Double price,
                           @RequestParam("pathFile") MultipartFile file) throws IOException {
        Order order = new Order();
        Payment payment = new Payment();
        Renter renter = new Renter();
        Room room = roomService.updateRoomStatus(idRoom, 5);
        Map r = this.cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        String url = (String) r.get("secure_url");

        ///
        RentalContract rentalContract = new RentalContract();
        User user = userService.getUser(idUser);
        renter.setName(user.getFullName());
        renter.setUser(new User(idUser));
        renter.setRoom(new Room(idRoom));
        renter.setEmail(user.getEmail());
        renter.setPhone(user.getPhone());
        renter.setStatus(1); // trạng thái đang đặt cọc nhà
        renterService.createRenter(renter);


        ///
        String idRental = UUID.randomUUID().toString().substring(0,10);
        rentalContract.setContractCode(idRental);
        rentalContract.setTenantCode(idUser);
        rentalContract.setRoomCode(idRoom);
        rentalContract.setRentalStartDate(LocalDate.now());
        rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(1));
        rentalContract.setDeposit(price);
        rentalContract.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        rentalContract.setUser(new User(idUser)); // Làm tương tự với User
        rentalContractService.createRenter(rentalContract);

        /////
        order.setRoom(room); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setTotalCost(price);
        order.setStatus("4"); // Trạng thái đơn hàng
        order.setStatusOrder(1);
        order.setMethod(idPayment);
        order.setOrderNo(idRental);
        order.setStatusOrder(1);
        if (idPayment == 2){
            order.setImage(url);
        }else {
            order.setImage("Đã thanh toán tiền mặt không khuyển khoản");
        }
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
                           @RequestParam("price") Double price,
                           @RequestParam("pathFile") MultipartFile file) throws IOException {
        Order order = new Order();
        Payment payment = new Payment();
        Room r = roomService.getRoom(idRoom);
        Room room = roomService.updateRoomStatus(idRoom, 5);
        Map result = this.cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        String url = (String) result.get("secure_url");
        //////
        Renter renter = new Renter();

        RentalContract rentalContract = new RentalContract();
        User user = userService.getUser(idUser);
        renter.setName(user.getFullName());
        renter.setUser(new User(idUser));
        renter.setRoom(new Room(idRoom));
        renter.setEmail(user.getEmail());
        renter.setPhone(user.getPhone());
        renter.setStatus(2);//// trạng thái đang thuê nhà
        renterService.createRenter(renter);
        //////


        String idRental = UUID.randomUUID().toString().substring(0,10);
        rentalContract.setContractCode(idRental);
        rentalContract.setTenantCode(idUser);
        rentalContract.setRoomCode(idRoom);
        rentalContract.setRentalStartDate(LocalDate.now());
        if (price == r.getPrice()){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(1));
            rentalContract.setDaysRemaining(30);
        }
        if(price == r.getPrice() * 2 * 0.98){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(2));
            rentalContract.setDaysRemaining(60);
        }
        if(price == r.getPrice() * 6 * 0.95){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(6));
            rentalContract.setDaysRemaining(180);
        }
        rentalContract.setMonthlyRent(r.getPrice());
        rentalContract.setDeposit(price);
        rentalContract.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        rentalContract.setUser(new User(idUser)); // Làm tương tự với User
        rentalContractService.createRenter(rentalContract);

        /////
        order.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setStatus("2"); // Trạng thái đơn hàng
        order.setTotalCost(price);
        order.setMethod(idPayment);
        order.setOrderNo(idRental);
        order.setStatusOrder(1);
        if (idPayment == 2){
            order.setImage(url);
        }else {
            order.setImage("Đã thanh toán tiền mặt không khuyển khoản");
        }

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

    @PostMapping("/payRoom-order")
    public String getOrderPayRoom(Model model,HttpSession session,
                               @RequestParam("room_id") Integer idRoom,
                               @RequestParam("user_id") Integer idUser,
                               @RequestParam("paymentMode") Integer idPayment,
                               @RequestParam("price") Double price,
                               @RequestParam("pathFile") MultipartFile file) throws IOException {
        Order order = new Order();
        Payment payment = new Payment();
        Room r = roomService.getRoom(idRoom);
        Room room = roomService.updateRoomStatus(idRoom, 5);
        Map result = this.cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        String url = (String) result.get("secure_url");
        //////

        RentalContract rentalContract = new RentalContract();
        String idRental = UUID.randomUUID().toString().substring(0,10);
        if (price == r.getPrice()){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(1));
        }
        if(price == r.getPrice() * 2 * 0.98){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(2));
        }
        if(price == r.getPrice() * 6 * 0.95){
            rentalContract.setLeaseEndDate(LocalDate.now().plusMonths(6));
        }
        rentalContractService.updateRental(idUser,rentalContract);

        /////
        order.setRoom(new Room(idRoom)); // Giả sử bạn có class Room và User
        order.setUser(new User(idUser)); // Làm tương tự với User
        order.setOrderDate(LocalDate.now());
        order.setCheckInDate(LocalDate.now());
        order.setStatus("2"); // Trạng thái đơn hàng
        order.setTotalCost(price);
        order.setMethod(idPayment);
        order.setOrderNo(idRental);
        order.setStatusOrder(1);
        if (idPayment == 2){
            order.setImage(url);
        }else {
            order.setImage("Đã thanh toán tiền mặt không khuyển khoản");
        }

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
