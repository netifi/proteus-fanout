package io.netifi.proteus.fanout.isvowel;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.5)",
    comments = "Source: io/netifi/proteus/fanout/isvowel/service.proto")
public interface VowelChecker {
  int NAMESPACE_ID = 1867447122;
  int SERVICE_ID = 867230833;
  int METHOD_IS_VOWEL = -1270844989;

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  reactor.core.publisher.Mono<io.netifi.proteus.fanout.isvowel.IsVowelResponse> isVowel(io.netifi.proteus.fanout.isvowel.IsVowelRequest message, io.netty.buffer.ByteBuf metadata);
}
