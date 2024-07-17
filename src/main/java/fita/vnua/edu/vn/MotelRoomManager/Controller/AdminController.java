package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import fita.vnua.edu.vn.MotelRoomManager.Service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
