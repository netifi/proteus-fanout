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
    
    // Build Netifi Proteus Connection
    this.netifi =
        Netifi.builder()
            .group("fanout.client") // Group name of client
            .accountId(accountId)
            .minHostsAtStartup(minHostsAtStartup)
            .poolSize(poolSize)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .build();

    randomStringGeneratorClient =
        netifi
            .connect("fanout.randomStringGenerator")
            .map(RandomStringGeneratorClient::new)
            .cache();

    vowelCounterClient = netifi.connect("fanout.vowelcounter").map(VowelCounterClient::new).cache();
  }

  public static void main(String... args) {
    int min = Integer.getInteger("MIN", 5);
    int max = Integer.getInteger("MAX", 25);
    int numberOfValues = Integer.getInteger("NUM_VOWELS", 10_000);

    logger.info("min string size {}", min);
    logger.info("max string size {}", max);
    logger.info("number of vowels {}", numberOfValues);

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
