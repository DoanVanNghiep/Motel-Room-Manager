package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalContractRepository extends JpaRepository<RentalContract, Integer> {
}
