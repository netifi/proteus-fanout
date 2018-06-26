package io.netifi.proteus.fanout.client;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.netifi.proteus.Proteus;
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

import java.time.Duration;

/** Starts the Fanout Client */
public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  private Proteus proteus;

  private RandomStringGeneratorClient randomStringGeneratorClient;

  private VowelCounterClient vowelCounterClient;

  public Main() {
    long accessKey = Long.getLong("ACCESS_KEY", 3855261330795754807L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "kTBDVtfRBO4tHOnZzSyY5ym2kfY=");
    String host = System.getProperty("BROKER_HOST", "localhost");
    int port = Integer.getInteger("BROKER_PORT", 8001);

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

    // Build Netifi Proteus Connection
    this.proteus =
        Proteus.builder()
            .group("fanout.client") // Group name of client
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .meterRegistry(registry)
            .build();

    randomStringGeneratorClient =
        new RandomStringGeneratorClient(proteus.group("fanout.randomStringGenerator"), registry);

    vowelCounterClient = new VowelCounterClient(proteus.group("fanout.vowelcounter"), registry);
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
  }

  private void countVowelsFromStrings(int min, int max, int numberOfValues) {
    Integer total =
        getRandomStringsFlux(min, max)
            // .flatMap(this::countVowels)
            .flatMap(s -> countVowels(s), 64)
            .scan(0, (c1, c2) -> c1 + c2)
            .filter(count -> count % 1000 == 0)
            .doOnNext(count -> logger.info("vowels currently found -> " + count))
            .takeUntil(count -> count >= numberOfValues)
            .blockLast();

    logger.info("vowels found -> " + total);
  }

  private Flux<String> getRandomStringsFlux(int min, int max) {
    RandomStringRequest request = RandomStringRequest.newBuilder().setMin(min).setMax(max).build();
    return randomStringGeneratorClient
        .generateString(request)
        .map(RandomStringResponse::getGenerated);
  }

  private Mono<Integer> countVowels(String target) {
    CountRequest request = CountRequest.newBuilder().setTarget(target).build();
    return vowelCounterClient.countVowels(request).map(CountResponse::getCount);
  }
}