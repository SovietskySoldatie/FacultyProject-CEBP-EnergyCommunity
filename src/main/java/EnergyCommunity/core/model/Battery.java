package EnergyCommunity.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
@Table(name = "battery")
@Schema(description = "Entity representing a battery storage unit installed in a house")
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the battery", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Battery model is required")
    @Column(nullable = false)
    @Schema(description = "Model or name of the battery unit", example = "Tesla Powerwall 3")
    private String model = "Standard Battery";

    @NotNull(message = "Battery capacity in kWh is required")
    @Positive(message = "Battery capacity must be positive")
    @Column(nullable = false)
    @Schema(description = "Total energy storage capacity in kWh", example = "13.5")
    private Double capacityKwh;

    @NotNull(message = "Charge percentage is required")
    @Min(value = 0, message = "Charge percentage cannot be less than 0")
    @Max(value = 100, message = "Charge percentage cannot exceed 100")
    @Column(nullable = false)
    @Schema(description = "Current state of charge in percentage (0 - 100)", example = "75.0")
    private Double currentChargePercentage = 50.0;

    @NotNull(message = "Max charge/discharge rate is required")
    @Positive(message = "Max charge/discharge rate must be positive")
    @Column(nullable = false)
    @Schema(description = "Maximum charge/discharge power rate in kW", example = "5.0")
    private Double maxPowerKw = 5.0;

    @NotNull(message = "Battery status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Operating status of the battery", example = "IDLE")
    private BatteryStatus status = BatteryStatus.IDLE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    @JsonIgnoreProperties({"batteries", "solarPanels", "user"})
    @Schema(description = "The house where this battery is installed")
    private House house;

    public Battery() {
    }

    public Battery(Long id, String model, Double capacityKwh, Double currentChargePercentage, Double maxPowerKw, BatteryStatus status) {
        this.id = id;
        this.model = model != null && !model.isBlank() ? model : "Standard Battery";
        this.capacityKwh = capacityKwh;
        this.currentChargePercentage = currentChargePercentage != null ? currentChargePercentage : 50.0;
        this.maxPowerKw = maxPowerKw != null ? maxPowerKw : 5.0;
        this.status = status != null ? status : BatteryStatus.IDLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getCapacityKwh() {
        return capacityKwh;
    }

    public void setCapacityKwh(Double capacityKwh) {
        this.capacityKwh = capacityKwh;
    }

    public Double getCurrentChargePercentage() {
        return currentChargePercentage;
    }

    public void setCurrentChargePercentage(Double currentChargePercentage) {
        this.currentChargePercentage = currentChargePercentage;
    }

    public Double getMaxPowerKw() {
        return maxPowerKw;
    }

    public void setMaxPowerKw(Double maxPowerKw) {
        this.maxPowerKw = maxPowerKw;
    }

    public BatteryStatus getStatus() {
        return status;
    }

    public void setStatus(BatteryStatus status) {
        this.status = status;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Battery other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
