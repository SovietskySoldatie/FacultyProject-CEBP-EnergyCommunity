package EnergyCommunity.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cardinal orientation of a solar panel array")
public enum PanelOrientation {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST
}
