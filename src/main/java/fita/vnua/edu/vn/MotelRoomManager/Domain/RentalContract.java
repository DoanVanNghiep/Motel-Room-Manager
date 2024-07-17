package fita.vnua.edu.vn.MotelRoomManager.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "`rental contract`", schema = "motelroom-manager")
public class RentalContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "`Contract Code`", length = 10)
    private String contractCode;

    @Column(name = "`Tenant code`")
    private Integer tenantCode;

    @Column(name = "`Room Code`")
    private Integer roomCode;

    @Column(name = "`Rental Start Date`")
    private Instant rentalStartDate;

    @Column(name = "`Lease End Date`")
    private LocalDate leaseEndDate;

    @Column(name = "Deposit")
    private Double deposit;

    @Column(name = "`Monthly Rent`")
    private Double monthlyRent;

    @Column(name = "`DaysRemaining`")
    private Integer daysRemaining;


}