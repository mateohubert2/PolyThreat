#!/bin/bash

FIRST_LINE=$(nmcli connection show --active | grep -A 1 NAME | sed "2p;d")

NAME=$(echo "$FIRST_LINE" | awk -F' ' '{print $1}')
TYPE=$(echo "$FIRST_LINE" | awk -F' ' '{print $3}')
INTER=$(echo "$FIRST_LINE" | awk -F' ' '{print $4}')

if [ "$TYPE" == "wifi" ]; then
    echo "The connection protocol used is Wi-Fi : $NAME"

    IFCONFIG_OUTPUT=$(ifconfig $INTER)
    NETMASK=$(echo "$IFCONFIG_OUTPUT" | grep 'netmask' | awk '{print $4}')
    IP=$(ifconfig $INTER 2>/dev/null | grep 'inet ' | awk '{print $2}')
    if [ -n "$NETMASK" ]; then
        echo "Interface subnet mask of $INTER is : $NETMASK"
        
        case "$NETMASK" in
            "255.0.0.0")     # /8
                USABLE_HOSTS=16777214
                bash NumberOfHosts.sh $IP/8
                ;;
            "255.128.0.0")   # /9
                USABLE_HOSTS=8388606
                bash NumberOfHosts.sh $IP/9
                ;;
            "255.192.0.0")   # /10
                USABLE_HOSTS=4194302
                bash NumberOfHosts.sh $IP/10
                ;;
            "255.224.0.0")   # /11
                USABLE_HOSTS=2097150
                bash NumberOfHosts.sh $IP/11
                ;;
            "255.240.0.0")   # /12
                USABLE_HOSTS=1048574
                bash NumberOfHosts.sh $IP/12
                ;;
            "255.248.0.0")   # /13
                USABLE_HOSTS=524286
                bash NumberOfHosts.sh $IP/13
                ;;
            "255.252.0.0")   # /14
                USABLE_HOSTS=262142
                bash NumberOfHosts.sh $IP/14
                ;;
            "255.254.0.0")   # /15
                USABLE_HOSTS=131070
                bash NumberOfHosts.sh $IP/15
                ;;
            "255.255.0.0")   # /16
                USABLE_HOSTS=65534
                bash NumberOfHosts.sh $IP/16
                ;;
            "255.255.128.0") # /17
                USABLE_HOSTS=32766
                bash NumberOfHosts.sh $IP/17
                ;;
            "255.255.192.0") # /18
                USABLE_HOSTS=16382
                bash NumberOfHosts.sh $IP/18
                ;;
            "255.255.224.0") # /19
                USABLE_HOSTS=8190
                bash NumberOfHosts.sh $IP/19
                ;;
            "255.255.240.0") # /20
                USABLE_HOSTS=4094
                bash NumberOfHosts.sh $IP/20
                ;;
            "255.255.248.0") # /21
                USABLE_HOSTS=2046
                bash NumberOfHosts.sh $IP/21
                ;;
            "255.255.252.0") # /22
                USABLE_HOSTS=1022
                bash NumberOfHosts.sh $IP/22
                ;;
            "255.255.254.0") # /23
                USABLE_HOSTS=510
                bash NumberOfHosts.sh $IP/23
                ;;
            "255.255.255.0") # /24
                USABLE_HOSTS=254
                bash NumberOfHosts.sh $IP/24
                ;;
            "255.255.255.128") # /25
                USABLE_HOSTS=126
                bash NumberOfHosts.sh $IP/25
                ;;
            "255.255.255.192") # /26
                USABLE_HOSTS=62
                bash NumberOfHosts.sh $IP/26
                ;;
            "255.255.255.224") # /27
                USABLE_HOSTS=30
                bash NumberOfHosts.sh $IP/27
                ;;
            "255.255.255.240") # /28
                USABLE_HOSTS=14
                bash NumberOfHosts.sh $IP/28
                ;;
            "255.255.255.248") # /29
                USABLE_HOSTS=6
                bash NumberOfHosts.sh $IP/29
                ;;
            "255.255.255.252") # /30
                USABLE_HOSTS=2
                bash NumberOfHosts.sh $IP/30
                ;;
            *)
                echo "Unrecognized subnet mask : $NETMASK"
                exit 1
                ;;
        esac
        echo "Number of usable hosts : $USABLE_HOSTS"

    else
        echo "Unable to find subnet mask for interface $INTER."
    fi

