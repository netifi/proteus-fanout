package io.netifi.proteus.fanout.randomchar;

import io.netifi.proteus.Netifi;

/** Starts the Random Char Server */
public class RandomCharMain {

  public static void main(String... args) throws Exception {
    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.randomCharGenerator") // Group name of service
            .accountId(100)
            .minHostsAtStartup(1)
            .poolSize(1)
            .accessKey(7685465987873703191L)
            .accessToken("PYYgV9XHSJ/3KqgK5wYjz+73MeA=")
            .host("localhost") // Proteus Router Host
            .port(8001) // Proteus Router Port
            .build();

    // Add Service to Respond to Requests
    netifi.addService(new RandomCharGeneratorServer(new DefaultRandomCharGenerator()));

    netifi.onClose().block();
  }
}
