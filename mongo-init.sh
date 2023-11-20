#!/bin/bash
mongosh <<EOF
use shopping
db.createUser(
  {
    user: "shopping-service",
    pwd: "Password123!",
    roles: [
      {
        role: "readWrite",
        db: "shopping"
      }
    ]
  }
);

use order
db.createUser(
  {
    user: "order-service",
    pwd: "Password123!",
    roles: [
      {
        role: "readWrite",
        db: "order"
      }
    ]
  }
);
EOF
