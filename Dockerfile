FROM mcr.microsoft.com/mssql-tools:latest

USER root
SHELL ["/bin/bash", "-c"]


COPY ./init.sql /docker-entrypoint-initdb.d/
COPY ./entrypoint.sh /docker-entrypoint-initdb.d/

RUN chmod -R +x /docker-entrypoint-initdb.d
WORKDIR /docker-entrypoint-initdb.d

CMD ["bash", "./entrypoint.sh"]