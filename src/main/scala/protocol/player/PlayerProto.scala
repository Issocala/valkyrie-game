// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.player

object PlayerProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq.empty
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.player.PlayerInfo,
      protocol.player.CS10020,
      protocol.player.SC10020,
      protocol.player.CS10021,
      protocol.player.SC10021,
      protocol.player.CS10022,
      protocol.player.SC10022
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CgxwbGF5ZXIucHJvdG8SCHByb3RvY29sIoICCgpQbGF5ZXJJbmZvEiMKBnJvbGVJZBgBIAIoA0IL4j8IEgZyb2xlSWRSBnJvb
  GVJZBIdCgRuYW1lGAIgASgJQgniPwYSBG5hbWVSBG5hbWUSIAoFbGV2ZWwYAyACKAVCCuI/BxIFbGV2ZWxSBWxldmVsEiMKBmdlb
  mRlchgEIAIoBUIL4j8IEgZnZW5kZXJSBmdlbmRlchIvCgpwcm9mZXNzaW9uGAUgAigFQg/iPwwSCnByb2Zlc3Npb25SCnByb2Zlc
  3Npb24SOAoNbGFzdExvZ2luVGltZRgGIAIoA0IS4j8PEg1sYXN0TG9naW5UaW1lUg1sYXN0TG9naW5UaW1lIgkKB0NTMTAwMjAiU
  AoHU0MxMDAyMBJFCgpwbGF5ZXJJbmZvGAEgAygLMhQucHJvdG9jb2wuUGxheWVySW5mb0IP4j8MEgpwbGF5ZXJJbmZvUgpwbGF5Z
  XJJbmZvIn4KB0NTMTAwMjESHQoEbmFtZRgBIAIoCUIJ4j8GEgRuYW1lUgRuYW1lEiMKBmdlbmRlchgCIAIoBUIL4j8IEgZnZW5kZ
  XJSBmdlbmRlchIvCgpwcm9mZXNzaW9uGAMgAigFQg/iPwwSCnByb2Zlc3Npb25SCnByb2Zlc3Npb24ifgoHU0MxMDAyMRImCgdzd
  WNjZXNzGAEgAigIQgziPwkSB3N1Y2Nlc3NSB3N1Y2Nlc3MSIwoGcm9sZUlkGAIgASgDQgviPwgSBnJvbGVJZFIGcm9sZUlkEiYKB
  2NvbnRlbnQYAyABKAlCDOI/CRIHY29udGVudFIHY29udGVudCIuCgdDUzEwMDIyEiMKBnJvbGVJZBgBIAIoA0IL4j8IEgZyb2xlS
  WRSBnJvbGVJZCJ+CgdTQzEwMDIyEiYKB3N1Y2Nlc3MYASACKAhCDOI/CRIHc3VjY2Vzc1IHc3VjY2VzcxIjCgZyb2xlSWQYAiABK
  ANCC+I/CBIGcm9sZUlkUgZyb2xlSWQSJgoHY29udGVudBgDIAEoCUIM4j8JEgdjb250ZW50Ugdjb250ZW50"""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Player.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}