package io.netifi.proteus.fanout.randomstring;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.5)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
public interface RandomStringGenerator {
  int NAMESPACE_ID = 1242547087;
  int SERVICE_ID = 1499203632;
  int METHOD_GENERATE_STRING = -962209162;

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomstring.RandomStringResponse> generateString(io.netifi.proteus.fanout.randomstring.RandomStringRequest message, io.netty.buffer.ByteBuf metadata);
}
