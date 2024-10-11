import random
import time

def generate_flow_log_file(filename, target_size_mb=10):
    log_version = 2
    account_id = "123456789012"
    interface_id = "eni-xxxxxxxx"
    actions = ["ACCEPT", "REJECT"]
    dst_ports = [35264, 34756, 12244, 35264, 23644]
    
    with open(filename, mode='w') as file:
        while file.tell() < target_size_mb * 1024 * 1024:
            src_addr = f"{random.randint(1, 223)}.{random.randint(0, 255)}.{random.randint(0, 255)}.{random.randint(0, 255)}"
            dst_addr = f"{random.randint(1, 223)}.{random.randint(0, 255)}.{random.randint(0, 255)}.{random.randint(0, 255)}"
            src_port = random.randint(1024, 65535)
            dst_port = random.choice(dst_ports)
            protocol = random.choice([1, 6, 17])
            packets = random.randint(1, 1000)
            bytes_transferred = random.randint(1, 10000)
            start_time = int(time.time())
            end_time = start_time + random.randint(1, 1000)
            action = random.choice(actions)
            status = "OK"
            
            log_entry = f"{log_version} {account_id} {interface_id} {src_addr} {dst_addr} {src_port} {dst_port} {protocol} {packets} {bytes_transferred} {start_time} {end_time} {action} {status}\n"
            
            file.write(log_entry)

generate_flow_log_file("../stress_flow_logs.txt")
