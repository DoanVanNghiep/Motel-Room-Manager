package fita.vnua.edu.vn.MotelRoomManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "views/_menu-admin"; // The name of your Thymeleaf template
    }

    @GetMapping("/pending-orders")
    public String pendingOrders(Model model) {
        model.addAttribute("activePage", "pending-orders");
        return "views/_menu-admin"; // The name of your Thymeleaf template
    }

    @GetMapping("/delivering-orders")
    public String deliveringOrders(Model model) {
        model.addAttribute("activePage", "delivering-orders");
        return "views/_menu-admin"; // The name of your Thymeleaf template
    }

    @GetMapping("/delivered-orders")
    public String deliveredOrders(Model model) {
        model.addAttribute("activePage", "delivered-orders");
        return "views/_menu-admin"; // The name of your Thymeleaf template
    }

    @GetMapping("/returned-orders")
    public String returnedOrders(Model model) {
        model.addAttribute("activePage", "returned-orders");
        return "views/_menu-admin"; // The name of your Thymeleaf template
    }
}
