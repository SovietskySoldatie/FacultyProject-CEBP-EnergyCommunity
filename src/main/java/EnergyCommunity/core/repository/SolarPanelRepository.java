package EnergyCommunity.core.repository;

import EnergyCommunity.core.model.SolarPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolarPanelRepository extends JpaRepository<SolarPanel, Long> {
    List<SolarPanel> findByHouseId(Long houseId);
}
