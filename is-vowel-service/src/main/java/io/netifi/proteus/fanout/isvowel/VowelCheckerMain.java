package io.netifi.proteus.fanout.isvowel;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.netifi.proteus.Proteus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

/** Starts Is Vowel Service */
public class VowelCheckerMain {
  private static final Logger logger = LogManager.getLogger(VowelCheckerMain.class);

  public static void main(String... args) throws Exception {
    long accessKey = Long.getLong("ACCESS_KEY", 3855261330795754807L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "kTBDVtfRBO4tHOnZzSyY5ym2kfY=");
    String host = System.getProperty("BROKER_HOST", "localhost");
    int port = Integer.getInteger("BROKER_PORT", 8001);
    int low = Integer.getInteger("LOW", 250);
    int high = Integer.getInteger("HIGH", 1500);
    String destination = System.getProperty("DESTINATION", UUID.randomUUID().toString());

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
            .group("fanout.isVowel") // Group name of service
            .destination(destination)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .meterRegistry(registry)
            .build();

    boolean delayed = Boolean.getBoolean("delayed");

    logger.info("starting vowel checker with a delay -> " + delayed);

    // Add Service to Respond to Requests
    proteus.addService(
        new VowelCheckerServer(new DefaultVowelChecker(delayed, low, high), Optional.of(registry)));

    proteus.onClose().block();
  }
}
