#!/bin/bash

cd ./kafka/kafka_2.11-1.1.0
sudo nohup bin/zookeeper-server-start.sh config/zookeeper.properties > /dev/null 2>&1 &
sudo bin/kafka-server-start.sh -daemon config/server.properties &
