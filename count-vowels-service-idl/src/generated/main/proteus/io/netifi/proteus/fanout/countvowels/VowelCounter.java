package io.netifi.proteus.fanout.countvowels;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.3)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
public interface VowelCounter {
  int NAMESPACE_ID = -557106202;
  int SERVICE_ID = -1648661050;
  int METHOD_COUNT_VOWELS = 726712608;

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  reactor.core.publisher.Mono<io.netifi.proteus.fanout.countvowels.CountResponse> countVowels(io.netifi.proteus.fanout.countvowels.CountRequest message, io.netty.buffer.ByteBuf metadata);
}
