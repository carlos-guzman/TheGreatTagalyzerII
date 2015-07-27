#!/bin/bash
# Setup virtualenv from a development environment

if [ $# -ne 1 ]; then
  echo "Usage $0 <project_name>"
  exit 1
fi

readonly project_name=$1

set x+
sudo pip install virtualenv
mkdir $project_name
cd $project_name
virtualenv --system-site-packages .

source ./bin/activate
pip install Django==1.8
echo "To activate run:"
echo "\$ ./bin/activate"
echo "To deactivate run:"
echo "\$ deactivate"

