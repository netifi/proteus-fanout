package io.netifi.proteus.fanout.randomstring;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.4.9)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
public final class RandomStringGeneratorServer extends io.netifi.proteus.AbstractProteusService {
  private final RandomStringGenerator service;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<io.rsocket.Payload>, ? extends org.reactivestreams.Publisher<io.rsocket.Payload>> generateString;

  public RandomStringGeneratorServer(RandomStringGenerator service) {
    this.service = service;
    this.generateString = java.util.function.Function.identity();
  }

  public RandomStringGeneratorServer(RandomStringGenerator service, io.micrometer.core.instrument.MeterRegistry registry) {
    this.service = service;
    this.generateString = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.server", "namespace", "io.netifi.proteus.fanout.randomstring", "service", "RandomStringGenerator", "method", "generateString");
  }

  @java.lang.Override
  public int getNamespaceId() {
    return RandomStringGenerator.NAMESPACE_ID;
  }

  @java.lang.Override
  public int getServiceId() {
    return RandomStringGenerator.SERVICE_ID;
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<Void> fireAndForget(io.rsocket.Payload payload) {
    return reactor.core.publisher.Mono.error(new UnsupportedOperationException("Fire and forget not implemented."));
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<io.rsocket.Payload> requestResponse(io.rsocket.Payload payload) {
    return reactor.core.publisher.Mono.error(new UnsupportedOperationException("Request-Response not implemented."));
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.rsocket.Payload> requestStream(io.rsocket.Payload payload) {
    try {
      io.netty.buffer.ByteBuf metadata = payload.sliceMetadata();
      switch(io.netifi.proteus.frames.ProteusMetadata.methodId(metadata)) {
        case RandomStringGenerator.METHOD_GENERATE_STRING: {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          return service.generateString(io.netifi.proteus.fanout.randomstring.RandomStringRequest.parseFrom(is), metadata).map(serializer).transform(generateString);
        }
        default: {
          return reactor.core.publisher.Flux.error(new UnsupportedOperationException());
        }
      }
    } catch (Throwable t) {
      return reactor.core.publisher.Flux.error(t);
    } finally {
      payload.release();
    }
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.rsocket.Payload> requestChannel(io.rsocket.Payload payload, reactor.core.publisher.Flux<io.rsocket.Payload> publisher) {
    return reactor.core.publisher.Flux.error(new UnsupportedOperationException("Request-Channel not implemented."));
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.rsocket.Payload> requestChannel(org.reactivestreams.Publisher<io.rsocket.Payload> payloads) {
    return reactor.core.publisher.Flux.error(new UnsupportedOperationException("Request-Channel not implemented."));
  }

  private static final java.util.function.Function<com.google.protobuf.MessageLite, io.rsocket.Payload> serializer =
    new java.util.function.Function<com.google.protobuf.MessageLite, io.rsocket.Payload>() {
      @java.lang.Override
      public io.rsocket.Payload apply(com.google.protobuf.MessageLite message) {
        io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.directBuffer(message.getSerializedSize());
        try {
          message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.nioBuffer(0, byteBuf.writableBytes())));
          byteBuf.writerIndex(byteBuf.capacity());
          return io.rsocket.util.ByteBufPayload.create(byteBuf);
        } catch (Throwable t) {
          byteBuf.release();
          throw new RuntimeException(t);
        }
      }
    };

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
