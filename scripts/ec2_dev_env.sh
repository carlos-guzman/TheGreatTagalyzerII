#!/bin/bash
# Setup an EC2 development environment (with virtual env).

virtual_envs=largescale

apt-get update
apt-get install git
apt-get install libmysqlclient-dev

export DEBIAN_FRONTEND=noninteractive
apt-get -q -y install mysql-server

apt-get install python-dev python-pip
pip install virtualenv

mkdir -p $virtual_envs
cd $virtual_envs
virtualenv --system-site-packages .

## The rest of these steps can be executed manually on the machine.
# source ./bin/activate
# pip install Django==1.8
# pip install django-debug-toolbar==1.3.2
# pip install MySQL-python==1.2.5
# echo "To activate run:"
# echo "\$ ./bin/activate"
# echo "To deactivate run:"
# echo "\$ deactivate"

## Get the source files
# git clone https://${gitserver}/scalica largescale
# git checkout single_host

## set up the database
# cd ${virtual_envs}/scalica/db
# ./install_db.sh
# cd ${virtual_envs}/scalica/web/scalica
# python manage.py migrate

## Start the dev server
# python manage.py runserver 0.0.0.0:8000
