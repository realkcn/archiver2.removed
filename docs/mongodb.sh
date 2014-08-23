#!/bin/sh

USERID=mongodb
MONGOD="numactl --interleave=all /usr/bin/mongod"
MONGOS="/usr/bin/mongos"
#MONGOS=/usr/bin/mongos
WORKDIR=/data/work/mongodb

ARCHIVERDBUSER=archiver
ARCHIVERDBPASS=
ADMINUSER=admin
ADMINPASS=
#chunk size xxM
CHUNKSIZE=64
SECUREOPTION="--setParameter enableLocalhostAuthBypass=0 --keyFile=${WORKDIR}/keyfile"
BINDIPOPTION="--bind_ip 127.0.0.1"
SERVERBIND="--bind_ip 0.0.0.0"

startshard() {
su ${USERID} -s /bin/sh -c "${MONGOD} --shardsvr --replSet shard1 --pidfilepath ${WORKDIR}/shard1.pid --port 27017 --dbpath ${WORKDIR}/shard1 --oplogSize 100 --logpath ${WORKDIR}/log/shard1.log --logappend --fork ${SECUREOPTION} ${BINDIPOPTION}"
su ${USERID} -s /bin/sh -c "${MONGOD} --shardsvr --replSet shard2 --pidfilepath ${WORKDIR}/shard2.pid --port 27018 --dbpath ${WORKDIR}/shard2 --oplogSize 100 --logpath ${WORKDIR}/log/shard2.log --logappend --fork ${SECUREOPTION} ${BINDIPOPTION}"
}

startconfig() {
su ${USERID} -s /bin/sh -c "${MONGOD} --configsvr --dbpath ${WORKDIR}/config/ --pidfilepath ${WORKDIR}/configsvr.pid --port 20000 --logpath ${WORKDIR}/log/config.log --logappend --fork ${SECUREOPTION} ${BINDIPOPTION}"
}

startrouter() {
su ${USERID} -s /bin/sh -c "${MONGOS} --configdb 127.0.0.1:20000 --pidfilepath ${WORKDIR}/mongos.pid --port 30000 --chunkSize ${CHUNKSIZE} --logpath ${WORKDIR}/log/mongos.log --logappend --fork ${SECUREOPTION} ${SERVERBIND}"
}
start() {
startshard
startconfig
startrouter
}

stop() {
killall mongod
killall mongos
}

init() {
stop

mkdir ${WORKDIR}/shard1
mkdir ${WORKDIR}/shard2
mkdir ${WORKDIR}/config
mkdir ${WORKDIR}/log

chown -R ${USERID} ${WORKDIR}
SAVESECUREOPTION=${SECUREOPTION}
SECUREOPTION=
startshard
#connect to shard1

mongo --port 27017 <<EOF
config = {_id: 'shard1', members: [
    {_id: 0, host: '127.0.0.1:27017'}]};
rs.initiate(config);
EOF

#connect to shard2
mongo --port 27018 <<EOF
config = {_id: 'shard2', members: [
    {_id: 0, host: '127.0.0.1:27018'}]};
rs.initiate(config);
use admin
EOF

sleep 5
mongo --port 27017 <<EOF
use admin
db.createUser({ user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ]
    })
use archiver
db.createUser({ user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
EOF

mongo --port 27018 <<EOF
use admin
db.createUser({ user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ]
    })
use archiver
db.createUser({ user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
EOF
startconfig
#connect to config
mongo --port 20000 <<EOF
use admin
db.createUser({ user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ]
    })
EOF

startrouter
#connect to mongos
mongo --port 30000 <<EOF
use admin
db.runCommand({addshard:"shard1/127.0.0.1:27017"})
db.runCommand({addshard:"shard2/127.0.0.1:27018"})

db.runCommand({enablesharding:'archiver'});
db.runCommand({shardcollection:"archiver.thread", key:{boardid:'hashed'}});

db.createUser( { user: "${ADMINUSER}",
    pwd: "${ADMINPASS}",
    roles: [ "clusterAdmin","userAdminAnyDatabase","dbAdminAnyDatabase","readWriteAnyDatabase" ]
    })
use archiver;
db.createUser({ user: "${ARCHIVERDBUSER}",
    pwd: "${ARCHIVERDBPASS}",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })
EOF
stop
SECUREOPTION=${SAVESECUREOPTION}
}

dropall() {
mongo --port 30000 <<EOF
use archiver
db.article.remove({})
db.thread.remove({})
db.encodingURLMapping.remove({})
db.originArticleInfo.remove({})
db.fs.chunks.remove({})
db.fs.files.remove({})
EOF
}
case "$1" in
  restart)
	stop
#	sleep 5
	start
	;;
  start)
	start
	;;
  startshard)
	startshard
	;;
  startconfig)
	startconfig
	;;
  startrouter)
	startrouter
	;;
  stop)
	stop
	;;
#  init)
#	init
#	;;
  *)
	echo $"Usage: $0 {start|stop|restart}"
	exit 2
esac