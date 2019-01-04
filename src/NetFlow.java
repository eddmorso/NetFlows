import java.util.Date;

public class NetFlow {
    Date date;
    long bytesIn, bytesOut, packetsIn, packetsOut;
    String appName = null;
    String destinationAddress = null;
    int protocolNumber;
    String sourceAddress = null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getBytesIn() {
        return bytesIn;
    }

    public void setBytesIn(long bytesIn) {
        this.bytesIn = bytesIn;
    }

    public long getBytesOut() {
        return bytesOut;
    }

    public void setBytesOut(long bytesOut) {
        this.bytesOut = bytesOut;
    }

    public long getPacketsIn() {
        return packetsIn;
    }

    public void setPacketsIn(long packetsIn) {
        this.packetsIn = packetsIn;
    }

    public long getPacketsOut() {
        return packetsOut;
    }

    public void setPacketsOut(long packetsOut) {
        this.packetsOut = packetsOut;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }
}
