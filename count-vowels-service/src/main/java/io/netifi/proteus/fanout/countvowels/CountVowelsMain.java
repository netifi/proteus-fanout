package io.netifi.proteus.fanout.countvowels;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.fanout.isvowel.VowelCheckerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

/** Starts Vowel Counter */
public class CountVowelsMain {
  private static final Logger logger = LogManager.getLogger(CountVowelsMain.class);

  public static void main(String... args) throws Exception {
    long accessKey = Long.getLong("ACCESS_KEY", 3855261330795754807L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "kTBDVtfRBO4tHOnZzSyY5ym2kfY=");
    String host = System.getProperty("BROKER_HOST", "localhost");
    int port = Integer.getInteger("BROKER_PORT", 8001);
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
    Proteus proteus =
        Proteus.builder()
            .group("fanout.vowelcounter") // Group name of service
            .destination(destination)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .meterRegistry(registry)
            .build();

    logger.info("starting vowel counter");

    System.out.println("looking for isVowel service");
    VowelCheckerClient client = new VowelCheckerClient(proteus.group("fanout.isVowel"), registry);

    proteus.addService(new VowelCounterServer(new DefaultVowelCounter(client), Optional.of(registry)));

    logger.info("vowel counter started");

    proteus.onClose().block();
  }
}
