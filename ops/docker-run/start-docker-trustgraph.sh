workdir=`dirname $0`
docker run -d -p 8889:8889 -e SPRING_PROFILES_ACTIVE=monitor -e SPRING_CONFIG_LOCATION=/config/ -v $PWD/$workdir:/config --name circlesubi-trustgraph ice0nine/circlesubi-trustgraph:latest
