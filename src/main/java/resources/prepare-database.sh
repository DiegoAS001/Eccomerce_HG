#!/bin/bash

# Este script prepara o banco de dados do projeto `lojaweb` para ser usado
# nos laboratórios.


# Iniciando o serviço do MySQL no laboratório
if [ ! `pidof mysqld` ]; then
    echo -n "Starting MySQL..."

    # Starting MySQL
    sudo /etc/init.d/mysql start > /dev/null

    # Waiting MySQL to start
    while [ ! `pidof mysqld` ]; do
        echo -n "."
        sleep 0.1
    done
    echo "Done!"
else
    echo "MySQL is running."
fi


# Criando o banco de dados
echo "Creating database schema..."
mysql --protocol=tcp --port=3307 --user=root --password=root < schema.sql

# Carregando os dados de teste
echo "Loading data..."
mysql --protocol=tcp --port=3307 --user=root --password=root loja < data.sql


