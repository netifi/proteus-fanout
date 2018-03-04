package io.netifi.proteus.fanout.randomstring;

import io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient;
import io.netifi.proteus.fanout.randomchar.RandomCharRequest;
import io.netifi.proteus.fanout.randomstring.RandomStringGenerator;
import io.netifi.proteus.fanout.randomstring.RandomStringRequest;
import io.netifi.proteus.fanout.randomstring.RandomStringResponse;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultRandomStringGenerator implements RandomStringGenerator {
    private static final Logger logger = LogManager.getLogger(DefaultRandomStringGenerator.class);
    
    private RandomCharGeneratorClient randomCharGeneratorClient;

  public DefaultRandomStringGenerator(RandomCharGeneratorClient randomCharGeneratorClient) {
    this.randomCharGeneratorClient = randomCharGeneratorClient;
  }

  @Override
  public Flux<RandomStringResponse> generateString(RandomStringRequest message, ByteBuf metadata) {
    int min = message.getMin();
    int max = message.getMax();

    return Flux.<Integer>generate(
            sink -> {
              int size = ThreadLocalRandom.current().nextInt(min, max);
              sink.next(size);
            })
        .flatMap(
            i -> {
              return randomCharGeneratorClient
                  .generateChar(RandomCharRequest.getDefaultInstance())
                  .limitRequest(i)
                  .reduce(
                      "",
                      (s, randomCharResponse) -> {
                        return s + randomCharResponse.getGenerated();
                      });
            })
        .map(s -> RandomStringResponse.newBuilder().setGenerated(s).build())
        .doOnError(Throwable::printStackTrace)
        .doOnNext(s -> logger.info(s.toString()));
  }
}
