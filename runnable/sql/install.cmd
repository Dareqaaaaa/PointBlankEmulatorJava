@echo off
TITLE Setup SQL

SET PGBIN="C:\Program Files\PostgreSQL\9.3\bin\psql.exe"
SET PGDATABASE=projectx
SET PGHOST=localhost
SET PGPORT=5432
SET PGUSER=postgres
SET PGPASSWORD=2002

%PGBIN% -f public.sql

pause