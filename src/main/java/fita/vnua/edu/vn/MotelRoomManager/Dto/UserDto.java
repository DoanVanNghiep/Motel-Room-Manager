package fita.vnua.edu.vn.MotelRoomManager.Dto;

import lombok.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    Integer id;
    String fullName;
    String username;
    String password;
    Byte role;
    String email;
    String phone;
    String address;
}