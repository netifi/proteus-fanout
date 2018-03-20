package io.netifi.proteus.fanout.randomstring;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient;
import io.prometheus.client.exporter.PushGateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

/** Starts the Random String Server */
public class RandomStringMain {
  private static final Logger logger = LogManager.getLogger(RandomStringMain.class);

  public static void main(String... args) throws Exception {

    long accountId = Long.getLong("ACCOUNT_ID", 100);
    int minHostsAtStartup = Integer.getInteger("MIN_HOSTS_AT_STARTUP", 1);
    int poolSize = Integer.getInteger("POOL_SIZE", 1);
    long accessKey = Long.getLong("ACCESS_KEY", 7685465987873703191L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "PYYgV9XHSJ/3KqgK5wYjz+73MeA=");
    String host = System.getProperty("ROUTER_HOST", "localhost");
    int port = Integer.getInteger("ROUTER_PORT", 8001);
    String destination = UUID.randomUUID().toString();

    System.out.println("system properties [");
    System.getProperties()
        .forEach(
            (k, v) -> {
              System.out.print(k + ": " + v + ", ");
            });

    System.out.println("\n]");

    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.randomStringGenerator") // Group name of service
            .destination(destination)
            .accountId(accountId)
            .minHostsAtStartup(minHostsAtStartup)
            .poolSize(poolSize)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .build();

    PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    netifi
        .connect("fanout.randomCharGenerator")
        .doOnNext(
            socket -> {
              RandomCharGeneratorClient client = new RandomCharGeneratorClient(socket, registry);

              // Add Service to Respond to Requests
              netifi.addService(
                  new RandomStringGeneratorServer(new DefaultRandomStringGenerator(client), registry));
            })
        .block();

    // Push metrics to Prometheus
    PushGateway pg = new PushGateway("edge.prd.netifi.io:9091");
    Flux.interval(Duration.ofSeconds(5))
        .publishOn(Schedulers.single())
        .subscribe(i -> {
          try {
            pg.pushAdd(registry.getPrometheusRegistry(), "fanout.randomStringGenerator", Collections.singletonMap("instance", destination));
          } catch (IOException e) {
            logger.error(e);
          }
        });

    netifi.onClose().block();
  }
}
