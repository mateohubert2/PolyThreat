#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <IP/mask>"
    exit 1
fi

IP_CIDR=$1

HOST_COUNT=0

NMAP_OUTPUT=$(nmap -sn $IP_CIDR)

while IFS= read -r line; do
    if [[ "$line" == *"Nmap scan report for"* ]]; then
        ((HOST_COUNT++))
    fi
done <<< "$NMAP_OUTPUT"

echo "Number of active hosts on the network $IP_CIDR : $HOST_COUNT"
