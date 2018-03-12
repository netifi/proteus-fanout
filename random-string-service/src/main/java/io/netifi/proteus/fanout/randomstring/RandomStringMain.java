package io.netifi.proteus.fanout.randomstring;

import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient;

/** Starts the Random String Server */
public class RandomStringMain {

  public static void main(String... args) throws Exception {

    long accountId = Long.getLong("ACCOUNT_ID", 100);
    int minHostsAtStartup = Integer.getInteger("MIN_HOSTS_AT_STARTUP", 1);
    int poolSize = Integer.getInteger("POOL_SIZE", 1);
    long accessKey = Long.getLong("ACCESS_KEY", 7685465987873703191L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "PYYgV9XHSJ/3KqgK5wYjz+73MeA=");
    String host = System.getProperty("ROUTER_HOST", "localhost");
    int port = Integer.getInteger("ROUTER_PORT", 8001);

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
            .accountId(accountId)
            .minHostsAtStartup(minHostsAtStartup)
            .poolSize(poolSize)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .build();

    netifi
        .connect("fanout.randomCharGenerator")
        .doOnNext(
            socket -> {
              RandomCharGeneratorClient client = new RandomCharGeneratorClient(socket);

              // Add Service to Respond to Requests
              netifi.addService(
                  new RandomStringGeneratorServer(new DefaultRandomStringGenerator(client)));
            })
        .block();

    netifi.onClose().block();
  }
}
