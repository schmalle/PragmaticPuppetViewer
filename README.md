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
needs Pla Framework (http://www.playframework.com/)
needs lsync (https://code.google.com/p/lsyncd/)
Mysql database / Maria database

Install:

wget -N http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz
Copy this database to /data/GeoIP.dat

Lsyncd can be tricky to be installed on OS X, e.g. for Mavericks download the xnu
package from Apple and use the following command to create necessary files

./configure --without-inotify --with-fsevents CFLAGS="-I /Users/XYZ/xnu" (from github)

Create the database by sourcing the file installmysql.sql.

If you have any questions, dont hesitate to contact me at

markus-schmall@t-online.de


