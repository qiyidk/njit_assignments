package njit.cs656.qiyi.DV;


/**
 * <p>
 * Node
 * </p>
 *
 * @author qiyi
 * @version 2015-12-4
 */
public class Node {
    int costs[][] = new int[4][4];
    int mincosts[] = new int[4];
    Simulator sim = null;
    int dest[] = null;
    int step = 0;

    /**
     * @param args
     */
    public RoutingPacket creatertpkt(int srcid, int destid, int mincosts[]) {
        RoutingPacket oRPacket = new RoutingPacket();
        oRPacket.setDestid(destid);
        oRPacket.setSourceid(srcid);
        oRPacket.setMincost(mincosts);
        return oRPacket;
    }

    void rtinit(int srcid, int dest[], int initcosts[][], int initMinCosts[],
            Simulator s) {
        //System.out.println("Entering rtinit\n");
        costs = initcosts;
        mincosts = initMinCosts;
        sim = s;
        this.dest = dest;
        for (int i = 0; i < dest.length; i++)
            sim.toLayer2(creatertpkt(srcid, dest[i], mincosts));
        if (srcid == 0) printTable();
    }

    public void rtupdate(RoutingPacket oReceivedPacket) {
        //System.out.println("Entering rtupdate");
        int srcid = oReceivedPacket.sourceid;
        int cur = oReceivedPacket.destid;
        boolean flag = false;
        for (int destid = 0; destid < 4; destid++) {
            //if there is new min distance exists
            if (oReceivedPacket.mincost[destid] != 999) {
                //update distance table
                this.costs[srcid][destid] = oReceivedPacket.mincost[destid];
                int cost = this.mincosts[srcid] + this.costs[srcid][destid];
                //find new min for that destination cost
                if (cost < this.mincosts[destid]) {
                    //update min cost table
                    this.mincosts[destid] = cost;
                    this.costs[cur][destid] = cost;
                    flag = true;
                }
            }
        }
        //send new minum value to all its neighbors
        if (flag){
            for (int k = 0; k < this.dest.length; k++)
            sim.toLayer2(
            creatertpkt(cur, this.dest[k], this.mincosts));
            if (cur == 0) printTable();
        }
    }

    void printTable() {
        step++;
        System.out.println("Step: " + step);
        System.out.printf("%-5s", "D0");
        System.out.printf("%-5s", "1");
        System.out.printf("%-5s", "2");
        System.out.printf("%-5s", "3");
        System.out.println();
        System.out.printf("%-5s", "-----");
        System.out.printf("%-5s", "-----");
        System.out.printf("%-5s", "-----");
        System.out.printf("%-5s", "-----");
        System.out.println();
        for (int i = 0; i < 4; i++){
            System.out.printf("%-5s", i);
            System.out.printf("%-5s", costs[i][1]);
            System.out.printf("%-5s", costs[i][2]);
            System.out.printf("%-5s", costs[i][3]);
            System.out.println();
        }
        System.out.println();
    }
}
