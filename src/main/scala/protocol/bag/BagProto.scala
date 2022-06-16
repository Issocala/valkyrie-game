// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.bag

object BagProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    protocol.equip.EquipProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protocol.bag.CurrencyInfo,
      protocol.bag.ItemInfo,
      protocol.bag.BagInfo,
      protocol.bag.SC11200,
      protocol.bag.SC11201,
      protocol.bag.SC11202,
      protocol.bag.SC11203,
      protocol.bag.SC11204
    )
  private lazy val ProtoBytes: Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CgliYWcucHJvdG8SCHByb3RvY29sGgtlcXVpcC5wcm90byJPCgxDdXJyZW5jeUluZm8SIwoGaXRlbUlkGAEgAigFQgviPwgSB
  ml0ZW1JZFIGaXRlbUlkEhoKA251bRgCIAIoA0II4j8FEgNudW1SA251bSImCghJdGVtSW5mbxIaCgNudW0YASACKANCCOI/BRIDb
  nVtUgNudW0itgEKB0JhZ0luZm8SFwoCaWQYASACKANCB+I/BBICaWRSAmlkEiMKBml0ZW1JZBgCIAIoBUIL4j8IEgZpdGVtSWRSB
  ml0ZW1JZBIxCgRpdGVtGAMgASgLMhIucHJvdG9jb2wuSXRlbUluZm9CCeI/BhIEaXRlbVIEaXRlbRI6CgVlcXVpcBgEIAEoCzIYL
  nByb3RvY29sLkVxdWlwRXh0cmFJbmZvQgriPwcSBWVxdWlwUgVlcXVpcCI7CgdTQzExMjAwEjAKBGJhZ3MYASADKAsyES5wcm90b
  2NvbC5CYWdJbmZvQgniPwYSBGJhZ3NSBGJhZ3MiOwoHU0MxMTIwMRIwCgRiYWdzGAEgAygLMhEucHJvdG9jb2wuQmFnSW5mb0IJ4
  j8GEgRiYWdzUgRiYWdzIiUKB1NDMTEyMDISGgoDaWRzGAEgAygDQgjiPwUSA2lkc1IDaWRzIlIKB1NDMTEyMDMSRwoKY3VycmVuY
  2llcxgBIAMoCzIWLnByb3RvY29sLkN1cnJlbmN5SW5mb0IP4j8MEgpjdXJyZW5jaWVzUgpjdXJyZW5jaWVzIlIKB1NDMTEyMDQSR
  woKY3VycmVuY2llcxgBIAMoCzIWLnByb3RvY29sLkN1cnJlbmN5SW5mb0IP4j8MEgpjdXJyZW5jaWVzUgpjdXJyZW5jaWVz"""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor =
    protocol.Bag.getDescriptor()
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}