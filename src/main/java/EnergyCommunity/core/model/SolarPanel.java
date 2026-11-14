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
@Table(name = "solar_panels")
@Schema(description = "Entity representing a solar panel array installed in a house")
public class SolarPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the solar panel", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Solar panel model is required")
    @Column(nullable = false)
    @Schema(description = "Model or description of the solar panel", example = "SunPower Maxeon 6")
    private String model = "Standard Solar Panel";

    @NotNull(message = "Max power capacity in kW is required")
    @Positive(message = "Max power capacity must be positive")
    @Column(nullable = false)
    @Schema(description = "Peak power generation capacity in kW (kWp)", example = "6.5")
    private Double maxCapacityKw;

    @NotNull(message = "Efficiency is required")
    @Min(value = 0, message = "Efficiency cannot be less than 0")
    @Max(value = 100, message = "Efficiency cannot exceed 100")
    @Column(nullable = false)
    @Schema(description = "Panel conversion efficiency percentage", example = "22.8")
    private Double efficiency = 20.0;

    @NotNull(message = "Panel orientation is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Panel cardinal orientation", example = "SOUTH")
    private PanelOrientation orientation = PanelOrientation.SOUTH;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    @JsonIgnoreProperties({"batteries", "solarPanels", "user"})
    @Schema(description = "The house where this solar panel is installed")
    private House house;

    public SolarPanel() {
    }

    public SolarPanel(Long id, String model, Double maxCapacityKw, Double efficiency, PanelOrientation orientation) {
        this.id = id;
        this.model = model != null && !model.isBlank() ? model : "Standard Solar Panel";
        this.maxCapacityKw = maxCapacityKw;
        this.efficiency = efficiency != null ? efficiency : 20.0;
        this.orientation = orientation != null ? orientation : PanelOrientation.SOUTH;
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

    public Double getMaxCapacityKw() {
        return maxCapacityKw;
    }

    public void setMaxCapacityKw(Double maxCapacityKw) {
        this.maxCapacityKw = maxCapacityKw;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public PanelOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(PanelOrientation orientation) {
        this.orientation = orientation;
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
        if (!(o instanceof SolarPanel other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
