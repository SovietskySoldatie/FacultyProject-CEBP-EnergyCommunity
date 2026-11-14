package EnergyCommunity.core.mcpServer.tools.model;

/**
 * Supported battery schedule actions that the optimization agent can propose.
 */
public enum BatteryActionType {
    SELL_TO_GRID,
    CHARGE_FROM_SOLAR,
    CHARGE_FROM_GRID,
    HOLD_FOR_EV,
    DISCHARGE_TO_HOME
}
