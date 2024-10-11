package illumio.VpcFlowLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StressTest {
    public static void main(String[] args) {
        String lookupFile = "stress_lookup.csv";
        String flowLogFile = "stress_flow_logs.txt";
        String outputFile = "stress_output_counts.txt";

        try {
            Map<String, String> lookupTable = FlowLogParser.loadLookupTable(lookupFile);
            List<VpcFlowLog> flowLogs = FlowLogParser.parseFlowLogs(flowLogFile);

            Map<String, Integer> tagCounts = new HashMap<>();
            Map<String, Integer> portProtocolCounts = new HashMap<>();

            for (VpcFlowLog log : flowLogs) {
                String key = log.getDstPort() + "," + FlowLogParser.getProtocolString(log.getProtocol());
                String tag = lookupTable.getOrDefault(key.toLowerCase(), "Untagged");
                System.out.println(key + " "  + tag);
                tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
                portProtocolCounts.put(key, portProtocolCounts.getOrDefault(key, 0) + 1);
            }

            FlowLogParser.writeOutput(tagCounts, portProtocolCounts, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
