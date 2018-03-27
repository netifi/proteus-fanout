package io.netifi.proteus.fanout.randomchar;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.5.1)",
    comments = "Source: io/netifi/proteus/fanout/randomchar/service.proto")
public interface RandomCharGenerator {
  int NAMESPACE_ID = 1351961714;
  int SERVICE_ID = 984590539;
  int METHOD_GENERATE_CHAR = -916212707;

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message, io.netty.buffer.ByteBuf metadata);
}
