package fita.vnua.edu.vn.MotelRoomManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping()
    public String showView(){
        return "views/index";
    }
}
