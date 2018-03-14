package io.netifi.proteus.fanout.isvowel;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultVowelChecker implements VowelChecker {
  private static final Logger logger = LogManager.getLogger(DefaultVowelChecker.class);

  private boolean hasDelay;
  
  private int low;
  
  private int high;

  public DefaultVowelChecker(boolean hasDelay, int low, int high) {
    this.hasDelay = hasDelay;
    this.low = low;
    this.high = high;
  }

  @Override
  public Mono<IsVowelResponse> isVowel(IsVowelRequest message, ByteBuf metadata) {
    String t = message.getTarget();

    if (t.length() != 1) {
      throw new IllegalStateException("can only check 1 character at a time");
    }

    boolean vowel = false;
    char c = t.toLowerCase().charAt(0);
    switch (c) {
      case 'a':
      case 'e':
      case 'i':
      case 'o':
      case 'u':
        vowel = true;
        break;
      default:
    }

    IsVowelResponse response = IsVowelResponse.newBuilder().setVowel(vowel).build();
    logger.info(c + " is vowel -> " + vowel);

    long delay = ThreadLocalRandom.current().nextLong(low, high);

    if (hasDelay) {
      return Mono.delay(Duration.ofMillis(delay)).then(Mono.just(response));
    } else {
      return Mono.just(response);
    }
  }
}
