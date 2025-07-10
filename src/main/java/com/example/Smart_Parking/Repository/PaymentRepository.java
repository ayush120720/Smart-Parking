package com.example.Smart_Parking.Repository;

import com.example.Smart_Parking.Model.Payment;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findAllByUserUserId(Long userId);
   // List<Payment> findAllByReserveId(Long ReserveId);
}
