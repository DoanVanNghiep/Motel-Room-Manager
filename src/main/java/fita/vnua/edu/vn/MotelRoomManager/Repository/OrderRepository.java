package fita.vnua.edu.vn.MotelRoomManager.Repository;

import fita.vnua.edu.vn.MotelRoomManager.Domain.Order;
import fita.vnua.edu.vn.MotelRoomManager.Until.MySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    // thống kê doanh thu theo tháng
    @Query(value = MySQL.MONTHLY_REVENUE,nativeQuery = true)
    List<Object[]> getRevenueByMonthYear(@Param("month") int month, @Param("year") int year);

    // tính tổng doanh thu cửa hang
    @Query(value = "select sum(o.totalCost) as revenue from Order o where o.statusOrder = 2")
    Long calculateTotalRevenue();

    @Query(value = "select COUNT(order_id) from `order`", nativeQuery = true)
    int countOrderById();

    // lay danh sach hoa don theo trang thai hoa don
    @Query("select ol from Order ol where ol.statusOrder = ?1")
    List<Order> getOrderByOrderStatus(Integer orderStatus);

    Order findById(int id);

    List<Order> getOrderByUserId(Integer idUser);

}