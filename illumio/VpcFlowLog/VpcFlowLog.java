package illumio.VpcFlowLog;

/**
 * Represents a record from AWS VPC Flow Logs.
 * This class encapsulates details about network traffic for a network interface,
 * including source/destination addresses, ports, protocol, and action taken.
 * 
 * Reference: 
 * https://docs.aws.amazon.com/vpc/latest/userguide/flow-log-records.html
 */
public class VpcFlowLog {
    private int version;
    private String accountId;
    private String interfaceId;
    private String srcAddr;
    private String dstAddr;
    private int srcPort;
    private int dstPort;
    private int protocol;
    private long packets;
    private long bytes;
    private long start;
    private long end;
    private String action;

    public VpcFlowLog(int version, String accountId, String interfaceId, String srcAddr,
                      String dstAddr, int srcPort, int dstPort, int protocol,
                      long packets, long bytes, long start, long end, String action) {
        this.version = version;
        this.accountId = accountId;
        this.interfaceId = interfaceId;
        this.srcAddr = srcAddr;
        this.dstAddr = dstAddr;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.protocol = protocol;
        this.packets = packets;
        this.bytes = bytes;
        this.start = start;
        this.end = end;
        this.action = action;
    }

    public static VpcFlowLog fromString(String line) throws InvalidFlowLogException {
        String[] parts = line.split(" ");
        if (parts.length < 12) {
            throw new InvalidFlowLogException("Invalid log line: " + line);
        }
        
        return new VpcFlowLog(
            Integer.parseInt(parts[0]), 
            parts[1], 
            parts[2], 
            parts[3], 
            parts[4], 
            Integer.parseInt(parts[5]), 
            Integer.parseInt(parts[6]), 
            Integer.parseInt(parts[7]), 
            Long.parseLong(parts[8]), 
            Long.parseLong(parts[9]), 
            Long.parseLong(parts[10]), 
            Long.parseLong(parts[11]), 
            parts[12]
        );
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getSrcAddr() {
        return srcAddr;
    }

    public void setSrcAddr(String srcAddr) {
        this.srcAddr = srcAddr;
    }

    public String getDstAddr() {
        return dstAddr;
    }

    public void setDstAddr(String dstAddr) {
        this.dstAddr = dstAddr;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public long getPackets() {
        return packets;
    }

    public void setPackets(long packets) {
        this.packets = packets;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
