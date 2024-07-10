package fita.vnua.edu.vn.MotelRoomManager.FormBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginForm {
    private String username;
    private String password;
    private String rememberMe;
    public List<String> validate(){
        List<String> errors = new ArrayList<String>();
        if (username == null || username.trim().isEmpty()) {
            errors.add("Nhap username");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.add("Nhap password");
        }
        return errors;
    }
}
