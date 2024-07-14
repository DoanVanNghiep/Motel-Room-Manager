package fita.vnua.edu.vn.MotelRoomManager.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Integer id;
    private String kindOfRoom;
    private String roomNumber;
    private Double price;
    private Integer status;
    private String image;
    private Double area;
    private String address;
}
