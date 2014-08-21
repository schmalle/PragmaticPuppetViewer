PragmaticPuppetViewer
=====================

Simple Puppet Information UI 

For information about Puppet see: http://puppetlabs.com/


Functions:

- see outstanding client certificate requests
- sign outstanding clients
- see information about available clients


Requirements:

needs geolocation IP database from Maxmind (http://geolite.maxmind.com)
needs Play Framework (http://www.playframework.com/)
needs lsync (https://code.google.com/p/lsyncd/)
Mysql database / Maria database
/tmp directory 

Install:

wget -N http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz
Copy this database to /data/GeoIP.dat

Copy the file ./conf/ppv.conf to /etc/ppv.conf

Create the database by sourcing the file installmysql.sql.

Special notes:

Lsyncd can be tricky to be installed on OS X, e.g. for Mavericks download the xnu
package from Apple and use the following command to create necessary files

./configure --without-inotify --with-fsevents CFLAGS="-I /Users/XYZ/xnu" (from github)


If you have any questions, dont hesitate to contact me at

markus-schmall@t-online.de

or

#flakedev on twitter




