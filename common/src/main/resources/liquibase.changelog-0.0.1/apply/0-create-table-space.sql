----------------- Directory table space  -----------------
CREATE TABLESPACE "DIR" DATAFILE '/u02/oradata/sigma01/directory.dbf' SIZE 1024M
AUTOEXTEND ON NEXT 100M MAXSIZE 32767M
LOGGING ONLINE PERMANENT BLOCKSIZE 8192
EXTENT MANAGEMENT LOCAL AUTOALLOCATE DEFAULT
NOCOMPRESS  SEGMENT SPACE MANAGEMENT AUTO;
----------------- Directory table space end  -----------------
