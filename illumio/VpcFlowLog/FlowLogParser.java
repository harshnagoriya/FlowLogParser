package illumio.VpcFlowLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class FlowLogParser {
    /**
     * Loads the lookup table from a CSV file.
     *
     * @param filename the path to the CSV file
     * @return a map of dstport/protocol combinations to tags
     * @throws IOException if an error occurs while reading the file
     */
    public static Map<String, String> loadLookupTable(String filename) throws IOException {
        Map<String, String> lookupTable = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String key = parts[0].trim() + "," + parts[1].trim();
                    lookupTable.put(key.toLowerCase(), parts[2].trim());
                }
            }
        }
        return lookupTable;
    }

    /**
     * Parses flow logs from a specified file into a list of VpcFlowLog objects.
     *
     * @param filename the path to the flow logs file
     * @return a list of VpcFlowLog objects
     * @throws IOException if an error occurs while reading the file
     */
    public static List<VpcFlowLog> parseFlowLogs(String filename) throws IOException {
        List<VpcFlowLog> logs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    VpcFlowLog log = VpcFlowLog.fromString(line);
                    logs.add(log);
                } catch (InvalidFlowLogException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return logs;
    }

    /**
     * Writes the tag counts to an output file.
     *
     * @param tagCounts  a map where the keys are tag names and the values are their
     *                   corresponding counts
     * @param outputFile the path to the output file
     * @throws IOException if an error occurs while writing to the file
     */
    public static void writeTagCounts(Map<String, Integer> tagCounts, String outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, true)))) {
            bw.write("Tag Counts:\n");
            bw.write("Tag,Count\n");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }

    /**
     * Writes the port/protocol combination counts to an output file.
     *
     * @param portProtocolCounts a map where the keys are port/protocol combinations
     *                           (formatted as "port,protocol")
     *                           and the values are their corresponding counts
     * @param outputFile         the path to the output file
     * @throws IOException if an error occurs while writing to the file
     */
    public static void writePortProtocolCounts(Map<String, Integer> portProtocolCounts, String outputFile)
            throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, true)))) {
            bw.write("\nPort/Protocol Combination Counts:\n");
            bw.write("Port,Protocol,Count\n");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                String[] parts = entry.getKey().split(",");
                bw.write(parts[0] + "," + parts[1] + "," + entry.getValue() + "\n");
            }
        }
    }

    /**
     * Converts a protocol number to its corresponding string representation.
     *
     * @param protocol the protocol number (e.g., 1 for ICMP, 6 for TCP, 17 for UDP)
     * @return the string representation of the protocol, or "unknown" if the
     *         protocol number is not recognized
     */
    public static String getProtocolString(int protocol) {
        switch (protocol) {
            case 1:
                return "icmp";
            case 6:
                return "tcp";
            case 17:
                return "udp";
            default:
                return "unknown";
        }
    }

    /**
     * Writes the combined output of tag counts and port/protocol combination counts
     * to an output file.
     *
     * @param tagCounts          a map of tag names to their corresponding counts
     * @param portProtocolCounts a map of port/protocol combinations to their counts
     * @param outputFile         the path to the output file
     * @throws IOException if an error occurs while writing to the file
     */
    public static void writeOutput(Map<String, Integer> tagCounts, Map<String, Integer> portProtocolCounts,
            String outputFile)
            throws IOException {
        new FileWriter(outputFile, false).close();
        writeTagCounts(tagCounts, outputFile);
        writePortProtocolCounts(portProtocolCounts, outputFile);
    }
}
