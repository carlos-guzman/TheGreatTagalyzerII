sudo apt-get update
sudo apt-get install gcc
sudo apt-get install python-dev python-pip
sudo apt-get install apache2 apache2-dev
sudo apt-get install libmysqlclient-dev

mkdir /home/ubuntu/tmp
chmod -R 777 /home/ubuntu/tmp

# In working dir
wget https://github.com/GrahamDumpleton/mod_wsgi/archive/4.4.13.tar.gz
tar xzvf 4.4.13.tar.gz
cd mod_wsgi-4.4.13/
./configure
make
sudo make install
# Enable the module
sudo sh -c "echo 'LoadModule wsgi_module /usr/lib/apache2/modules/mod_wsgi.so' > /etc/apache2/mods-available/wsgi.load"
sudo a2enmod wsgi
sudo service apache2 restart
make clean

#install a virtualenv
sudo pip install virtualenv
sudo mkdir /var/www/site
cd /var/www/site
sudo virtualenv --system-site-packages .
source ./bin/activate
sudo pip install Django==1.8
sudo pip install MySQL-python==1.2.5
sudo pip install django-debug-toolbar==1.3.2

# Get the python source files/ Git or tarball

# Use the following config.
cat <<EOF > /etc/apache2/sites-available/scalica.conf
WSGIScriptAlias / /var/www/site/scalica/scalica/wsgi.py
WSGIDaemonProcess scalica python-path=/var/www/site/scalica:/var/www/site/lib/python2.7/site-packages
WSGIProcessGroup scalica
<Directory /var/www/site/scalica/scalica>
  <Files wsgi.py>
    Require all granted
  </Files>
</Directory>
EOF
sudo a2ensite scalica
sudo service apache2 reload
# We should be able to serve now.

# TODO: Collect static file
# TODO: Connect to database.


