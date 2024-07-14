package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.Mapper.RoomMapper;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Room> getRooms(Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo-1, 6);
        return this.roomRepository.findAll(pageable);
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

    public List<Room> findSimilarRooms(Double price, String kindOfRoom) {
        return roomRepository.findSimilarRooms(price,kindOfRoom);
    }


    public Page<Room> searchRoom(String keyword, Integer pageNo){
        List list = this.roomRepository.searchRoom(keyword);
        Pageable pageable = PageRequest.of(pageNo - 1 ,6 );
        Integer start = (int) pageable.getOffset();
        Integer end = (pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<Room>(list, pageable, list.size());
    }
}
