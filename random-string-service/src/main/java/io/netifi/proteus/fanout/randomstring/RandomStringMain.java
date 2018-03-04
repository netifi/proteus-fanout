package io.netifi.proteus.fanout.randomstring;

import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient;

/** Starts the Random String Server */
public class RandomStringMain {

  public static void main(String... args) throws Exception {
    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.randomStringGenerator") // Group name of service
            .accountId(100)
            .minHostsAtStartup(1)
            .poolSize(1)
            .accessKey(7685465987873703191L)
            .accessToken("PYYgV9XHSJ/3KqgK5wYjz+73MeA=")
            .host("localhost") // Proteus Router Host
            .port(8001) // Proteus Router Port
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
