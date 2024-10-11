import csv
import random

def generate_lookup_file(filename, num_entries=10000):
    protocols = ['tcp', 'udp', 'icmp']
    dst_ports = [35264, 34756, 12244, 35264, 23644]
    tags = [f"Tag_{i}" for i in range(1, 201)]
    
    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file)
        
        writer.writerow(["DstPort", "Protocol", "Tag"])
        
        for _ in range(num_entries):
            dst_port = random.choice(dst_ports)
            protocol = random.choice(protocols)
            tag = random.choice(tags)
            
            writer.writerow([dst_port, protocol, tag])

generate_lookup_file("../stress_lookup.csv")
