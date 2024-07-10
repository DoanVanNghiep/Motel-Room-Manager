package fita.vnua.edu.vn.MotelRoomManager.Domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "room", schema = "motelroom-manager")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "kindOfRoom", length = 50)
    private String kindOfRoom;

    @Column(name = "roomNumber", length = 10)
    private String roomNumber;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "image")
    private String image;

}