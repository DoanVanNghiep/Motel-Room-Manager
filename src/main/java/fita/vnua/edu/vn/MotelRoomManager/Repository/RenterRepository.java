package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {
}
