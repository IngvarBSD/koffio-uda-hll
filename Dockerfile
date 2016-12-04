FROM cassandra:2.2.8

# copy the cassandra config with the permission for user defined functions
COPY ./cassandra.yaml /etc/cassandra/cassandra.yaml

# copy the jar with out implementation of hll functions
COPY ./hll.jar /usr/share/cassandra/lib/