package io.netifi.proteus.fanout.randomchar;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.netifi.proteus.Netifi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Starts the Random Char Server */
public class RandomCharMain {
  private static final Logger logger = LogManager.getLogger(RandomCharMain.class);

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

    AtlasMeterRegistry registry =
        new AtlasMeterRegistry(
            new AtlasConfig() {
              @Override
              public Duration step() {
                return Duration.ofSeconds(10);
              }

              @Override
              public String get(String k) {
                return null;
              }

              @Override
              public boolean enabled() {
                return false;
              }

            });
    
    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.randomCharGenerator") // Group name of service
            .destination(destination)
            .accountId(accountId)
            .minHostsAtStartup(minHostsAtStartup)
            .poolSize(poolSize)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .meterRegistry(registry)
            .build();

    // Add Service to Respond to Requests
    netifi.addService(new RandomCharGeneratorServer(new DefaultRandomCharGenerator(), registry));

    netifi.onClose().block();
  }
}
