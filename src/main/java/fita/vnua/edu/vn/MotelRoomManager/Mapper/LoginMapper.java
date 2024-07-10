package fita.vnua.edu.vn.MotelRoomManager.Mapper;

import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class LoginMapper implements EntityMapper<User, UserDto>{
    @Override
    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .build();
    }

    @Override
    public List<User> toEntity(List<UserDto> d) {
        List<User> users = new ArrayList<>();
        d.forEach(userDto ->{
            users.add(toEntity(userDto));
        });
        return users;
    }

    @Override
    public UserDto toDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    @Override
    public List<UserDto> toDTO(List<User> e) {
        List<UserDto> list = new ArrayList<>();
        e.forEach(user -> {
            list.add(toDTO(user));
        });
        return list;
    }
}
