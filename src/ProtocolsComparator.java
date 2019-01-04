import java.util.Comparator;

public class ProtocolsComparator implements Comparator<NetFlow> {
    public int compare(NetFlow n1, NetFlow n2){
        if(n1.protocolNumber == n2.protocolNumber){
            return 0;
        }else if(n1.protocolNumber < n2.protocolNumber){
            return 1;
        }else
            return -1;
    }
}
