package io.netifi.proteus.fanout.randomchar;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.15)",
    comments = "Source: io/netifi/proteus/fanout/randomchar/service.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.CLIENT,
    idlClass = BlockingRandomCharGenerator.class)
public final class BlockingRandomCharGeneratorClient implements BlockingRandomCharGenerator {
  private final io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient delegate;

  public BlockingRandomCharGeneratorClient(io.rsocket.RSocket rSocket) {
    this.delegate = new io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient(rSocket);
  }

  public BlockingRandomCharGeneratorClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.delegate = new io.netifi.proteus.fanout.randomchar.RandomCharGeneratorClient(rSocket, registry);
  }

  public  io.netifi.proteus.BlockingIterable<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message) {
    return generateChar(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public  io.netifi.proteus.BlockingIterable<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message, io.netty.buffer.ByteBuf metadata) {
    reactor.core.publisher.Flux stream = delegate.generateChar(message, metadata);
    return new  io.netifi.proteus.BlockingIterable<>(stream, reactor.util.concurrent.Queues.SMALL_BUFFER_SIZE, reactor.util.concurrent.Queues.small());
  }

}

