# Choreography-based saga

### How to use

Firstly you need to start all backing services via docker-compose as shown bellow:
```bash
$ docker-compose -up -d
```

After docker-compose startup finishes, you need to build and run the individual instances. On each of `customer/`, `order/` and `stock` directories, execute: 

```bash
$ mvn clean spring-boot:run
```

Instructions on how to use the implemented API's are available at the respective directories [customer](customer), [order](order) and [stock](stock).