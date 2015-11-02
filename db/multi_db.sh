#!/bin/bash

# This script mostly works.
# Apparmor may cause some issues, and is not easy to configure correctly.
# It can be overriden with
# $ sudo aa-complain /usr/sbin/mysqld

i=$1
port=$[3306+$i]

mkdir /var/lib/mysql$i
chown -R mysql.mysql /var/lib/mysql$i/
mkdir /var/log/mysql$i
chown -R mysql.mysql /var/log/mysql$i
cp -R /etc/mysql/ /etc/mysql$i

cd /etc/mysql$i/
sed -i "s/3306/$port/g" my.cnf
sed -i "s/mysqld.sock/mysqld$i.sock/g" my.cnf
sed -i "s/mysqld.pid/mysqld$i.pid/g" my.cnf
sed -i "s/var\/lib\/mysql/var\/lib\/mysql$i/g" my.cnf
sed -i "s/var\/log\/mysql/var\/log\/mysql$i/g" my.cnf

mysql_install_db --user=mysql --datadir=/var/lib/mysql$i/

mysqld_safe --defaults-file=/etc/mysql$i/my.cnf --user=mysql &
