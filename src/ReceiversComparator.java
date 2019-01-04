import java.util.Comparator;

public class ReceiversComparator implements Comparator<NetFlow> {

    public int compare(NetFlow n1, NetFlow n2){
        if(n1.bytesIn == n2.bytesIn){
            return 0;
        }else if(n1.bytesIn < n2.bytesIn){
            return 1;
        }else
            return -1;
    }
}
