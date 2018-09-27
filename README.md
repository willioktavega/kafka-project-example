# kafka-project-example

Case: Meetup Reservation (http://stream.meetup.com/2/rsvps)

# Config

Already set in each files inside `kafka-properties`

### How to Run

* This is maven project, make sure your device is installed maven.
* Download apache kafka [here](https://kafka.apache.org/downloads), then unzip. In this case I used `kafka_2.12-1.1.0`.
* Copy all files in `kafka-properties` folder, then paste into `config` folder which located in apache kafka folder.
* Run zookeeper `./bin/zookeeper-server-start.sh config/zookeeper.properties`
* Run kafka `./bin/kafka-server-start.sh config/server.properties`
* Create topic `bin/kafka-topics.sh --create --zookeeper localhost:3000 --replication-factor 1 --partitions 1 --topic test`
* Run `MeetupRsvpProducer.scala` to start send message `meetup rsvp data` into kafka.
* Run `MeetupRsvpConsumer.scala` to dump out `meetup rsvp data`.

#
# Still Develop