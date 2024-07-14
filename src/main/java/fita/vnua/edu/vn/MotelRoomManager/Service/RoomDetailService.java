package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.RoomImage;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RoomDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailService {
    @Autowired
    RoomDetailRepository roomDetailRepository;

    public RoomImage getRoomImage(Integer id){
        return roomDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
