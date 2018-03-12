package io.netifi.proteus.fanout.client;

import io.netifi.proteus.Netifi;
import io.netifi.proteus.fanout.countvowels.CountRequest;
import io.netifi.proteus.fanout.countvowels.CountResponse;
import io.netifi.proteus.fanout.countvowels.VowelCounterClient;
import io.netifi.proteus.fanout.randomstring.RandomStringGeneratorClient;
import io.netifi.proteus.fanout.randomstring.RandomStringRequest;
import io.netifi.proteus.fanout.randomstring.RandomStringResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Starts the Fanout Client */
public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  private Netifi netifi;

  private Mono<RandomStringGeneratorClient> randomStringGeneratorClient;

  private Mono<VowelCounterClient> vowelCounterClient;

  public Main() {
    // Build Netifi Proteus Connection
    this.netifi =
        Netifi.builder()
            .group("fanout.client") // Group name of client
            .accountId(100)
            .accessKey(7685465987873703191L)
            .minHostsAtStartup(1)
            .accessToken("PYYgV9XHSJ/3KqgK5wYjz+73MeA=")
            .host("localhost") // Proteus Router Host
            .port(8001) // Proteus Router Port
            .build();

    randomStringGeneratorClient =
        netifi
            .connect("fanout.randomStringGenerator")
            .map(RandomStringGeneratorClient::new)
            .cache();

    vowelCounterClient = netifi.connect("fanout.vowelcounter").map(VowelCounterClient::new).cache();
  }

  public static void main(String... args) {
    int min = 5;
    int max = 25;
    int numberOfValues = 1_000_000_000;

    Main main = new Main();
    main.countVowelsFromStrings(min, max, numberOfValues);
    main.shutdown();
  }

  private void shutdown() {
    System.exit(0);
  }

  private void countVowelsFromStrings(int min, int max, int numberOfValues) {
    Integer total =
        getRandomStringsFlux(min, max)
            .doOnNext(s -> logger.info("counting string -> " + s))
            // .flatMap(this::countVowels)
            .flatMap(s -> countVowels(s), 8)
            .scan(0, (c1, c2) -> c1 + c2)
            .doOnNext(count -> logger.info("vowels currently found -> " + count))
            .takeUntil(count -> count >= numberOfValues)
            .blockLast();

    logger.info("vowels found -> " + total);
  }

  private Flux<String> getRandomStringsFlux(int min, int max) {
    return randomStringGeneratorClient
        .flatMapMany(
            client -> {
              RandomStringRequest request =
                  RandomStringRequest.newBuilder().setMin(min).setMax(max).build();

              return client.generateString(request);
            })
        .map(RandomStringResponse::getGenerated);
  }

  private Mono<Integer> countVowels(String target) {
    return vowelCounterClient
        .flatMap(
            client -> {
              CountRequest request = CountRequest.newBuilder().setTarget(target).build();

              return client.countVowels(request);
            })
        .map(CountResponse::getCount);
  }
}
