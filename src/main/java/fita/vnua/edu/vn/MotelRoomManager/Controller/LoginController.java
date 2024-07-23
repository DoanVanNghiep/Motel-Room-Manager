package fita.vnua.edu.vn.MotelRoomManager.Controller;

import fita.vnua.edu.vn.MotelRoomManager.Domain.User;
import fita.vnua.edu.vn.MotelRoomManager.FormBean.LoginForm;
import fita.vnua.edu.vn.MotelRoomManager.FormBean.RegisterForm;
import fita.vnua.edu.vn.MotelRoomManager.Repository.UserRepository;
import fita.vnua.edu.vn.MotelRoomManager.Service.UserService;
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
    UserService loginService;

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

    @GetMapping("/register")
    public String viewRegister(){
        return "views/register";
    }

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
                if (user.getRole() == 1){
                    response.addCookie(cookieUserName);
                    return "redirect:/adminHome";
                }else if (user.getRole() == 2){
                    response.addCookie(cookieUserName);
                    return "redirect:/clientHome";
                }
            }
        }
        model.addAttribute("errors",String.join(", ", errors));
        model.addAttribute("loginForm", loginForm);
        return "views/login";
    }


    // đăng kí
    @PostMapping("register-account")
    public String registerAccount(Model model,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("fullName") String fullName,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  @RequestParam("email") String email,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("address") String address) {
        RegisterForm registerForm = new RegisterForm(username, password, confirmPassword, fullName,email,phone,address);
        // Kiểm tra tính hợp lệ khi nhập vào
        List<String> errors = registerForm.validate();


        if (errors.isEmpty()) {
            // Kiểm tra tài khoản đã tồn tại chưa
            User user = userRepository.findByRegister(username);
            if (user != null) {
                errors.add("Tài khoản đã tồn tại.!");
                model.addAttribute("errors", String.join(", ", errors));
                return "/views/register";
            }else if (!password.equals(confirmPassword)) {
                model.addAttribute("errors", String.join(", ", errors));
                errors.add("Xác nhận mật khẩu không chính xác.!");
                return "/views/register";
            } else {
                User users = new User();
                users.setRole((byte) 2);
                users.setUsername(username);
                users.setFullName(fullName);
                users.setPassword(password);
                users.setPhone(phone);
                users.setEmail(email);
                users.setRole((byte) 2);
                users.setAddress(address);
                userRepository.save(users);
                model.addAttribute("message","Đăng kí thành công.");
                return "/views/login";
            }
        }
        return "/views/register";
    }


}
