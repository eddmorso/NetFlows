import java.util.Comparator;

public class TransmittersComparator implements Comparator<NetFlow> {
    public int compare(NetFlow n1, NetFlow n2){
        if(n1.bytesOut == n2.bytesOut){
            return 0;
        }else if(n1.bytesOut < n2.bytesOut){
            return 1;
        }else
            return -1;
    }
}
