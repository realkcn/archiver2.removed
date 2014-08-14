#!/bin/sh

MONGOD=/usr/local/opt/mongodb/bin/mongod
MONGOS=/usr/local/opt/mongodb/bin/mongos
WORKDIR=/opt/mongodb

ARCHIVERDBUSER=archiver
ARCHIVERDBPASS=test1234
ADMINUSER=admin
ADMINPASS=admin123
#chunk size xxM
CHUNKSIZE=64
SECUREOPTION='--setParameter enableLocalhostAuthBypass=0 --keyFile=${WORKDIR}/keyfile'

${MONGOD} --shardsvr --replSet shard1 --port 27017 --dbpath ${WORKDIR}/shard1 --oplogSize 100 --logpath ${WORKDIR}/shard1.log --logappend --fork --keyFile=${WORKDIR}/keyfile
${MONGOD} --shardsvr --replSet shard2 --port 27018 --dbpath ${WORKDIR}/shard2 --oplogSize 100 --logpath ${WORKDIR}/shard2.log --logappend --fork --keyFile=${WORKDIR}/keyfile

${MONGOD} --configsvr --dbpath ${WORKDIR}/config/ --port 20000 --logpath ${WORKDIR}/config.log --logappend --fork --keyFile=${WORKDIR}/keyfile

${MONGOS} --configdb 127.0.0.1:20000 --port 30000 --chunkSize ${CHUNKSIZE} --logpath ${WORKDIR}/mongos.log --logappend --fork --keyFile=${WORKDIR}/keyfile


function init() {
#connect to shard1
mongo --port 27017
use admin
db.createUser({ user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
use archiver
db.createUser({ user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
#connect to shard2
mongo --port 27018
use admin
db.createUser({ user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
use archiver
db.createUser({ user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
#connect to config

#connect to mongos
mongo --port 30000

use admin;
db.addUser( { user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ] } )
use config;
db.addUser( { user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ] } )

db.runCommand({addshard:"127.0.0.1:27017"})
db.runCommand({addshard:"127.0.0.1:27018"})

#enable shard
db.runCommand({enablesharding:'archiver'});
db.runCommand({shardcollection:"archiver.thread", key:{_boardid:1}});
}