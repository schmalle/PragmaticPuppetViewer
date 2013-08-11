DROP DATABASE IF EXISTS PPR;
CREATE DATABASE PPR;

use PPR;

CREATE TABLE Nodes(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                 name   TEXT,
                                 firstSeen TEXT,
                                 lastSeen TEXT,
                                 lastIP TEXT,
                                 status TEXT,
                                 puppetVersion TEXT,
                                 configurationVersion  TEXT);


#INSERT INTO Nodes VALUES (1, "testnode", "firstseen", "lastseen", "127.0.0.1", "ok", "puppetversion", "configversion");
#INSERT INTO Nodes VALUES (2, "testnode_orig", "firstseen", "lastseen", "127.0.0.1", "ok", "puppetversion", "configversion");


GRANT ALL PRIVILEGES
       ON PPR.*
       TO 'ppruser'@'localhost'
       IDENTIFIED BY 'pprpw100pw200';
