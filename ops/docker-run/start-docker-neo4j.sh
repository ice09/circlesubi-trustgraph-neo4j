workdir=`dirname $0`
docker run --name circlesubi-neo4j -p7687:7687 -d -v $PWD/$workdir/neo4j/data:/data -v $PWD/$workdir/neo4j/logs:/logs -v $PWD/$workdir/neo4j/import:/var/lib/neo4j/import -v $PWD/$workdir/neo4j/plugins:/plugins --env NEO4J_AUTH=neo4j/f6iVPwhZZ9 --env NEO4J_dbms_memory_pagecache_size=768M --env NEO4J_dbms_memory_heap_max__size=2048M neo4j:latest
