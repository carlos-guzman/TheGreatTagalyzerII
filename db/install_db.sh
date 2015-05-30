#!/bin/bash

sql_file=scalica.sql

set -x
if [ $# -gt 0 ]; then
  case $1 in
    "remove")
      sql_file=remove_scalica.sql
      ;;
    *)
      echo "unknown"
  esac
fi

mysql -u root -p < ${sql_file}
