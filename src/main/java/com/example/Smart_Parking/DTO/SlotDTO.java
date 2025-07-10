package com.example.Smart_Parking.DTO;

public class SlotDTO {
    private int slotId;
    private String slotLabel;
    private boolean available;

    public SlotDTO(int slotId, String slotLabel, boolean available) {
        this.slotId = slotId;
        this.slotLabel = slotLabel;
        this.available = available;
    }

    public int getSlotId() { return slotId; }
    public String getSlotLabel() { return slotLabel; }
    public boolean isAvailable() { return available; }
}
