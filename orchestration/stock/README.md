## Stock

Create a new stock item
```bash
$ curl localhost:8082/v1/products -XPOST \
    -H 'Content-Type: application/json' \
    -d '{"name":"LEGO - Minecraft","value":89.9,"quantity":45}' | jq .
```

List available stock items
```bash
$ curl localhost:8082/v1/products | jq .
```