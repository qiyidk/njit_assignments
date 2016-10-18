package njit.cs656.qiyi.DV;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
/*import java.util.Random;*/

/**
 * <p>
 * Simulator
 * </p>
 *
 * @author qiyi
 * @version 2015-12-4
 */
public class Simulator {
    int LINKCHANGES = 0;
    int TRACE = 1; /* for my debugging */
    int YES = 1;
    int NO = 0;
    int FROM_LAYER2 = 2;
    int LINK_CHANGE = 10;
    double clocktime = 0.000;
    private ArrayList<Event> eventList = null;
    private Event event = null;
    Node node0 = new Node();
    Node node1 = new Node();
    Node node2 = new Node();
    Node node3 = new Node();

    Simulator() {
        eventList = new ArrayList<Event>();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Simulator sim = new Simulator();
        sim.run();
    }

    void init() /* initialize the simulator */
    {
        node0.rtinit(0, initDest0(), initCosts0(), initMinCosts0(), this);
        node1.rtinit(1, initDest1(), initCosts1(), initMinCosts1(), this);
        node2.rtinit(2, initDest2(), initCosts2(), initMinCosts2(), this);
        node3.rtinit(3, initDest3(), initCosts3(), initMinCosts3(), this);
    }

    int[] initDest0() {
        int dest[] = { 1, 3 };
        return dest;
    }

    int[] initDest1() {
        int dest[] = { 2, 0 };
        return dest;
    }

    int[] initDest2() {
        int dest[] = { 3, 1 };
        return dest;
    }

    int[] initDest3() {
        int dest[] = { 0, 2 };
        return dest;
    }

    int[] initMinCosts0() {
        int mincosts[] = { 0, 1, 3, 7 };
        return mincosts;
    }

    int[] initMinCosts1() {
        int mincosts[] = { 1, 0, 1, 999 };
        return mincosts;
    }

    int[] initMinCosts2() {
        int mincosts[] = { 3, 1, 0, 2 };
        return mincosts;
    }

    int[] initMinCosts3() {
        int mincosts[] = { 7, 999, 2, 0 };
        return mincosts;
    }

    int[][] initCosts0() {
        int costs[][] = { { 0, 1, 3, 7 },{ 999, 999, 999, 999 },{ 999, 999, 999, 999 },{ 999, 999, 999, 999 }};
        return costs;
    }
    int[][] initCosts1() {
        int costs[][] = { {999, 999, 999, 999},{ 1, 0, 1, 7  },{ 999, 999, 999, 999 },{ 999, 999, 999, 999 }};
        return costs;
    }

    int[][] initCosts2() {
        int costs[][] = { {999, 999, 999, 999},{ 999, 999, 999, 999 },{ 3 , 1, 0, 2 },{ 999, 999, 999, 999 }};
        return costs;
    }

    int[][] initCosts3() {
        int costs[][] = { {999, 999, 999, 999},{ 999, 999, 999, 999 },{ 999, 999, 999, 999 },{7, 999, 2, 0  }};
        return costs;
    }

    public int getLINKCHANGES() {
        return LINKCHANGES;
    }

    public void setLINKCHANGES(int linkchanges) {
        LINKCHANGES = linkchanges;
    }

    public int getTRACE() {
        return TRACE;
    }

    public void setTRACE(int trace) {
        TRACE = trace;
    }

    /*private double getJimRand() {
        Random r = new Random();
        return r.nextDouble();
    }*/

    public void toLayer2(RoutingPacket oRoutingPacket) {
        //System.out.println("toLayer2");
        Event newEvent = new Event();
        newEvent.setEventType(FROM_LAYER2);
        newEvent.setEventity(oRoutingPacket.destid);
        newEvent.setPacket(oRoutingPacket);
        //long x;
        //x = (new Double(this.getJimRand())).longValue();
        newEvent.setEventTime(new Date());
        addEvent(newEvent);
    }

    private void addEvent(Event event) {
        eventList.add(event);
        //System.out.println("addEvent ");
    }

    public Event getNextEvent() {
        //System.out.println("getNextEvent ");
        Event tmpEvent = null;
        Iterator<Event> iterator = eventList.iterator();
        if (iterator.hasNext()) {
            tmpEvent = (Event) iterator.next();
            eventList.remove(tmpEvent);
            return tmpEvent;
        }
        return null;
    }

    public void run() {
        init();
        while (((event = getNextEvent()) != null)) { // runs in a infinite loop
            //System.out.println("************Loop :" + i++);
            if (event.getEventType() == FROM_LAYER2) {
                if (event.getEventity() == 0)
                    node0.rtupdate(event.getPacket());
                else if (event.getEventity() == 1)
                    node1.rtupdate(event.getPacket());
                else if (event.getEventity() == 2)
                    node2.rtupdate(event.getPacket());
                else if (event.getEventity() == 3)
                    node3.rtupdate(event.getPacket());
                else {
                    System.out.println(" unknown event entity\n");
                }
            }
        }
        System.out.println("Simulator Completed !");
    }
}
