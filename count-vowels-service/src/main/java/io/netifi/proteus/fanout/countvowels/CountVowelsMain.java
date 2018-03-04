package io.netifi.proteus.fanout.countvowels;

import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.isvowel.VowelCheckerClient;

/** Starts Vowel Counter */
public class CountVowelsMain {

  public static void main(String... args) throws Exception {
    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.vowelcounter") // Group name of service
            .accountId(100)
            .minHostsAtStartup(1)
            .poolSize(1)
            .accessKey(7685465987873703191L)
            .accessToken("PYYgV9XHSJ/3KqgK5wYjz+73MeA=")
            .host("localhost") // Proteus Router Host
            .port(8001) // Proteus Router Port
            .build();

    System.out.println("starting vowel counter");

    netifi
        .connect("fanout.isVowel")
        .doOnNext(
            socket -> {
              System.out.println("looking for isVowel service");
              VowelCheckerClient client = new VowelCheckerClient(socket);

              // Add Service to Respond to Requests
              netifi.addService(new VowelCounterServer(new DefaultVowelCounter(client)));
            })
        .block();

    System.out.println("vowel counter started");

    netifi.onClose().block();
  }
}
