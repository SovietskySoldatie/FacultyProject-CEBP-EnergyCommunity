package EnergyCommunity.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Operating status of a battery storage unit")
public enum BatteryStatus {
    IDLE,
    CHARGING,
    DISCHARGING,
    HOLD_FOR_EV
}
