package io.netifi.proteus.fanout.countvowels;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.15)",
    comments = "Source: io/netifi/proteus/fanout/countvowels/service.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.SERVICE,
    idlClass = BlockingVowelCounter.class)
@javax.inject.Named(
    value ="BlockingVowelCounterServer")
public final class BlockingVowelCounterServer extends io.netifi.proteus.AbstractProteusService {
  private final BlockingVowelCounter service;
  private final reactor.core.scheduler.Scheduler scheduler;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<io.rsocket.Payload>, ? extends org.reactivestreams.Publisher<io.rsocket.Payload>> countVowels;
  @javax.inject.Inject
  public BlockingVowelCounterServer(BlockingVowelCounter service, java.util.Optional<reactor.core.scheduler.Scheduler> scheduler, java.util.Optional<io.micrometer.core.instrument.MeterRegistry> registry) {
    this.scheduler = scheduler.orElse(reactor.core.scheduler.Schedulers.elastic());
    this.service = service;
    if (!registry.isPresent()) {
      this.countVowels = java.util.function.Function.identity();
    } else {
      this.countVowels = io.netifi.proteus.metrics.ProteusMetrics.timed(registry.get(), "proteus.server", "service", BlockingVowelCounter.SERVICE_ID, "method", BlockingVowelCounter.METHOD_COUNT_VOWELS);
    }

  }

  @java.lang.Override
  public String getService() {
    return BlockingVowelCounter.SERVICE_ID;
  }

  @java.lang.Override
  public Class<?> getServiceClass() {
    return service.getClass();
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<Void> fireAndForget(io.rsocket.Payload payload) {
    return reactor.core.publisher.Mono.error(new UnsupportedOperationException("Fire and forget not implemented."));
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<io.rsocket.Payload> requestResponse(io.rsocket.Payload payload) {
    try {
      io.netty.buffer.ByteBuf metadata = payload.sliceMetadata();
      switch(io.netifi.proteus.frames.ProteusMetadata.getMethod(metadata)) {
        case VowelCounter.METHOD_COUNT_VOWELS: {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          io.netifi.proteus.fanout.countvowels.CountRequest message = io.netifi.proteus.fanout.countvowels.CountRequest.parseFrom(is);
          return reactor.core.publisher.Mono.fromSupplier(() -> service.countVowels(message, metadata)).map(serializer).transform(countVowels).subscribeOn(scheduler);
        }
        default: {
          return reactor.core.publisher.Mono.error(new UnsupportedOperationException());
        }
      }
    } catch (Throwable t) {
      return reactor.core.publisher.Mono.error(t);
    } finally {
      payload.release();
    }
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<io.rsocket.Payload> requestStream(io.rsocket.Payload payload) {
    return reactor.core.publisher.Flux.error(new UnsupportedOperationException("Request-Stream not implemented."));
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
        int length = message.getSerializedSize();
        io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length);
        try {
          message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.internalNioBuffer(0, length)));
          byteBuf.writerIndex(length);
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
