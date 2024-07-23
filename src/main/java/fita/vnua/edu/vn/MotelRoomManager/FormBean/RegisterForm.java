package fita.vnua.edu.vn.MotelRoomManager.FormBean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterForm {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    public List<String> validate(){
        List<String> errors = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            errors.add("Yêu cầu điền thông tin tài khoản.!");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.add("Yêu cầu điền thông tin mật khẩu.!");
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errors.add("Yêu cầu xác nhận mật khẩu.!");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            errors.add("Yêu cầu hãy nhập họ và tên.!");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.add("Yêu cầu hãy nhập email.!");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.add("Yêu cầu hãy nhập số điện thoại.!");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.add("Yêu cầu hãy nhập địa chỉ.!");
        }
        return errors;
    }
}

