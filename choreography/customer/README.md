## Customer

Create a new customer
```bash
$ curl localhost:8081/v1/customers -XPOST \
    -H 'Content-Type: application/json' \
    -d '{"name":"John Doe","creditLimit":2300}' | jq .
```

List existing customers
```bash
$ curl localhost:8081/v1/customers | jq .
```