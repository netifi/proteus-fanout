package io.netifi.proteus.fanout.randomchar;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultRandomCharGenerator implements RandomCharGenerator {
    private static final Logger logger = LogManager.getLogger(DefaultRandomCharGenerator.class);
    
    private static final char[] chars =
      "abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

  private static char getRandomChar() {
    int idx = ThreadLocalRandom.current().nextInt(0, chars.length);
    return chars[idx];
  }

  @Override
  public Flux<RandomCharResponse> generateChar(RandomCharRequest message, ByteBuf metadata) {
    return Flux.generate(
            sink -> {
              String s = String.valueOf(getRandomChar());
              RandomCharResponse response = RandomCharResponse.newBuilder().setGenerated(s).build();
              sink.next(response);
            })
        .doOnNext(s -> logger.info(s.toString()))
        .doOnError(Throwable::printStackTrace)
        .cast(RandomCharResponse.class);
  }
}
