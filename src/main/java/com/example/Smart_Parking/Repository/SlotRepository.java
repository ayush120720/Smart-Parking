package com.example.Smart_Parking.Repository;

import com.example.Smart_Parking.Model.Slots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slots, Integer> {
}
