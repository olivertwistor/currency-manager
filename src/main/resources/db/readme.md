# Database resources
In this folder, we will store SQL statements needed for upgrading and downgrading the database. Each separate statement must be in their separate files. *I will probably want to redo this system in the future, so multiple statements can be in one file, but for now this has to do.* 

We're grouping SQL files that belong to the same database upgrade/downgrade in separate subfolders, named according to this naming pattern: `[source version]-to-[target version]`. For example, the SQL needed to upgrade the database from version 0 to 1 is under the folder [0-to-1](0-to-1).
