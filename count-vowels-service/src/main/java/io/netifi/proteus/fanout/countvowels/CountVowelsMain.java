package io.netifi.proteus.fanout.countvowels;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.isvowel.VowelCheckerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/** Starts Vowel Counter */
public class CountVowelsMain {
  private static final Logger logger = LogManager.getLogger(CountVowelsMain.class);

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
            .group("fanout.vowelcounter") // Group name of service
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

    logger.info("starting vowel counter");

    netifi
        .connect("fanout.isVowel")
        .doOnNext(
            socket -> {
              System.out.println("looking for isVowel service");
              VowelCheckerClient client = new VowelCheckerClient(socket, registry);

              // Add Service to Respond to Requests
              netifi.addService(new VowelCounterServer(new DefaultVowelCounter(client), registry));
            })
        .block();

    logger.info("vowel counter started");

    netifi.onClose().block();
  }
}
