**Start the Kafka Cluster**

To run Zookeeper and Kafka using docker-compose.yml. 
Execute below command to start the Kafka cluster:

`$  docker-compose up -d`

**Verifying the Kafka Cluster**
In order to verify that the Kafka cluster is running successfully, 
let’s run the following command to see the running containers:

`$ docker-compose ps`

`NAME                IMAGE                    COMMAND                  SERVICE        CREATED          STATUS          PORTS
kafka               wurstmeister/kafka       "start-kafka.sh"         kafka          27 seconds ago   Up 27 seconds   0.0.0.0:9092->9092/tcp
zookeeper           wurstmeister/zookeeper   "/bin/sh -c '/usr/sb…"   zookeeper      3 minutes ago    Up 3 minutes    22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp
`
**Creating a Kafka Topic**

`$ docker-compose exec kafka kafka-topics.sh --create --topic baeldung_linux
--partitions 1 --replication-factor 1 --bootstrap-server kafka:9092`

**Publishing and Consuming Messages**

With the Kafka topic in place, let’s publish and consume some messages. 
First, start a consumer by running the following command:

`$ docker-compose exec kafka kafka-console-consumer.sh --topic baeldung_linux
--from-beginning --bootstrap-server kafka:9092`

Let’s also look at publishing the data to this Kafka topic:

`$ docker-compose exec kafka kafka-console-producer.sh --topic baeldung_linux
--broker-list kafka:9092`