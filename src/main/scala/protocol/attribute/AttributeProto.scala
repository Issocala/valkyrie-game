// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.attribute

object AttributeProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    protocol.base.BaseProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.attribute.CS10040,
      protocol.attribute.SC10040
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """Cg9hdHRyaWJ1dGUucHJvdG8SCHByb3RvY29sGgpiYXNlLnByb3RvIgkKB0NTMTAwNDAimAEKB1NDMTAwNDASPgoPZmlnaHRPc
  mdhbmlzbUlkGAEgAigDQhTiPxESD2ZpZ2h0T3JnYW5pc21JZFIPZmlnaHRPcmdhbmlzbUlkEk0KDGF0dHJpYnV0ZU1hcBgCIAMoC
  zIWLnByb3RvY29sLkF0dHJpYnV0ZU1hcEIR4j8OEgxhdHRyaWJ1dGVNYXBSDGF0dHJpYnV0ZU1hcA=="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Attribute.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}