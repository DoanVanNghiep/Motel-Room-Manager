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
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomMapper roomMapper;
    public  List<Room> findAll(){
        return roomRepository.findAll();
    }
    public Room createRoom(Room room){
        return roomRepository.save(room);
    }
    public Page<Room> getRooms(Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo-1, 6);
        return this.roomRepository.findAll(pageable);
    }

    public Room getRoom(Integer id){
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Room updateRoom(Integer id,Room room){
        room = getRoom(id);
        return roomRepository.save(room);
    }

    public Room updateRoomStatus(Integer roomId, int status) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setStatus(status);
            return roomRepository.save(room);
        } else {
            throw new RuntimeException("Room not found with id " + roomId);
        }
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

    // get room floor
    public Page<Room> roomFloor(Integer pageNo,String floorNumber){
        Pageable pageable = PageRequest.of(pageNo-1, 6);
        return this.roomRepository.roomFloor(floorNumber,pageable);
    }
    // get kind of room

    public Page<Room> kindOfRoom(Integer pageNo,String kindOfRoom){
        Pageable pageable = PageRequest.of(pageNo-1, 6);
        return this.roomRepository.findByKindOfRoom(kindOfRoom,pageable);
    }
}
