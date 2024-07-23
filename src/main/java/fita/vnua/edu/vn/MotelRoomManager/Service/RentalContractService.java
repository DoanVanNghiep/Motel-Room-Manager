package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.RentalContract;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RentalContractRepository;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void deleteRental(Integer id){
        rentalContractRepository.deleteById(id);
    }

    public RentalContract updateRental(int id, RentalContract rentalContract){
        rentalContract = rentalContractRepository.findByUser_Id(id);
        return rentalContractRepository.save(rentalContract);
    }
    public Page<RentalContract> searchAndPage(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 4);
        return rentalContractRepository.findByUserContaining(keyword, pageable);
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

    public Page<RentalContract> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 4);
        return rentalContractRepository.findAll(pageable);
    }
}
