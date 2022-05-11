// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

object SceneProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq.empty
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.scene.MoveInfo,
      protocol.scene.StopInfo,
      protocol.scene.JumpInfo,
      protocol.scene.CS10300,
      protocol.scene.SC10300,
      protocol.scene.CS10301,
      protocol.scene.SC10301,
      protocol.scene.CS10302,
      protocol.scene.SC10302,
      protocol.scene.CS10303,
      protocol.scene.SC10303,
      protocol.scene.SC10304,
      protocol.scene.CS10305,
      protocol.scene.SC10305,
      protocol.scene.CS10306,
      protocol.scene.SC10306,
      protocol.scene.SC10307,
      protocol.scene.SC10308,
      protocol.scene.CS10309,
      protocol.scene.CS10310,
      protocol.scene.CS10311,
      protocol.scene.CS10312,
      protocol.scene.SC10312
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CgtzY2VuZS5wcm90bxIIcHJvdG9jb2wiswEKCE1vdmVJbmZvEiwKCXBvc2l0aW9uWBgBIAIoAkIO4j8LEglwb3NpdGlvblhSC
  XBvc2l0aW9uWBIsCglwb3NpdGlvblkYAiACKAJCDuI/CxIJcG9zaXRpb25ZUglwb3NpdGlvblkSHQoEZmFjZRgDIAIoBUIJ4j8GE
  gRmYWNlUgRmYWNlEiwKCWRpcmVjdGlvbhgEIAIoAkIO4j8LEglkaXJlY3Rpb25SCWRpcmVjdGlvbiKFAQoIU3RvcEluZm8SLAoJc
  G9zaXRpb25YGAEgAigCQg7iPwsSCXBvc2l0aW9uWFIJcG9zaXRpb25YEiwKCXBvc2l0aW9uWRgCIAIoAkIO4j8LEglwb3NpdGlvb
  llSCXBvc2l0aW9uWRIdCgRmYWNlGAMgAigFQgniPwYSBGZhY2VSBGZhY2UiyQIKCEp1bXBJbmZvEiwKCXBvc2l0aW9uWBgBIAIoA
  kIO4j8LEglwb3NpdGlvblhSCXBvc2l0aW9uWBIsCglwb3NpdGlvblkYAiACKAJCDuI/CxIJcG9zaXRpb25ZUglwb3NpdGlvblkSM
  goLaGlnaGVzdFBvc1gYAyACKAJCEOI/DRILaGlnaGVzdFBvc1hSC2hpZ2hlc3RQb3NYEjIKC2hpZ2hlc3RQb3NZGAQgAigCQhDiP
  w0SC2hpZ2hlc3RQb3NZUgtoaWdoZXN0UG9zWRIsCglmaW5hbFBvc1gYBSACKAJCDuI/CxIJZmluYWxQb3NYUglmaW5hbFBvc1gSL
  AoJZmluYWxQb3NZGAYgAigCQg7iPwsSCWZpbmFsUG9zWVIJZmluYWxQb3NZEh0KBGZhY2UYByACKAVCCeI/BhIEZmFjZVIEZmFjZ
  SIxCgdDUzEwMzAwEiYKB3NjZW5lSWQYASACKANCDOI/CRIHc2NlbmVJZFIHc2NlbmVJZCKNAQoHU0MxMDMwMBImCgdzY2VuZUlkG
  AEgAigDQgziPwkSB3NjZW5lSWRSB3NjZW5lSWQSLAoJcG9zaXRpb25YGAIgAigCQg7iPwsSCXBvc2l0aW9uWFIJcG9zaXRpb25YE
  iwKCXBvc2l0aW9uWRgDIAIoAkIO4j8LEglwb3NpdGlvbllSCXBvc2l0aW9uWSIxCgdDUzEwMzAxEiYKB3NjZW5lSWQYASACKANCD
  OI/CRIHc2NlbmVJZFIHc2NlbmVJZCI0CgdTQzEwMzAxEikKCHBsYXllcklkGAEgAigDQg3iPwoSCHBsYXllcklkUghwbGF5ZXJJZ
  CJ5CgdDUzEwMzAyEi8KCm9yZ2FuaXNtSWQYASACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBI9Cghtb3ZlSW5mbxgCI
  AIoCzISLnByb3RvY29sLk1vdmVJbmZvQg3iPwoSCG1vdmVJbmZvUghtb3ZlSW5mbyJ5CgdTQzEwMzAyEi8KCm9yZ2FuaXNtSWQYA
  SACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBI9Cghtb3ZlSW5mbxgCIAIoCzISLnByb3RvY29sLk1vdmVJbmZvQg3iP
  woSCG1vdmVJbmZvUghtb3ZlSW5mbyJ5CgdDUzEwMzAzEi8KCm9yZ2FuaXNtSWQYASACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnY
  W5pc21JZBI9CghzdG9wSW5mbxgCIAIoCzISLnByb3RvY29sLlN0b3BJbmZvQg3iPwoSCHN0b3BJbmZvUghzdG9wSW5mbyJ5CgdTQ
  zEwMzAzEi8KCm9yZ2FuaXNtSWQYASACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBI9CghzdG9wSW5mbxgCIAIoCzISL
  nByb3RvY29sLlN0b3BJbmZvQg3iPwoSCHN0b3BJbmZvUghzdG9wSW5mbyK1AgoHU0MxMDMwNBIvCgpvcmdhbmlzbUlkGAEgAigDQ
  g/iPwwSCm9yZ2FuaXNtSWRSCm9yZ2FuaXNtSWQSNQoMb3JnYW5pc21UeXBlGAIgAigFQhHiPw4SDG9yZ2FuaXNtVHlwZVIMb3JnY
  W5pc21UeXBlEiwKCXBvc2l0aW9uWBgDIAIoAkIO4j8LEglwb3NpdGlvblhSCXBvc2l0aW9uWBIsCglwb3NpdGlvblkYBCACKAJCD
  uI/CxIJcG9zaXRpb25ZUglwb3NpdGlvblkSHQoEZmFjZRgFIAIoBUIJ4j8GEgRmYWNlUgRmYWNlEkcKEm9yZ2FuaXNtVGVtcGxhd
  GVJZBgGIAIoBUIX4j8UEhJvcmdhbmlzbVRlbXBsYXRlSWRSEm9yZ2FuaXNtVGVtcGxhdGVJZCJ5CgdDUzEwMzA1Ei8KCm9yZ2Fua
  XNtSWQYASACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBI9CghqdW1wSW5mbxgCIAIoCzISLnByb3RvY29sLkp1bXBJb
  mZvQg3iPwoSCGp1bXBJbmZvUghqdW1wSW5mbyJ5CgdTQzEwMzA1Ei8KCm9yZ2FuaXNtSWQYASACKANCD+I/DBIKb3JnYW5pc21JZ
  FIKb3JnYW5pc21JZBI9CghqdW1wSW5mbxgCIAIoCzISLnByb3RvY29sLkp1bXBJbmZvQg3iPwoSCGp1bXBJbmZvUghqdW1wSW5mb
  yKWAQoHQ1MxMDMwNhIsCglwb3NpdGlvblgYASACKAJCDuI/CxIJcG9zaXRpb25YUglwb3NpdGlvblgSLAoJcG9zaXRpb25ZGAIgA
  igCQg7iPwsSCXBvc2l0aW9uWVIJcG9zaXRpb25ZEi8KCm9yZ2FuaXNtSWQYAyACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc
  21JZCKWAQoHU0MxMDMwNhIsCglwb3NpdGlvblgYASACKAJCDuI/CxIJcG9zaXRpb25YUglwb3NpdGlvblgSLAoJcG9zaXRpb25ZG
  AIgAigCQg7iPwsSCXBvc2l0aW9uWVIJcG9zaXRpb25ZEi8KCm9yZ2FuaXNtSWQYAyACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnY
  W5pc21JZCIJCgdTQzEwMzA3InoKB1NDMTAzMDgSLwoKb3JnYW5pc21JZBgBIAIoA0IP4j8MEgpvcmdhbmlzbUlkUgpvcmdhbmlzb
  UlkEj4KD3NraWxsVGVtcGxhdGVJZBgCIAIoBUIU4j8REg9za2lsbFRlbXBsYXRlSWRSD3NraWxsVGVtcGxhdGVJZCIJCgdDUzEwM
  zA5IncKB0NTMTAzMTASLwoKb3JnYW5pc21JZBgBIAIoA0IP4j8MEgpvcmdhbmlzbUlkUgpvcmdhbmlzbUlkEjsKDm9yZ2FuaXNtS
  XRlbUlkGAIgAigDQhPiPxASDm9yZ2FuaXNtSXRlbUlkUg5vcmdhbmlzbUl0ZW1JZCJ0CgdDUzEwMzExEi8KCm9yZ2FuaXNtSWQYA
  SACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBI4Cg1vcmdhbmlzbU5wY0lkGAIgAigDQhLiPw8SDW9yZ2FuaXNtTnBjS
  WRSDW9yZ2FuaXNtTnBjSWQitQEKB0NTMTAzMTISLwoKb3JnYW5pc21JZBgBIAIoA0IP4j8MEgpvcmdhbmlzbUlkUgpvcmdhbmlzb
  UlkEiwKCXBvc2l0aW9uWBgCIAIoAkIO4j8LEglwb3NpdGlvblhSCXBvc2l0aW9uWBIsCglwb3NpdGlvblkYAyACKAJCDuI/CxIJc
  G9zaXRpb25ZUglwb3NpdGlvblkSHQoEdGltZRgEIAIoAkIJ4j8GEgR0aW1lUgR0aW1lIrUBCgdTQzEwMzEyEi8KCm9yZ2FuaXNtS
  WQYASACKANCD+I/DBIKb3JnYW5pc21JZFIKb3JnYW5pc21JZBIsCglwb3NpdGlvblgYAiACKAJCDuI/CxIJcG9zaXRpb25YUglwb
  3NpdGlvblgSLAoJcG9zaXRpb25ZGAMgAigCQg7iPwsSCXBvc2l0aW9uWVIJcG9zaXRpb25ZEh0KBHRpbWUYBCACKAJCCeI/BhIEd
  GltZVIEdGltZQ=="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Scene.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}