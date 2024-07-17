package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Renter;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Renter createRenter(Renter renter){
        return renterRepository.save(renter);
    }
}
