// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.buff

object BuffProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq.empty
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.buff.SC10070,
      protocol.buff.SC10071,
      protocol.buff.SC10072
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CgpidWZmLnByb3RvEghwcm90b2NvbCIJCgdTQzEwMDcwIoYBCgdTQzEwMDcxEj4KD2ZpZ2h0T3JnYW5pc21JZBgBIAIoA0IU4
  j8REg9maWdodE9yZ2FuaXNtSWRSD2ZpZ2h0T3JnYW5pc21JZBI7Cg5idWZmVGVtcGxhdGVJZBgCIAIoBUIT4j8QEg5idWZmVGVtc
  GxhdGVJZFIOYnVmZlRlbXBsYXRlSWQihgEKB1NDMTAwNzISPgoPZmlnaHRPcmdhbmlzbUlkGAEgAigDQhTiPxESD2ZpZ2h0T3JnY
  W5pc21JZFIPZmlnaHRPcmdhbmlzbUlkEjsKDmJ1ZmZUZW1wbGF0ZUlkGAIgAigFQhPiPxASDmJ1ZmZUZW1wbGF0ZUlkUg5idWZmV
  GVtcGxhdGVJZA=="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Buff.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}