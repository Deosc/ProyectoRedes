import subprocess
import os
import re
import ping_logger
import consoleExecute as consoleExecute


SNMP_GET_ROUTABLE_CMD = ['snmpwalk', '-v', '2c', '-c', 'public', '%s', '.1.3.6.1.2.1.4.24.4.1.16']


def scan_networks(networks):
    hosts = []
    command = " nmap --min-parallelism 100  {}   -sL --open  -oG -  "
    stringReturn = ""
    for line in consoleExecute.run_command(command.format(networks)):
        lineDecoded = line.decode('utf-8').strip()
        if(lineDecoded.startswith("Host:")):
            host = lineDecoded.split(" ")[1]
            if(  (not host.endswith("0")) and (not host.endswith("3")) ):
                hosts.append(host)

    hosts = list(dict.fromkeys(hosts))
    return hosts



def get_networks(snmp_host):
    SNMP_GET_ROUTABLE_CMD[5] = snmp_host
    output = subprocess.check_output(SNMP_GET_ROUTABLE_CMD).decode('UTF-8')
    networks = ""
    for line in output.split('\n'):
        line = line.strip()
        if len(line) != 0:
            match = re.match(r"iso\.3\.6\.1\.2\.1\.4\.24\.4\.1\.16\.(.+)", line)
            if match:
                ip_row = match.groups()[0]
                matches = re.findall(r"(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\.?)", ip_row)
                ip = matches[0][:-1]
                if (ip != '0.0.0.0'):
                    networks = networks +" " + f"{ip}/30"
    return networks

def main_implementation(param):
    snmp_host = param
    networks = get_networks(snmp_host)
    print(networks)
    hosts = scan_networks(networks)
    print(hosts)
    reachable, unreachable = ping_logger.ping_log(hosts)
    print (reachable)
    print (unreachable)
    return  reachable,unreachable



if __name__ == '__main__':
    snmp_host = "10.0.1.2"
    networks = get_networks(snmp_host)
    print(networks)
    hosts = scan_networks(networks)
    print(hosts)
    reachable, unreachable = ping_logger.ping_log(hosts)
    print (reachable)
    print (unreachable)