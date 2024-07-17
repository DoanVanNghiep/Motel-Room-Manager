package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Until.MySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = MySQL.FIND_LOGIN,nativeQuery = true)
    User findByLogin(String username, String password);

    @Query(value = MySQL.FIND_REGISTER,nativeQuery = true)
    User findByRegister(String username);

    @Query(value = "SELECT COUNT(u.IDUser) FROM user u WHERE u.role = ?1", nativeQuery = true)
    int countUsersByRoleId(Integer role);
}
