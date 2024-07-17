package fita.vnua.edu.vn.MotelRoomManager.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.RoomImage;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomDetailService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import fita.vnua.edu.vn.MotelRoomManager.Until.MyUntil;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class RoomController {

    @Autowired
    RoomDetailService roomDetailService;

    @Autowired
    RoomService roomService;

    @Autowired
    private Cloudinary cloudinary;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @GetMapping("/details/{id}")
    public String getRoomDetails(@PathVariable Integer id, Model model) {
        Room room = roomService.getRoom(id);
        List<Room> similarRooms = roomService.findSimilarRooms(room.getPrice(), room.getKindOfRoom());
        RoomImage roomImage = roomDetailService.getRoomImage(id);
        model.addAttribute("room", room);
        model.addAttribute("similarRooms", similarRooms);

        return "views/detailRoomView";
    }
    @GetMapping("/detail_admin/{id}")
    public String getRoomDetailsAdmin(@PathVariable Integer id, Model model) {
        Room room = roomService.getRoom(id);
        RoomImage roomImage = roomDetailService.getRoomImage(id);
        model.addAttribute("room", room);
        return "views/detailRoomAdminView";
    }
    @GetMapping("/checkout-deposit/{id}")
    public String getRoomCheckOutDeposit(@PathVariable Integer id, Model model) {
        Room room = roomService.getRoom(id);
        RoomImage roomImage = roomDetailService.getRoomImage(id);
        model.addAttribute("room", room);
        return "views/orderDeposit";
    }
    @GetMapping("/checkout-rent/{id}")
    public String getRoomCheckOut1Month(@PathVariable Integer id, Model model) {
        Room room = roomService.getRoom(id);
        RoomImage roomImage = roomDetailService.getRoomImage(id);
        model.addAttribute("room", room);
        return "views/orderRent";
    }


    // update room
    @GetMapping("/updateRoom/{id}")
    public String getUpdateRoom(Model model,@PathVariable Integer id){
        Room room = roomService.getRoom(id);
        model.addAttribute("room",room);
        return "views/updateRoomView";
    }

    @PostMapping("/updateRoom")
    public String updateRoom(Model model,
                             @RequestParam("room_id") Integer idRoom,
                             @RequestParam("roomNumber") String roomNumber,
                             @RequestParam("kindOfRoom") String kindOfRoom,
                             @RequestParam("price") Double price,
                             @RequestParam("status") Integer status,
                             @RequestParam("area") Double area,
                             @RequestParam("address") String address,
                             @RequestParam("description") String description,
                             @RequestParam("pathFile") MultipartFile file) throws IOException {
        boolean check = true;
        if (file.isEmpty() || file == null) {
            model.addAttribute("file", "Bạn chưa chọn file");
            check = false;
        }if (!check) {
            List<Room> rooms = roomService.findAll();
            model.addAttribute("roomList", rooms);
            return "views/updateRoomView";
        }else {
            Map r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String url = (String) r.get("secure_url");
            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setKindOfRoom(kindOfRoom);
            room.setPrice(price);
            room.setStatus(status);
            room.setDescription(description);
            room.setArea(area);
            room.setAddress(address);
            room.setImage(url);
            roomService.updateRoom(idRoom, room);
        }
        return "redirect:/adminHome";
    }

    //delete room
    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable Integer id,Model model){
        model.addAttribute("message","xóa thành công");
        roomService.deleteRoom(id);
        return "redirect:/roomManager";
    }

    // create room
    @GetMapping("/createRoom")
    public String createRoom(Model model){
        return "views/createRoomView";
    }
    @PostMapping("/createRoom")
    public String createRoom(Model model,
                             @RequestParam("roomNumber") String roomNumber,
                             @RequestParam("kindOfRoom") String kindOfRoom,
                             @RequestParam("price") Double price,
                             @RequestParam("status") Integer status,
                             @RequestParam("area") Double area,
                             @RequestParam("address") String address,
                             @RequestParam("description") String description,
                             @RequestParam("pathFile") MultipartFile file) throws IOException {
        //validate
        boolean check = true;
        if (file.isEmpty() || file == null) {
            model.addAttribute("file", "Bạn chưa chọn file");
            check = false;
        }
        if (!check) {
            List<Room> rooms = roomService.findAll();
            model.addAttribute("roomList", rooms);
            return "views/createRoomView";
        }else {
            Map r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String url = (String) r.get("secure_url");
            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setKindOfRoom(kindOfRoom);
            room.setPrice(price);
            room.setStatus(status);
            room.setArea(area);
            room.setDescription(description);
            room.setCreateDate(LocalDate.now());
            room.setAddress(address);
            room.setImage(url);
            roomService.createRoom(room);
        }
        return "redirect:/adminHome";
    }

    // get room floor
    @GetMapping("/roomFloor/{floor}")
    public String roomFloor(Model model, @RequestParam(name = "keyword", required = false) String keyword,@PathVariable String floor,
                            @RequestParam(name = "page",defaultValue = "1") Integer pageNo){
        Page<Room> rooms = this.roomService.roomFloor(pageNo,floor);
        if (keyword != null){
            rooms = this.roomService.searchRoom(keyword,pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("noOfPages",rooms.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("roomList",rooms);
        return "views/clientHomeViewFloor";
    }

    // get kind of room
    @GetMapping("/kindOfRoom/{kindOfRoom}")
    public String kindOfRoom(Model model, @RequestParam(name = "keyword", required = false) String keyword,@PathVariable String kindOfRoom,
                            @RequestParam(name = "page",defaultValue = "1") Integer pageNo){
        Page<Room> rooms = this.roomService.roomFloor(pageNo,kindOfRoom);
        if (keyword != null){
            rooms = this.roomService.searchRoom(keyword,pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("noOfPages",rooms.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("roomList",rooms);
        return "views/clientHomeView";
    }
}
