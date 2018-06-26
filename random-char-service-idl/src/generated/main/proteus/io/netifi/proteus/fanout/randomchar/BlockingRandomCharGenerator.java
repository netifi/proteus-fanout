package io.netifi.proteus.fanout.randomchar;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.15)",
    comments = "Source: io/netifi/proteus/fanout/randomchar/service.proto")
public interface BlockingRandomCharGenerator {
  String SERVICE_ID = "io.netifi.proteus.fanout.randomchar.RandomCharGenerator";
  String METHOD_GENERATE_CHAR = "GenerateChar";

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  Iterable<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message, io.netty.buffer.ByteBuf metadata);
}
