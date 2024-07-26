package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.OrderService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomDetailService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewController {
    @Autowired
    RoomService roomService;

    @Autowired
    RoomDetailService roomDetailService;

    @Autowired
    OrderService orderService;

    @GetMapping("/clientHome")
    public String getAllRoom(Model model, @RequestParam(name = "keyword", required = false) String keyword,
                             @RequestParam(name = "page",defaultValue = "1") Integer pageNo){
        Page<Room> rooms = this.roomService.getRooms(pageNo);
        if (keyword != null){
            rooms = this.roomService.searchRoom(keyword,pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("noOfPages",rooms.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("roomList",rooms);
        return "/views/clientHomeView";
    }

    // thông tin cá nhân
    @GetMapping("/customerOrderList")
    public String getCustomerOderList(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("orderListOfCustomer",orderService.getOrderByUserId(user.getId()));
        return "/views/customerOrderListView";
    }
}
