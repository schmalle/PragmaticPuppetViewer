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


INSERT INTO Nodes VALUES (1, "testnode", "firstseen", "lastseen", "127.0.0.1", "ok", "puppetversion", "configversion");
INSERT INTO Nodes VALUES (2, "testnode_orig", "firstseen", "lastseen", "127.0.0.1", "ok", "puppetversion", "configversion");
INSERT INTO Nodes VALUES (3, "testnode_changed", "firstseen", "lastseen", "127.0.0.1", "changed", "puppetversion", "configversion");
INSERT INTO Nodes VALUES (4, "testnode_failed", "firstseen", "lastseen", "127.0.0.1", "failed", "puppetversion", "configversion");


GRANT ALL PRIVILEGES
       ON PPR.*
       TO 'ppruser'@'localhost'
       IDENTIFIED BY 'pprpw100pw200';


