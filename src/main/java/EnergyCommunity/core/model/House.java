package EnergyCommunity.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "houses")
@Schema(description = "Entity representing a smart house and its hardware configuration")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the house", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "House name or label is required")
    @Column(nullable = false)
    @Schema(description = "Name or label of the house", example = "Casa Timisoara Nord")
    private String name;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    @Schema(description = "Physical address of the house", example = "Str. Vasile Parvan nr. 2, Timisoara")
    private String address;

    @Column
    @Schema(description = "Geographic latitude of the house", example = "45.7489")
    private Double latitude;

    @Column
    @Schema(description = "Geographic longitude of the house", example = "21.2087")
    private Double longitude;

    @NotNull(message = "Current consumption is required")
    @PositiveOrZero(message = "Current consumption must be zero or positive")
    @Column(nullable = false)
    @Schema(description = "Current baseline power consumption in kW", example = "1.8")
    private Double currentConsumptionKw = 0.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("houses")
    @Schema(description = "Owner of the house")
    private User user;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    @Schema(description = "Batteries installed in the house")
    private List<Battery> batteries = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    @Schema(description = "Solar panels installed in the house")
    private List<SolarPanel> solarPanels = new ArrayList<>();

    public House() {
    }

    public House(Long id, String name, String address, Double latitude, Double longitude, Double currentConsumptionKw) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentConsumptionKw = currentConsumptionKw != null ? currentConsumptionKw : 0.0;
    }

    public void addBattery(Battery battery) {
        if (battery == null) {
            return;
        }
        if (!this.batteries.contains(battery)) {
            this.batteries.add(battery);
        }
        battery.setHouse(this);
    }

    public void removeBattery(Battery battery) {
        if (battery == null) {
            return;
        }
        this.batteries.remove(battery);
        if (battery.getHouse() == this) {
            battery.setHouse(null);
        }
    }

    public void addSolarPanel(SolarPanel solarPanel) {
        if (solarPanel == null) {
            return;
        }
        if (!this.solarPanels.contains(solarPanel)) {
            this.solarPanels.add(solarPanel);
        }
        solarPanel.setHouse(this);
    }

    public void removeSolarPanel(SolarPanel solarPanel) {
        if (solarPanel == null) {
            return;
        }
        this.solarPanels.remove(solarPanel);
        if (solarPanel.getHouse() == this) {
            solarPanel.setHouse(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getCurrentConsumptionKw() {
        return currentConsumptionKw;
    }

    public void setCurrentConsumptionKw(Double currentConsumptionKw) {
        this.currentConsumptionKw = currentConsumptionKw != null ? currentConsumptionKw : 0.0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Battery> getBatteries() {
        return batteries;
    }

    public void setBatteries(List<Battery> batteries) {
        for (Battery existing : new ArrayList<>(this.batteries)) {
            removeBattery(existing);
        }
        if (batteries != null) {
            for (Battery battery : batteries) {
                addBattery(battery);
            }
        }
    }

    public List<SolarPanel> getSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(List<SolarPanel> solarPanels) {
        for (SolarPanel existing : new ArrayList<>(this.solarPanels)) {
            removeSolarPanel(existing);
        }
        if (solarPanels != null) {
            for (SolarPanel panel : solarPanels) {
                addSolarPanel(panel);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
