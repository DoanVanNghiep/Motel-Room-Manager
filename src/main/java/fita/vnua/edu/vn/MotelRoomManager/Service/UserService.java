package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Room;
import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUser(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
