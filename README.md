# Hands on saga pattern

This is a hands-on approach showcasing how to implement the sage pattern in a microservice architecture to coordinate distributed transaction.

There are two ways of coordination sagas:
- [Choreography](choreography) - each local transaction publishes domain events that trigger local transactions in other services
- [Orchestration](orchestration) - an orchestrator (object) tells the participants what local transactions to execute

## License

This software is provided under the MIT open source license, read the `LICENSE` file for details.