// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: io/netifi/proteus/fanout/randomchar/service.proto

package io.netifi.proteus.fanout.randomchar;

public final class RandomCharProto {
  private RandomCharProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_netifi_proteus_fanout_randomchar_RandomCharRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_netifi_proteus_fanout_randomchar_RandomCharResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n1io/netifi/proteus/fanout/randomchar/se" +
      "rvice.proto\022#io.netifi.proteus.fanout.ra" +
      "ndomchar\"\023\n\021RandomCharRequest\"\'\n\022RandomC" +
      "harResponse\022\021\n\tgenerated\030\001 \001(\t2\233\001\n\023Rando" +
      "mCharGenerator\022\203\001\n\014GenerateChar\0226.io.net" +
      "ifi.proteus.fanout.randomchar.RandomChar" +
      "Request\0327.io.netifi.proteus.fanout.rando" +
      "mchar.RandomCharResponse\"\0000\001B8\n#io.netif" +
      "i.proteus.fanout.randomcharB\017RandomCharP" +
      "rotoP\001b\006proto3"
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
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_netifi_proteus_fanout_randomchar_RandomCharRequest_descriptor,
        new java.lang.String[] { });
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_io_netifi_proteus_fanout_randomchar_RandomCharResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_netifi_proteus_fanout_randomchar_RandomCharResponse_descriptor,
        new java.lang.String[] { "Generated", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}