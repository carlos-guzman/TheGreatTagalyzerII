#!/bin/bash

# To use with non-standard mysql instances. Pass -h <hostname> -P <port>

sql_file=scalica.sql

# Change for other DBs.
PORT=3308

set -x
if [ $# -gt 0 ]; then
  case $1 in
    "remove")
      sql_file=remove_scalica.sql
      ;;
    *)
      echo "unknown command: $1"
      exit 1
  esac
fi

mysql -u root -h 127.0.0.1 -P $PORT < ${sql_file}
