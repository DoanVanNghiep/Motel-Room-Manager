package fita.vnua.edu.vn.MotelRoomManager.Service;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Domain.Payment;
import fita.vnua.edu.vn.MotelRoomManager.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

}
