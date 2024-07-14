package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.Dto.RoomDto;
import fita.vnua.edu.vn.MotelRoomManager.FormBean.LoginForm;
import fita.vnua.edu.vn.MotelRoomManager.Repository.UserRepository;
import fita.vnua.edu.vn.MotelRoomManager.Service.LoginService;
import fita.vnua.edu.vn.MotelRoomManager.Service.ParamService;
import fita.vnua.edu.vn.MotelRoomManager.Service.RoomService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    // service
    @Autowired
    LoginService loginService;

    @Autowired
    ParamService paramService;
    @Autowired
    RoomService roomService;

    // repository
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String viewLogin(Model model){
        return "views/login";
    }

//    @GetMapping("/register")
//    public String viewRegister(){
//        return "views/register";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response){
        Cookie cookieUserName = new Cookie("name", null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
        session.invalidate();
        return "views/login";
    }

    // đăng nhập
    @PostMapping("check-login")
    public String checkLogin(Model model, HttpSession session,
                             HttpServletResponse response,
                             @RequestParam(value = "rememberMe", required = false) String rememberMe,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password){

        LoginForm loginForm = new LoginForm(username,password,rememberMe);

        // kiểm tra tính hợp lệ của dữ liệu nhập vào
        List<String> errors = loginForm.validate();
        if (errors.isEmpty()) {
            User user = userRepository.findByLogin(username,password);
            if (user == null) {//Nếu sai thông tin sẽ thêm vào danh sách lỗi
                errors.add("Sai thông tin tài khooản.!");
            } else {
                session.setAttribute("user", user);
                boolean remember = "Y".equals(rememberMe);
                Cookie cookieUserName = new Cookie("name", user.getUsername());
                if (!remember) {
                    cookieUserName.setMaxAge(1);
                } else {
                    // 1 ngay(doi ra giay)
                    cookieUserName.setMaxAge(60 * 60 * 24);
                }
                response.addCookie(cookieUserName);
                return "redirect:/";
            }
        }
        model.addAttribute("errors",String.join(", ", errors));
        model.addAttribute("loginForm", loginForm);
        return "views/login";
    }

}
