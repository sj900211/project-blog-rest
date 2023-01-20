#!/bin/bash
set -e

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    echo "*:*:*:replicator:${POSTGRES_PASSWORD}" > ~/.pgpass
    chmod 0600 ~/.pgpass
    until ping -c 1 -W 1 postgres-main
    do
        echo "Waiting for main to ping..."
        sleep 1s
    done

    until pg_basebackup -h postgres-main -D ${PGDATA} -U replicator -vP -W
    do
        echo "Waiting for main to connect..."
        sleep 1s
    done

    sed -i 's/wal_level = hot_standby/wal_level = replica/g' ${PGDATA}/postgresql.conf

    cat > ${PGDATA}/recovery.conf <<EOF
standby_mode = on
primary_conninfo = 'host=postgres-main port=5432 user=replicator password=${POSTGRES_PASSWORD} application_name=postgres-replica'
primary_slot_name = 'node_a_slot'
EOF

    chown postgres:postgres ${PGDATA} -R
    chmod 700 ${PGDATA} -R
fi