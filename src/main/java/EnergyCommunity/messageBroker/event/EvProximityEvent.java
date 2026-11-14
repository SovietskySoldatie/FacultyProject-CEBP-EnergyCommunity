package EnergyCommunity.messageBroker.event;

import java.time.Instant;

/**
 * Event published when the mobile application's geolocation module detects an EV proximity transition.
 *
 * @param houseId   Target household identifier
 * @param userId    Owner / driver user identifier
 * @param eventType Transition type: {@code APPROACHING_HOME} or {@code LEFT_HOME_AREA}
 * @param timestamp Timestamp of the geolocation detection
 */
public record EvProximityEvent(
        Long houseId,
        Long userId,
        ProximityType eventType,
        Instant timestamp
) {
    public enum ProximityType {
        APPROACHING_HOME,
        LEFT_HOME_AREA
    }
}
