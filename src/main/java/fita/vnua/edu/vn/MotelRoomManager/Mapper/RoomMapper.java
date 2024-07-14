package fita.vnua.edu.vn.MotelRoomManager.Mapper;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RoomMapper implements EntityMapper<Room, RoomDto> {

    @Override
    public Room toEntity(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .image(roomDto.getImage())
                .kindOfRoom(roomDto.getKindOfRoom())
                .roomNumber(roomDto.getRoomNumber())
                .price(roomDto.getPrice())
                .status(roomDto.getStatus())
                .area(roomDto.getArea())
                .address(roomDto.getAddress())
                .build();
    }

    @Override
    public List<Room> toEntity(List<RoomDto> d) {
        List<Room> rooms = new ArrayList<>();
        d.forEach( roomDto ->{
            rooms.add(toEntity(roomDto));
        });
        return rooms;
    }

    @Override
    public RoomDto toDTO(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .image(room.getImage())
                .kindOfRoom(room.getKindOfRoom())
                .price(room.getPrice())
                .roomNumber(room.getRoomNumber())
                .status(room.getStatus())
                .area(room.getArea())
                .address(room.getAddress())
                .build();
    }

    @Override
    public List<RoomDto> toDTO(List<Room> e) {
        List<RoomDto> roomDto = new ArrayList<>();
        e.forEach( room -> {
                roomDto.add(toDTO(room));
            }
        );
        return roomDto;
    }
}
