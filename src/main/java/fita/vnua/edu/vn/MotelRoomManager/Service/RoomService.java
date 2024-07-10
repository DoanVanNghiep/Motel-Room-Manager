package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Mapper.RoomMapper;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomMapper roomMapper;
    public Room createRoom(RoomDto roomDto){
        Room room = new Room();
        room.setId(roomDto.getId());
        room.setKindOfRoom(roomDto.getKindOfRoom());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setPrice(roomDto.getPrice());
        room.setStatus(roomDto.getStatus());
        room.setImage(roomDto.getImage());
        return roomRepository.save(room);
    }
    public List<RoomDto> getRooms(){
        List<Room> rooms = roomRepository.findAll();
        return roomMapper.toDTO(rooms);
    }

    public Room getRoom(Integer id){
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Room updateRoom(Integer id, RoomDto roomDto){
        Room room = getRoom(id);

        room.setRoomNumber(roomDto.getRoomNumber());
        room.setPrice(roomDto.getPrice());
        room.setStatus(roomDto.getStatus());
        room.setImage(roomDto.getImage());
        return roomRepository.save(room);
    }

    public void deleteRoom(Integer id){ roomRepository.deleteById(id);}

}
