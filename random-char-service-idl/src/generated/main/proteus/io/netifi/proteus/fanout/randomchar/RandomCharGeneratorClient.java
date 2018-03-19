package io.netifi.proteus.fanout.randomchar;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.8)",
    comments = "Source: io/netifi/proteus/fanout/randomchar/service.proto")
public final class RandomCharGeneratorClient implements RandomCharGenerator {
  private final io.rsocket.RSocket rSocket;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<io.netifi.proteus.fanout.randomchar.RandomCharResponse>, ? extends org.reactivestreams.Publisher<io.netifi.proteus.fanout.randomchar.RandomCharResponse>> generateChar;

  public RandomCharGeneratorClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
    this.generateChar = java.util.function.Function.identity();
  }

  public RandomCharGeneratorClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.rSocket = rSocket;
    this.generateChar = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "namespace", "io.netifi.proteus.fanout.randomchar", "service", "RandomCharGenerator", "method", "generateChar");
  }

  public reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message) {
    return generateChar(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomchar.RandomCharResponse> generateChar(io.netifi.proteus.fanout.randomchar.RandomCharRequest message, io.netty.buffer.ByteBuf metadata) {
    return reactor.core.publisher.Flux.defer(new java.util.function.Supplier<reactor.core.publisher.Flux<io.rsocket.Payload>>() {
      @java.lang.Override
      public reactor.core.publisher.Flux<io.rsocket.Payload> get() {
        final int length = io.netifi.proteus.frames.ProteusMetadata.computeLength(metadata);
        io.netty.buffer.ByteBuf metadataBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(length);
        io.netifi.proteus.frames.ProteusMetadata.encode(metadataBuf, RandomCharGenerator.NAMESPACE_ID, RandomCharGenerator.SERVICE_ID, RandomCharGenerator.METHOD_GENERATE_CHAR, metadata);
        io.netty.buffer.ByteBuf data = serialize(message);
        return rSocket.requestStream(io.rsocket.util.ByteBufPayload.create(data, metadataBuf));
      }
    }).map(deserializer(io.netifi.proteus.fanout.randomchar.RandomCharResponse.parser())).transform(generateChar);
  }

  private static io.netty.buffer.ByteBuf serialize(final com.google.protobuf.MessageLite message) {
    io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(message.getSerializedSize());
    try {
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.nioBuffer(0, byteBuf.writableBytes())));
      byteBuf.writerIndex(byteBuf.capacity());
      return byteBuf;
    } catch (Throwable t) {
      byteBuf.release();
      throw new RuntimeException(t);
    }
  }

  private static <T> java.util.function.Function<io.rsocket.Payload, T> deserializer(final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<io.rsocket.Payload, T>() {
      @java.lang.Override
      public T apply(io.rsocket.Payload payload) {
        try {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          return parser.parseFrom(is);
        } catch (Throwable t) {
          throw new RuntimeException(t);
        } finally {
          payload.release();
        }
      }
    };
  }
}
