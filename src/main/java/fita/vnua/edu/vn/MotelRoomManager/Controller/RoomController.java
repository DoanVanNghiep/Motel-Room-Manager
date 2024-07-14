package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.RoomImage;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomDetailService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

//    @GetMapping("/details/{id}")
//    public String getRoomDetails(@PathVariable Integer id, Model model) {
//        Room room = roomService.getRoom(id);
//        List<Room> similarRooms = roomService.findSimilarRooms(room.getPrice(), room.getKindOfRoom());
//        RoomImage roomImage = roomDetailService.getRoomImage(id);
//        model.addAttribute("room", room);
//        model.addAttribute("similarRooms", similarRooms);
//
//        return "views/room-details";
//    }
//    @GetMapping("/checkout-deposit/{id}")
//    public String getRoomCheckOutDeposit(@PathVariable Integer id, Model model) {
//        Room room = roomService.getRoom(id);
//        RoomImage roomImage = roomDetailService.getRoomImage(id);
//        model.addAttribute("room", room);
//        return "views/checkout-deposit";
//    }
//    @GetMapping("/checkout-1Month/{id}")
//    public String getRoomCheckOut1Month(@PathVariable Integer id, Model model) {
//        Room room = roomService.getRoom(id);
//        RoomImage roomImage = roomDetailService.getRoomImage(id);
//        model.addAttribute("room", room);
//        return "views/checkout-1month";
//    }
//    @GetMapping("/checkout-3Month/{id}")
//    public String getRoomCheckOut3Month(@PathVariable Integer id, Model model) {
//        Room room = roomService.getRoom(id);
//        RoomImage roomImage = roomDetailService.getRoomImage(id);
//        model.addAttribute("room", room);
//        return "views/checkout-3month";
//    }
}
