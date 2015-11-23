#!/bin/bash
# script to start two more instances of mysql on the same machine.
# See the installation script in this directory.

mysqld_safe --defaults-file=/etc/mysql2/my.cnf --user=mysql &
mysqld_safe --defaults-file=/etc/mysql3/my.cnf --user=mysql &
