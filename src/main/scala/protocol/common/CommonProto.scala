// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.common

object CommonProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq.empty
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.common.SC10080,
      protocol.common.CS10081,
      protocol.common.SC10081,
      protocol.common.CS10082
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """Cgxjb21tb24ucHJvdG8SCHByb3RvY29sIlIKB1NDMTAwODASRwoSYXBwbGljYXRpb25FcnJvcklkGAEgAigFQhfiPxQSEmFwc
  GxpY2F0aW9uRXJyb3JJZFISYXBwbGljYXRpb25FcnJvcklkIgkKB0NTMTAwODEiOgoHU0MxMDA4MRIvCgpzZXJ2ZXJUaW1lGAEgA
  igDQg/iPwwSCnNlcnZlclRpbWVSCnNlcnZlclRpbWUiOgoHQ1MxMDA4MhIvCgpzZXJ2ZXJUaW1lGAEgAigDQg/iPwwSCnNlcnZlc
  lRpbWVSCnNlcnZlclRpbWU="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Common.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}