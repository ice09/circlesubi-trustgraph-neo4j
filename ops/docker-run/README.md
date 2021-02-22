## Run Trustgraph on Neo4j in Docker Mode

* Create new folder and copy batch scripts to this folder
* Create sub-folder `neo4j` with folders: `import`, `data`, `logs` and `plugins`
* Unzip provided import ZIP file in root to `neo4j/import` directory
* Run Neo4j with `start-docker-neo4j.bat`
    * (Opt.) Install [Neo4j-Desktop](https://neo4j.com/download/) to connect to Docker instance
    * Connect to "remote" Neo4j instance with user `neo4j` and password `123654789`
    * Follow the steps in https://gist.github.com/ice09/87509a1ecafd9ddd73e02c6ebc5b005d#file-load_events_from_csv-cypher
    * Test the import with Cypher Query: https://gist.github.com/ice09/87509a1ecafd9ddd73e02c6ebc5b005d#file-shortest_path_trustlines-cypher
* Run Trustgraph with `start-docker-trustgraph.bat`

## Call API for Trustgraph

This call should give you a transitive payment path of 3 hops for paying from `trustee` to `truster`.

`curl -X GET "http://localtest.me:8889/trust/0x9FaA986911c4423ad9895805BEE4a566DD11120D/0xC29D7Ab348b2dA3B59eE80A8492bEDFaDf350AEF/50" -H "accept: */*"`


