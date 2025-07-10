package com.example.Smart_Parking.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "slots")
public class Slots {

    @Id
    @Column(name = "slot_id")
    private int slotId;

    @Column(name = "slot_label")
    private String slotLabel;

    public Slots() {}

    public Slots(int slotId,String slotLabel) {
        this.slotId = slotId;
        this.slotLabel = slotLabel;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getSlotLabel() {
        return slotLabel;
    }

    public void setSlotLabel(String slotLabel) {
        this.slotLabel = slotLabel;
    }
}
