package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping
    public String getAllRoom(Model model){
        List<RoomDto> rooms = roomService.getRooms();
        model.addAttribute("rooms",rooms);
        return "/views/room";
    }
}
