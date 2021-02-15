## Circles UBI Trustgraph Visualization and Shortest Path Calculation with Neo4j

(see https://gist.github.com/ice09/87509a1ecafd9ddd73e02c6ebc5b005d for quick overview)

This projects consists of two loosly related sub-projects:
* Circles UBI-Trustmonitor
* Circles UBI-Trustgraph-Neo4j

The Trustmonitor reads all `Trust` events of the `Hub` Smart Contract and calls an (external) service with the retrieved data.

The Trustgraph accepts incoming requests with `truster`, `trustee`, `blockNumber` and `amount` data, stores this data into a Neo4j Graph Database and can calculate the shortest Trust-Path between two Users.

## Usage

When starting the application, the Monitor listens from Block `start.block` in `application.properties`. Though it's possible to start with 12529458, which is the deployment blocknumber of the `Hub` Smart Contract in the xDai Chain, the processing would then take some hours to catch up with the current state.  
Therefore, it is much better to import the provided file `trustgraph_14487468.csv` into Neo4j directly, which takes ~30 secs and then set the `start.block` to 14487468.

### Importing the Bootstrap Data CSV into Neo4j

* Create a new Neo4j database, use settings `dbms.memory.heap.initial_size=2G` and `dbms.memory.heap.max_size=4G`, set password to 123654789 or change password in `application.properties`

* Use the Cypher statements from this gist to import the data, copy the CSV file to the correct import folder before.

![](docs/img/neo4j.png)

## Circles UBI-Trustmonitor

After starting the application, a scheduler will run which monitors the Events of the `Hub` Smart Contract for `Trust` Events. If an event is found, a REST POST call is used to store the data in an external data source. In this demo, the external data source is the Trustgraph service.

## Circles UBI-Trustgraph Neo4j

The Trustgraph service provides an API which is described at http://localhost:8889/swagger-ui.html



