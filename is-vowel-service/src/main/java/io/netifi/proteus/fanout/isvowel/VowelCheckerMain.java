package io.netifi.proteus.fanout.isvowel;

import io.netifi.proteus.Netifi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Starts Is Vowel Service */
public class VowelCheckerMain {
  private static final Logger logger = LogManager.getLogger(VowelCheckerMain.class);
  
  
  public static void main(String... args) throws Exception {
    long accountId = Long.getLong("ACCOUNT_ID", 100);
    int minHostsAtStartup = Integer.getInteger("MIN_HOSTS_AT_STARTUP", 1);
    int poolSize = Integer.getInteger("POOL_SIZE", 1);
    long accessKey = Long.getLong("ACCESS_KEY", 7685465987873703191L);
    String accessToken = System.getProperty("ACCESS_TOKEN", "PYYgV9XHSJ/3KqgK5wYjz+73MeA=");
    String host = System.getProperty("ROUTER_HOST", "localhost");
    int port = Integer.getInteger("ROUTER_PORT", 8001);
    int low = Integer.getInteger("LOW", 250);
    int high = Integer.getInteger("HIGH", 1500);
  
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
            .group("fanout.isVowel") // Group name of service
            .accountId(accountId)
            .minHostsAtStartup(minHostsAtStartup)
            .poolSize(poolSize)
            .accessKey(accessKey)
            .accessToken(accessToken)
            .host(host) // Proteus Router Host
            .port(port) // Proteus Router Port
            .build();
    
    boolean delayed = Boolean.getBoolean("delayed");

    logger.info("starting vowel checker with a delay -> " + delayed);
    // Add Service to Respond to Requests
    netifi.addService(new VowelCheckerServer(new DefaultVowelChecker(delayed, low, high)));

    netifi.onClose().block();
  }
}
