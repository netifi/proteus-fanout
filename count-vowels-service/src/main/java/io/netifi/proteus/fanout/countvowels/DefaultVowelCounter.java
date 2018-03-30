package io.netifi.proteus.fanout.countvowels;

import io.netifi.proteus.fanout.isvowel.IsVowelRequest;
import io.netifi.proteus.fanout.isvowel.IsVowelResponse;
import io.netifi.proteus.fanout.isvowel.VowelCheckerClient;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class DefaultVowelCounter implements VowelCounter {
  private static final Logger logger = LogManager.getLogger(DefaultVowelCounter.class);

  private VowelCheckerClient vowelChecker;

  public DefaultVowelCounter(VowelCheckerClient vowelChecker) {
    this.vowelChecker = vowelChecker;
  }

  @Override
  public Mono<CountResponse> countVowels(CountRequest message, ByteBuf metadata) {
    String target = message.getTarget();

    logger.info("counting vowels in -> " + target);

    String[] split = target.split("");

    return Flux.just(split)
        .limitRate(16, 8)
        .flatMap(s -> checkVowel(IsVowelRequest.newBuilder().setTarget(s).build()))
        .filter(IsVowelResponse::getVowel)
        .count()
        .map(c -> CountResponse.newBuilder().setCount(c.intValue()).build())
        .doOnNext(c -> System.out.println(c.toString()));
  }

  private Mono<IsVowelResponse> checkVowel(IsVowelRequest request) {
    return vowelChecker
        .isVowel(request)
        .timeout(Duration.ofSeconds(10))
        .doOnError(throwable -> logger.error("error checking if vowel"))
        .retry();
  }
}
