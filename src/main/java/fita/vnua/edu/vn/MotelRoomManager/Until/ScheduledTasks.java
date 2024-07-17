package fita.vnua.edu.vn.MotelRoomManager.Until;

import fita.vnua.edu.vn.MotelRoomManager.Service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final RentalContractService rentalContractService;

    @Autowired
    public ScheduledTasks(RentalContractService rentalContractService) {
        this.rentalContractService = rentalContractService;
    }

    // Lập lịch thực hiện mỗi ngày vào lúc 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void decreaseDaysRemainingDaily() {
        rentalContractService.decreaseDaysRemainingDaily();
    }
}

