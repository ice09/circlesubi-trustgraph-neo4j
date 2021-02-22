## Run Trustgraph on Neo4j in Docker Mode

* Unzip provided import ZIP file in root to `neo4j/import` directory
* Run Neo4j with `start-docker-neo4j.bat`
    * (Opt.) Install [Neo4j-Desktop](https://neo4j.com/download/) to connect to Docker instance
    * Connect to "remote" Neo4j instance with user `neo4j` and password `123654789`
    * Follow the steps in https://gist.github.com/ice09/87509a1ecafd9ddd73e02c6ebc5b005d#file-load_events_from_csv-cypher
    * Test the import with Cypher Query: https://gist.github.com/ice09/87509a1ecafd9ddd73e02c6ebc5b005d#file-shortest_path_trustlines-cypher
* Run Trustgraph with `start-docker-trustgraph.bat`

## Call API for Trustgraph

This call should give you a transitive payment path of 3 hops for paying from `trustee` to `truster`.

`curl -X GET "http://localtest.me:8889/trust/0x249fa3ecd95a53f742707d53688fcafbbd072f33/0x945CaC6047B1f58945ed2aafA5BaeD96A31faa4c/50" -H "accept: */*"`


