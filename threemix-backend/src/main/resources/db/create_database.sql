-- Run this script as the root user to create the threemix user and schema.

CREATE USER 'threemix'@'localhost' IDENTIFIED BY 'threemix';
CREATE SCHEMA threemix;
GRANT ALL ON threemix.* TO 'threemix'@'localhost';
