// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: io/netifi/proteus/fanout/countvowels/service.proto

package io.netifi.proteus.fanout.countvowels;

public final class VowelCounterProto {
  private VowelCounterProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_netifi_proteus_fanout_countvowels_CountRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_netifi_proteus_fanout_countvowels_CountRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_netifi_proteus_fanout_countvowels_CountResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_netifi_proteus_fanout_countvowels_CountResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n2io/netifi/proteus/fanout/countvowels/s" +
      "ervice.proto\022$io.netifi.proteus.fanout.c" +
      "ountvowels\"\036\n\014CountRequest\022\016\n\006target\030\001 \001" +
      "(\t\"\036\n\rCountResponse\022\r\n\005count\030\001 \001(\0052\210\001\n\014V" +
      "owelCounter\022x\n\013CountVowels\0222.io.netifi.p" +
      "roteus.fanout.countvowels.CountRequest\0323" +
      ".io.netifi.proteus.fanout.countvowels.Co" +
      "untResponse\"\000B;\n$io.netifi.proteus.fanou" +
      "t.countvowelsB\021VowelCounterProtoP\001b\006prot" +
      "o3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_io_netifi_proteus_fanout_countvowels_CountRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_io_netifi_proteus_fanout_countvowels_CountRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_netifi_proteus_fanout_countvowels_CountRequest_descriptor,
        new java.lang.String[] { "Target", });
    internal_static_io_netifi_proteus_fanout_countvowels_CountResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_io_netifi_proteus_fanout_countvowels_CountResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_netifi_proteus_fanout_countvowels_CountResponse_descriptor,
        new java.lang.String[] { "Count", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
