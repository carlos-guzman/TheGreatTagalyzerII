#!/bin/bash

mysqld_safe --defaults-file=/etc/mysql2/my.cnf --user=mysql &
mysqld_safe --defaults-file=/etc/mysql3/my.cnf --user=mysql &
