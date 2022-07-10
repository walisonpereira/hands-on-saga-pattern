## Customer

Create a new order
```bash
$ curl localhost:8080/v1/orders -XPOST \
    -H 'Content-Type: application/json' \
    -d '{"customerId":"07fd2f78-1e02-46de-8873-236e259cf4c5","products":[{"id":"bb66a115-60b4-448e-8e34-f29066666ff9","value":89.9,"quantity":2}]}' | jq .
```

List existing orders into system
```bash
$ curl localhost:8080/v1/orders | jq .
```