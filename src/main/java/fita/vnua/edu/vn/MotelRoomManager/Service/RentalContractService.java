package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.RentalContract;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RentalContractRepository;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalContractService {
    @Autowired
    RentalContractRepository rentalContractRepository;

    public List<RentalContract> findAll(){
        return rentalContractRepository.findAll();
    }


    public RentalContract getRenter(Integer id){
        return rentalContractRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public RentalContract createRenter(RentalContract rentalContract){
        return rentalContractRepository.save(rentalContract);
    }

    @Transactional
    public void decreaseDaysRemainingDaily() {
        List<RentalContract> rentalContracts = rentalContractRepository.findAll();
        LocalDate today = LocalDate.now();
        for (RentalContract rentalContract : rentalContracts) {
            LocalDate lastUpdated = rentalContract.getLeaseEndDate();
            if (lastUpdated == null || lastUpdated.isBefore(today)) {
                rentalContract.setDaysRemaining(rentalContract.getDaysRemaining() - 1);
                rentalContract.setLeaseEndDate(today);
                rentalContractRepository.save(rentalContract);
                System.out.println("Đã giảm số ngày còn lại của phòng ");
            }
        }
    }
}
