package EnergyCommunity.messageBroker.connection;

/**
 * Lifecycle and health management contract for the message broker infrastructure (Kafka / RabbitMQ).
 */
public interface MessageBrokerConnection {

    /**
     * Initializes and establishes the connection to the event streaming broker cluster.
     */
    void connect();

    /**
     * Closes the active broker connection and releases network channels.
     */
    void disconnect();

    /**
     * Verifies whether the broker connection is currently healthy and active.
     *
     * @return true if connected
     */
    boolean isConnected();

    /**
     * Identifies the underlying broker technology (e.g. "Kafka", "RabbitMQ").
     *
     * @return broker type name
     */
    String getBrokerType();
}
