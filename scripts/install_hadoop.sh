#!/bin/bash

core_conf=etc/hadoop/core-site.xml
hdfs_conf=etc/hadoop/hdfs-site.xml
maprd_conf=etc/hadoop/mapred-site.xml
hadoop_dir=./hadoop-2.7.1

# download 
download_hadoop() {
  wget http://apache.arvixe.com/hadoop/common/hadoop-2.7.1/hadoop-2.7.1.tar.gz
  tar xzvf hadoop-2.7.1.tar.gz
}

if [ ! -d "$hadoop_dir" ]; then
  download_hadoop
fi
cd $hadoop_dir

# Config files
echo "export JAVA_HOME=$JAVA_HOME" >> etc/hadoop/hadoop-env.sh

if [ -f $core_conf ]; then
  mv ${core_conf} ${core_conf}.orig
fi
cat <<EOF > $core_conf
<configuration>
  <property><name>fs.default.name</name>
    <value>hdfs://localhost:29000</value>
  </property>
</configuration>
EOF

if [ -f $hdfs_conf ]; then
  mv ${hdfs_conf} ${hdfs_conf}.orig
fi
cat <<EOF > $hdfs_conf
<configuration>
  <property><name>dfs.replication</name><value>1</value></property>
</configuration>
EOF

if [ -f $maprd_conf ]; then
  mv ${maprd_conf} ${hdfs_conf}.orig
fi
cat <<EOF > $maprd_conf
<configuration>
  <property>
    <name>mapred.job.tracker</name><value>localhost:29001</value>
  </property>
</configuration>
EOF

# Setup SSH password-less access
id_rsa="no_such_key"
if [ -f ~/.ssh/id_rsa ]; then
  echo Key Pair exists
  id_rsa=$(cat ~/.ssh/id_rsa)
else
  echo Creating Key Pair
  ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
fi
if grep -v -- "$id_rsa" ~/.ssh/authorized_keys >> /dev/null; then
  echo Adding public keys to authorized keys;
  cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys;
fi

# Format filesystem
bin/hdfs namenode -format

# Startup
echo To start HDFS run sbin/start-dfs.sh

