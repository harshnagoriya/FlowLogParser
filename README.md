# Flow Log Tagging and Analysis

## Overview
This program parses AWS VPC Flow Log data, maps each log entry to corresponding tags based on a provided lookup table, and generates counts for tags and port/protocol combinations.

## Input Files
1. **Flow Log File**: A text file containing flow log entries in the format specified by AWS VPC Flow Logs version 2.
   - **Expected Format**: Space-separated values with 12 items.
   - **Example Filename**: `flow_logs.txt`
   - **Location**: Place this file in the root directory of the project.
2. **Lookup Table**: A CSV file with the following structure: ```dstport,protocol,tag```
   - **Example Filename**: `lookup.csv`
   - **Location**: Place this file in the root directory of the project.

## Output
The program generates an output file containing:
1. **Tag Counts**:

```bash
Tag,Count
sv_P2,1
sv_P1,2
Untagged,9
```

2. **Port/Protocol Combination Counts**:
```bash
Port,Protocol,Count
22,tcp,1 
23,tcp,1
```

## Implementation

### Design Considerations
- **VpcFlowLog Data Class**: 
   - Encapsulates all attributes of a flow log entry, making it easier to manage and access log data. This class structure promotes better organization and clarity, allowing for straightforward modifications and enhancements in the future.
   - Using a dedicated class also helps in validation, ensuring that each log entry is correctly formatted and adheres to expected data types.

- **InputStreamReader**:
   - This class provides a flexible way to read character streams, supporting various input types (like files). It allows for reading text data in a character encoding format, which is essential for handling any non-ASCII characters that might appear in the logs.
   - It works well with `BufferedReader`, which efficiently reads large files, improving performance by reducing the number of I/O operations.

### Assumptions
- The program only supports the default log format (version 2).
- Flow log entries are expected to contain exactly 12 items in a specified sequence.
- The lookup table can contain up to 10,000 mappings.
- The flow log file can be up to 10 MB in size.
- The program handles case insensitivity for tag matching.

### How to Compile and Run
1. **Requirements**: Ensure you have Java installed on your machine.
2. **Compiling**:
```bash
javac -d . FlowLogParser.java InvalidFlowLogException.java VpcFlowLog.java Main.java StressTest.java
```
3. **Running**:
```bash
   java illumio.VpcFlowLog.Main
```

### Example Files

Flow Log File: Create a file named flow_logs.txt with the appropriate format.
Lookup Table: Create a file named lookup.csv with the required mappings.

# Utils

The project includes a utils folder containing Python scripts to generate stress test files:
    generate_stress_flow_log_file.py: Generates a flow log file with random entries.
    generate_stress_lookup_file.py: Generates a lookup CSV file with random tag mappings.

### Testing

Use the provided StressTest class to run tests with larger datasets.
```bash
   java illumio.VpcFlowLog.StressTest
```
Random flow logs and lookup tables can be generated using the provided Python scripts.

### Code Structure

FlowLogParser: Handles loading the lookup table, parsing flow logs, and writing output files.  
InvalidFlowLogException: Custom exception for invalid log lines.  
Main: Entry point for executing the program, orchestrating loading, processing, and outputting results.  
VpcFlowLog: Represents individual flow log entries with methods to construct objects from strings.  

/illumio/VpcFlowLog  
├── FlowLogParser.java       # Parses the flow logs and manages lookup table loading  
├── InvalidFlowLogException.java # Custom exception for invalid flow log entries  
├── VpcFlowLog.java          # Represents a single flow log entry  
├── Main.java                # Entry point for executing the program  
└── StressTest.java          # Class for stress testing with large datasets  
/utils  
├── generate_stress_flow_log_file.py # Generates a stress test flow log file  
└── generate_stress_lookup_file.py    # Generates a stress test lookup CSV file  
/lookup.csv                  # Lookup table for tagging  
/flow_logs.txt               # Sample flow logs for analysis  

