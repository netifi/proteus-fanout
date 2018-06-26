package io.netifi.proteus.fanout.countvowels;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.15)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
public interface BlockingVowelCounter {
  String SERVICE_ID = "io.netifi.proteus.fanout.countvowels.VowelCounter";
  String METHOD_COUNT_VOWELS = "CountVowels";

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  io.netifi.proteus.fanout.countvowels.CountResponse countVowels(io.netifi.proteus.fanout.countvowels.CountRequest message, io.netty.buffer.ByteBuf metadata);
}
