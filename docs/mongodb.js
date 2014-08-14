use archiver

db.createUser({ user: "archiver",
    pwd: "test1234",
    roles: [
    { role: "dbOwner", db: "archiver" }
    ]
    })

