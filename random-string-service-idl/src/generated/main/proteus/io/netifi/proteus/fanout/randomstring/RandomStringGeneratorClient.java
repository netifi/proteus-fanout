package io.netifi.proteus.fanout.randomstring;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.15)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.CLIENT,
    idlClass = RandomStringGenerator.class)
public final class RandomStringGeneratorClient implements RandomStringGenerator {
  private final io.rsocket.RSocket rSocket;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<io.netifi.proteus.fanout.randomstring.RandomStringResponse>, ? extends org.reactivestreams.Publisher<io.netifi.proteus.fanout.randomstring.RandomStringResponse>> generateString;

  public RandomStringGeneratorClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
    this.generateString = java.util.function.Function.identity();
  }

  public RandomStringGeneratorClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.rSocket = rSocket;
    this.generateString = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "service", RandomStringGenerator.SERVICE, "method", RandomStringGenerator.METHOD_GENERATE_STRING);
  }

  public reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomstring.RandomStringResponse> generateString(io.netifi.proteus.fanout.randomstring.RandomStringRequest message) {
    return generateString(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.netifi.proteus.fanout.randomstring.RandomStringResponse> generateString(io.netifi.proteus.fanout.randomstring.RandomStringRequest message, io.netty.buffer.ByteBuf metadata) {
    return reactor.core.publisher.Flux.defer(new java.util.function.Supplier<reactor.core.publisher.Flux<io.rsocket.Payload>>() {
      @java.lang.Override
      public reactor.core.publisher.Flux<io.rsocket.Payload> get() {
        final io.netty.buffer.ByteBuf metadataBuf = io.netifi.proteus.frames.ProteusMetadata.encode(io.netty.buffer.ByteBufAllocator.DEFAULT, RandomStringGenerator.SERVICE, RandomStringGenerator.METHOD_GENERATE_STRING, metadata);
        io.netty.buffer.ByteBuf data = serialize(message);
        return rSocket.requestStream(io.rsocket.util.ByteBufPayload.create(data, metadataBuf));
      }
    }).map(deserializer(io.netifi.proteus.fanout.randomstring.RandomStringResponse.parser())).transform(generateString);
  }

  private static io.netty.buffer.ByteBuf serialize(final com.google.protobuf.MessageLite message) {
    int length = message.getSerializedSize();
    io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length);
    try {
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.internalNioBuffer(0, length)));
      byteBuf.writerIndex(length);
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
