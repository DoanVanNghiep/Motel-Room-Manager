package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.RentalContract;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalContractRepository extends JpaRepository<RentalContract, Integer> {
    Page<RentalContract> findByUserContaining(String keyword, Pageable pageable);
    RentalContract findByUser_Id(int id);
}
