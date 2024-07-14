package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomDetailService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
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

    @GetMapping("/clientHome")
    public String getAllRoom(Model model, @Param("keyword") String keyword,
                             @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo){
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
}
