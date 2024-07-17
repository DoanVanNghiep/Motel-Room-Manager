package fita.vnua.edu.vn.MotelRoomManager.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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

    @Column(name = "area")
    private Double area;

    @Column(name = "address")
    private String address;

    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(name = "description")
    private String description;
    public Room(Integer id) {
        this.id = id;
    }
}