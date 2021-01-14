# DB resources
Put SQL statements required to recreate the database from scratch up to the 
latest version in this folder, for example `CREATE TABLE`, `CREATE INDEX`, 
`ALTER TABLE` and `INSERT INTO`.

Each database version has its own folder, starting with `1`. In this folder, 
put every bit of SQL needed to upgrade the database from version 0 to version 1.

Within each folder, put files with exactly one SQL statement each. The files 
shall be named with the database version, and an incremented number, starting 
with `1-1.sql` for the SQL statement that should be executed first.

Please look at the following folder structure and file contents for an example.

**Folder structure**:
```
resources/
|   db/
|   |   1/
|   |   |   1-1.sql
|   |   |   1-2.sql
|   |   |   1-3.sql
|   |
|   |   2/
|   |   |   2-1.sql
|   |
|   |   3/
|   |   |   3-1.sql
|   |   |   3-2.sql        
```

**File contents**:
```sql
/* In file: 1-2.sql */

CREATE TABLE IF NOT EXISTS cat
(
    id    INTEGER PRIMARY KEY,
    name  TEXT    NOT NULL,
    owner INTEGER,
    
    FOREIGN KEY (owner) REFERENCES owner(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

/* In file: 3-1.sql */

ALTER TABLE cat
ADD COLUMN age INTEGER NOT NULL DEFAULT 0;
```
