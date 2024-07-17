package fita.vnua.edu.vn.MotelRoomManager.Dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {
    Integer id;
    RoomDto room;
    UserDto user;
    LocalDate orderDate;
    LocalDate checkInDate;
    private Double totalCost;
    String status;

}