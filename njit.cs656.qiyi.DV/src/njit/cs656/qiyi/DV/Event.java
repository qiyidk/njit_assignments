package njit.cs656.qiyi.DV;

import java.util.Date;

/**
 * <p>
 * Event
 * </p>
 *
 * @author qiyi
 * @version 2015-12-4
 */
public class Event {
    Date eventTime;
    int eventType;
    int eventity;
    RoutingPacket packet;

    public String toString() {
        if (packet != null) {
            return "eventTime: " + eventTime + "| eventType: " + eventType
                    + "| eventity: " + eventity + "|Packet : "
                    + packet.toString();
        } else {
            return "eventTime: " + eventTime + "| eventType: " + eventType
                    + "| eventity: " + eventity;
        }
    }

    public int getEventity() {
        return eventity;
    }

    public void setEventity(int eventity) {
        this.eventity = eventity;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public RoutingPacket getPacket() {
        return packet;
    }

    public void setPacket(RoutingPacket packet) {
        this.packet = packet;
    }
}
