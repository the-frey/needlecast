#!/bin/bash

sudo mkdir ./kafka
sudo wget http://apache.mirror.anlx.net/kafka/1.1.0/kafka_2.11-1.1.0.tgz
sudo mv kafka_2.11-1.1.0.tgz ./kafka/kafka_2.11-1.1.0.tgz
cd ./kafka
sudo tar -xzf kafka_2.11-1.1.0.tgz
