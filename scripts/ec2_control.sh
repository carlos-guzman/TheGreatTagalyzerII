#!/bin/bash

install_ec2_cli () {
  wget http://s3.amazonaws.com/ec2-downloads/ec2-api-tools.zip
  sudo mkdir /usr/local/ec2
  sudo unzip ec2-api-tools.zip -d /usr/local/ec2
}

start_ec2_dev () {
  ec2-run-instances ami-d05e75b8 -n 1 -f ec2_dev_env.sh -k beakerkey \
    -g sg-e70f0780 --instance-type t2.micro --subnet subnet-83de89da \
    --region us-east-1  
}
