#!/bin/bash

sleep 30s

/opt/mssql-tools/bin/sqlcmd -S $MSSQL_HOST_NAME -U SA -P $MSSQL_SA_PASSWORD -i /docker-entrypoint-initdb.d/init.sql