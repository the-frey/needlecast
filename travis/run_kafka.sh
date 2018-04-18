#!/bin/bash

cd ./kafka/kafka_2.11-1.1.0
sudo bin/zookeeper-server-start.sh config/zookeeper.properties &
sudo bin/kafka-server-start.sh -daemon config/server.properties &
