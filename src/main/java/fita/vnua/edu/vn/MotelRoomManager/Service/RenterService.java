package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {
    @Autowired
    RenterRepository renterRepository;

    public List<Renter> findAll(){
        return renterRepository.findAll();
    }


    public Renter getRenter(Integer id){
        return renterRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Renter getRenterByRoomID(Integer id){
        return renterRepository.findByRoom_Id(id);
    }
    public Renter createRenter(Renter renter){
        return renterRepository.save(renter);
    }

    public Page<Renter> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 4);
        return renterRepository.findAll(pageable);
    }

    public Page<Renter> searchAndPage(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 4);
        return renterRepository.findByNameContaining(keyword, pageable);
    }
}
