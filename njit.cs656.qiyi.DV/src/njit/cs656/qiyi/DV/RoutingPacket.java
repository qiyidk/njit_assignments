package njit.cs656.qiyi.DV;

/**
 * <p>
 * RoutingPacket
 * </p>
 *
 * @author qiyi
 * @version 2015-12-4
 */
public class RoutingPacket {
    int sourceid; /* id of sending router sending this pkt */
    int destid; /* id of router to which pkt being sent
    (must be an immediate neighbor) */
    int mincost[]=new int [4]; /* min cost to node 0 ... 3 */
    public int getDestid() {
    return destid;
    }
    public void setDestid(int destid) {
    this.destid = destid;
    }
    public int[] getMincost() {
    return mincost;
    }
    public void setMincost(int[] mincost) {
    this.mincost = mincost;
    }
    public int getSourceid() {
    return sourceid;
    }
    public void setSourceid(int sourceid) {
    this.sourceid = sourceid;
    }
}
