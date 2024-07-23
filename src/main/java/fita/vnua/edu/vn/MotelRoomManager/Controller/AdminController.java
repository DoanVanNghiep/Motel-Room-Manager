package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Domain.RentalContract;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    RoomService roomService;

    @Autowired
    StatisticalService statisticalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RenterService renterService;

    @Autowired
    private RentalContractService rentalContractService;

    @GetMapping("/adminHome")
    public String adminHome(Model model) {
        // thống kê theo tháng
        Map<Integer, Integer> revenueMap = generateRevenueMap();
        if(!revenueMap.isEmpty() && revenueMap != null) {
            model.addAttribute("revenueMap", revenueMap);
        }

        // tinh tong doanh thu cua hang
        long totalRevenue = calculateTotalRevenue();
        model.addAttribute("totalRevenue", totalRevenue);

        // dem so luong user trong he thong
        int numberUser = countUser();
        model.addAttribute("numberUser", numberUser);

        // dem so luong hoa don
        int numberOrder = countOrder();
        model.addAttribute("numberOrder", numberOrder);

        return "views/adminHomeViews";
    }
    @GetMapping("/roomManager")
    public String getAllRoom(Model model, @Param("keyword") String keyword,
                             @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo){
        Page<Room> rooms = this.roomService.getRooms(pageNo);
        if (keyword != null){
            rooms = this.roomService.searchRoom(keyword,pageNo);
            model.addAttribute("keyword", keyword);
            if(rooms.isEmpty()){
                model.addAttribute("listNull", "Không tìm thấy sản phẩm với từ khóa tìm kiếm là: "+keyword);
            }
        }

        model.addAttribute("totalPage",rooms.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("roomList",rooms);
        return "/views/managerRoom";
    }
    // Quản lý đơn hàng
    @GetMapping("/order/waitingConfirm")
    public String waitingDelivery(Model model){
        List<Order> orderList = orderService.getListOrderByOrderStatus(1);
        if(orderList.isEmpty()){
            model.addAttribute("orderListEmpty", "Không có đơn hàng nào chưa xác nhận ");
        }else{
            model.addAttribute("orderList", orderList);
        }
        return "views/waitingConfirmOrder";
    }

    @GetMapping("/order/confirmed")
    public String confirmedOrder(Model model){
        List<Order> orderList = orderService.getListOrderByOrderStatus(2);
        if(orderList.isEmpty()){
            model.addAttribute("orderListEmpty"," Không có đơn hàng nào đã xác nhận ");
        }else{
            model.addAttribute("orderList", orderList);
        }
        return "views/confirmedOrder";
    }

    @GetMapping("/order/canceled")
    public String canceled(Model model){
        List<Order> orderList = orderService.getListOrderByOrderStatus(3);
        if(orderList.isEmpty()){
            model.addAttribute("orderListEmpty"," Không có đơn hàng nào đã hủy ");
        }else{
            model.addAttribute("orderList", orderList);
        }
        return "/views/canceledOrder";
    }

    @PostMapping("/order/confirmOrder")
    public String confirmOrder(@RequestParam("statusOrder") int statusOrder,
                               @RequestParam("orderId") int orderId,
                               @RequestParam("roomId") int roomID){
        orderService.updateOrderByOrderStatus(statusOrder,orderId);
        Order order = orderService.getOrderById(orderId);
        if(statusOrder == 2){
            paymentService.update(2,roomID);
            roomService.updateRoomStatus(roomID,Integer.valueOf(order.getStatus()));
        }
        if (statusOrder == 3){
            paymentService.update(3,roomID);
            roomService.updateRoomStatus(roomID,1);
        }
        System.out.println(roomID);

        return "redirect:/order/waitingConfirm";
    }

    // Quản lý người thuê phòng

    @GetMapping("/renter")
    public String listUser(Model model, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<Renter> renter = renterService.getAll(pageNo);
        if(keyword!=null && !keyword.isEmpty()){
            renter = renterService.searchAndPage(keyword, pageNo);
            if(renter.isEmpty()){
                model.addAttribute("listNull", "Không tìm thấy tài khoản với từ khóa tìm kiếm là: "+keyword);
            }
        }
        model.addAttribute("totalPage", renter.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listUser", renter);
        return "/views/renter_manager";
    }
    // Quản lý hợp đồng thuê
    @GetMapping("/rental_contract")
    public String listRentalContract(Model model, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<RentalContract> rentalContracts = rentalContractService.getAll(pageNo);
        if(keyword!=null && !keyword.isEmpty()){
            rentalContracts = rentalContractService.searchAndPage(keyword, pageNo);
            if(rentalContracts.isEmpty()){
                model.addAttribute("listNull", "Không tìm thấy tài khoản với từ khóa tìm kiếm là: "+keyword);
            }
        }
        model.addAttribute("totalPage", rentalContracts.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listUser", rentalContracts);
        return "/views/rental-contract_manager";
    }

    @GetMapping("/rental_contract/detail/{id}")
    public String rentalContractDetail(@PathVariable("id") Integer id,Model model){
        model.addAttribute("rentalContract",rentalContractService.getRenter(id));
        return "/views/rental-contract_detail";
    }




    // hàm lấy Map tháng và doanh thu của tháng đó
    private Map<Integer, Integer> generateRevenueMap() {
        Map<Integer, Integer> revenueMap = new HashMap<>();

        // lấy năm hiện tại
        int currentYear = LocalDate.now().getYear();

        for (int month = 1; month <= 12; month++) {
            // lặp qua các tháng trong nam và lấy năm hiện tại truyền vào hàm
            List<Object[]> resultList = statisticalService.getRevenueByMonth(month, currentYear);
//            if(!resultList.isEmpty()) {
            int totalRevenue = calculateTotalRevenueByMonth(resultList);
            revenueMap.put(month, totalRevenue);
//            }
        }

        return revenueMap;
    }
    // tính tong doanh thu tra ve từ đối tượng trong database (hàm trong db trả về các bản ghi gồm 2 cột: month và revenue)
    // có dạng: 05-2024 - 73770000
    private int calculateTotalRevenueByMonth(List<Object[]> resultList) {
        int totalRevenue = 0;
        if(!resultList.isEmpty()) {
            for (Object[] result : resultList) {
                Double revenue = (Double) result[1]; // Lấy giá trị doanh thu từ cột thứ hai
                totalRevenue += revenue;
            }
        }
        return totalRevenue;
    }

    // tính tổng doanh thu cửa hàng
    private long calculateTotalRevenue() {
        long totalRevenue = statisticalService.calculateTotalRevenue();
        return totalRevenue > 0 ? totalRevenue:0;
    }
    // dem so luong user trong he thong
    private int countUser() {
        return statisticalService.countUser() > 0 ? statisticalService.countUser() : 0;
    }

    // dem so luong don hang
    private int countOrder(){
        return statisticalService.countOrder()> 0 ? statisticalService.countOrder() : 0;
    }
}
