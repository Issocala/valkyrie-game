// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.equip

object EquipProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    protocol.base.BaseProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.equip.EquipInfo,
      protocol.equip.EquipExtraInfo,
      protocol.equip.StrengthenInfo,
      protocol.equip.SC11100,
      protocol.equip.CS11101,
      protocol.equip.SC11101,
      protocol.equip.CS11102,
      protocol.equip.SC11102,
      protocol.equip.SC11103,
      protocol.equip.CS11104,
      protocol.equip.SC11104,
      protocol.equip.CS11105,
      protocol.equip.SC11105
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CgtlcXVpcC5wcm90bxIIcHJvdG9jb2waCmJhc2UucHJvdG8iiAEKCUVxdWlwSW5mbxIXCgJpZBgBIAIoA0IH4j8EEgJpZFICa
  WQSJgoHZXF1aXBJZBgCIAIoBUIM4j8JEgdlcXVpcElkUgdlcXVpcElkEjoKBWV4dHJhGAMgAigLMhgucHJvdG9jb2wuRXF1aXBFe
  HRyYUluZm9CCuI/BxIFZXh0cmFSBWV4dHJhIrcBCg5FcXVpcEV4dHJhSW5mbxI7Cg5wb3RlbnRpYWxHcmFkZRgBIAIoBUIT4j8QE
  g5wb3RlbnRpYWxHcmFkZVIOcG90ZW50aWFsR3JhZGUSaAoVcG90ZW50aWFsQXR0cmlidXRlTWFwGAIgAygLMhYucHJvdG9jb2wuQ
  XR0cmlidXRlTWFwQhriPxcSFXBvdGVudGlhbEF0dHJpYnV0ZU1hcFIVcG90ZW50aWFsQXR0cmlidXRlTWFwInkKDlN0cmVuZ3RoZ
  W5JbmZvEh0KBHNsb3QYASACKAVCCeI/BhIEc2xvdFIEc2xvdBIgCgVsZXZlbBgCIAIoBUIK4j8HEgVsZXZlbFIFbGV2ZWwSJgoHc
  GVyZmVjdBgDIAIoBUIM4j8JEgdwZXJmZWN0UgdwZXJmZWN0IkMKB1NDMTExMDASOAoGZXF1aXBzGAEgAygLMhMucHJvdG9jb2wuR
  XF1aXBJbmZvQgviPwgSBmVxdWlwc1IGZXF1aXBzIjEKB0NTMTExMDESJgoHd2VhcklkcxgBIAMoA0IM4j8JEgd3ZWFySWRzUgd3Z
  WFySWRzIjEKB1NDMTExMDESJgoHd2VhcklkcxgBIAMoA0IM4j8JEgd3ZWFySWRzUgd3ZWFySWRzIisKB0NTMTExMDISIAoFc2xvd
  HMYASADKAVCCuI/BxIFc2xvdHNSBXNsb3RzIisKB1NDMTExMDISIAoFc2xvdHMYASADKAVCCuI/BxIFc2xvdHNSBXNsb3RzIlcKB
  1NDMTExMDMSTAoLc3RyZW5ndGhlbnMYASADKAsyGC5wcm90b2NvbC5TdHJlbmd0aGVuSW5mb0IQ4j8NEgtzdHJlbmd0aGVuc1ILc
  3RyZW5ndGhlbnMiKAoHQ1MxMTEwNBIdCgRzbG90GAEgAigFQgniPwYSBHNsb3RSBHNsb3QiVAoHU0MxMTEwNBJJCgpzdHJlbmd0a
  GVuGAEgAigLMhgucHJvdG9jb2wuU3RyZW5ndGhlbkluZm9CD+I/DBIKc3RyZW5ndGhlblIKc3RyZW5ndGhlbiIoCgdDUzExMTA1E
  h0KBHNsb3QYASACKAVCCeI/BhIEc2xvdFIEc2xvdCJUCgdTQzExMTA1EkkKCnN0cmVuZ3RoZW4YASACKAsyGC5wcm90b2NvbC5Td
  HJlbmd0aGVuSW5mb0IP4j8MEgpzdHJlbmd0aGVuUgpzdHJlbmd0aGVu"""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Equip.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}