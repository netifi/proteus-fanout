package io.netifi.proteus.fanout.isvowel;

import io.netifi.proteus.Netifi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Starts Is Vowel Service */
public class VowelCheckerMain {
  private static final Logger logger = LogManager.getLogger(VowelCheckerMain.class);
  
  
  public static void main(String... args) throws Exception {
    boolean delayed = Boolean.getBoolean("delayed");

    logger.info("starting vowel checker with a delay -> " + delayed);
    
    // Build Netifi Connection
    Netifi netifi =
        Netifi.builder()
            .group("fanout.isVowel") // Group name of service
            .accountId(100)
            .minHostsAtStartup(1)
            .poolSize(1)
            .accessKey(7685465987873703191L)
            .accessToken("PYYgV9XHSJ/3KqgK5wYjz+73MeA=")
            .host("localhost") // Proteus Router Host
            .port(8001) // Proteus Router Port
            .build();
    
    // Add Service to Respond to Requests
    netifi.addService(new VowelCheckerServer(new DefaultVowelChecker(delayed)));

    netifi.onClose().block();
  }
}
