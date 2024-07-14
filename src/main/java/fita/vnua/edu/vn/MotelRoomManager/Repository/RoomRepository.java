package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.price BETWEEN ?1 - 10 AND ?1 + 10 AND r.kindOfRoom = ?2")
    List<Room> findSimilarRooms(Double price, String kindOfRoom);

    @Query("SELECT r FROM Room r WHERE r.roomNumber LIKE %?1% ")
    List<Room> searchRoom(String keyword);
}
