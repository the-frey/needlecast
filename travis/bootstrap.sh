#!/bin/bash

sudo chown -R travis ./travis/*
sudo mkdir -p /etc/leiningen/
sudo mv ./travis/profiles.clj /etc/leiningen/profiles.clj
sudo apt-get update && sudo apt-get install wget -yy
