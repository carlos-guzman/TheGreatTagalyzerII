#/bin/bash

if [ $# -ne 2 ]; then
  echo "Usage $0 <url> <output_file>"
  exit 1
fi

readonly url=$1
readonly o_file=$2

wget_output=$(wget --output-document=/dev/null $url \
  --max-redirect=0 2>&1 | grep HTTP | cut -d' ' -f 6-)
if [ "$wget_output" == "200 OK" ]; then
  echo "up"
else
  echo "down ($wget_output)"
fi