elif [ "$TYPE" == "ethernet" ]; then
    echo "The connection protocol used is Ethernet : $NAME"

    IFCONFIG_OUTPUT=$(ifconfig $INTER)
    NETMASK=$(echo "$IFCONFIG_OUTPUT" | grep 'netmask' | awk '{print $4}')
    IP=$(ifconfig $INTER 2>/dev/null | grep 'inet ' | awk '{print $2}')
    if [ -n "$NETMASK" ]; then
        echo "Interface subnet mask of $INTER is : $NETMASK"
        
        case "$NETMASK" in
            "255.0.0.0")     # /8
                USABLE_HOSTS=16777214
                bash NumberOfHosts.sh $IP/8
                ;;
            "255.128.0.0")   # /9
                USABLE_HOSTS=8388606
                bash NumberOfHosts.sh $IP/9
                ;;
            "255.192.0.0")   # /10
                USABLE_HOSTS=4194302
                bash NumberOfHosts.sh $IP/10
                ;;
            "255.224.0.0")   # /11
                USABLE_HOSTS=2097150
                bash NumberOfHosts.sh $IP/11
                ;;
            "255.240.0.0")   # /12
                USABLE_HOSTS=1048574
                bash NumberOfHosts.sh $IP/12
                ;;
            "255.248.0.0")   # /13
                USABLE_HOSTS=524286
                bash NumberOfHosts.sh $IP/13
                ;;
            "255.252.0.0")   # /14
                USABLE_HOSTS=262142
                bash NumberOfHosts.sh $IP/14
                ;;
            "255.254.0.0")   # /15
                USABLE_HOSTS=131070
                bash NumberOfHosts.sh $IP/15
                ;;
            "255.255.0.0")   # /16
                USABLE_HOSTS=65534
                bash NumberOfHosts.sh $IP/16
                ;;
            "255.255.128.0") # /17
                USABLE_HOSTS=32766
                bash NumberOfHosts.sh $IP/17
                ;;
            "255.255.192.0") # /18
                USABLE_HOSTS=16382
                bash NumberOfHosts.sh $IP/18
                ;;
            "255.255.224.0") # /19
                USABLE_HOSTS=8190
                bash NumberOfHosts.sh $IP/19
                ;;
            "255.255.240.0") # /20
                USABLE_HOSTS=4094
                bash NumberOfHosts.sh $IP/20
                ;;
            "255.255.248.0") # /21
                USABLE_HOSTS=2046
                bash NumberOfHosts.sh $IP/21
                ;;
            "255.255.252.0") # /22
                USABLE_HOSTS=1022
                bash NumberOfHosts.sh $IP/22
                ;;
            "255.255.254.0") # /23
                USABLE_HOSTS=510
                bash NumberOfHosts.sh $IP/23
                ;;
            "255.255.255.0") # /24
                USABLE_HOSTS=254
                bash NumberOfHosts.sh $IP/24
                ;;
            "255.255.255.128") # /25
                USABLE_HOSTS=126
                bash NumberOfHosts.sh $IP/25
                ;;
            "255.255.255.192") # /26
                USABLE_HOSTS=62
                bash NumberOfHosts.sh $IP/26
                ;;
            "255.255.255.224") # /27
                USABLE_HOSTS=30
                bash NumberOfHosts.sh $IP/27
                ;;
            "255.255.255.240") # /28
                USABLE_HOSTS=14
                bash NumberOfHosts.sh $IP/28
                ;;
            "255.255.255.248") # /29
                USABLE_HOSTS=6
                bash NumberOfHosts.sh $IP/29
                ;;
            "255.255.255.252") # /30
                USABLE_HOSTS=2
                bash NumberOfHosts.sh $IP/30
                ;;
            *)
                echo "Unrecognized subnet mask : $NETMASK"
                exit 1
                ;;
        esac
        echo "Number of usable hosts : $USABLE_HOSTS"

    else
        echo "Unable to find subnet mask for interface $INTER."
    fi
else
    echo "First active connection type is neither Wi-Fi nor Ethernet : $TYPE"
fi
