// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.equip
import _root_.scalapb.internal.compat.JavaConverters._

/** 装备附加信息
  *
  * @param potentialGrade
  *   装备潜能评级 (0未鉴定，1以上为评级)
  * @param potentialAttributeMap
  *   装备潜能属性
  */
@SerialVersionUID(0L)
final case class EquipExtraInfo(
    potentialGrade: _root_.scala.Int,
    potentialAttributeMap: _root_.scala.Seq[protocol.base.AttributeMap] = _root_.scala.Seq.empty,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[EquipExtraInfo] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = potentialGrade
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(1, __value)
      };
      potentialAttributeMap.foreach { __item =>
        val __value = __item
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
      }
      __size += unknownFields.serializedSize
      __size
    }
    override def serializedSize: _root_.scala.Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      
      {
        val __v = potentialGrade
        _output__.writeInt32(1, __v)
      };
      potentialAttributeMap.foreach { __v =>
        val __m = __v
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__m.serializedSize)
        __m.writeTo(_output__)
      };
      unknownFields.writeTo(_output__)
    }
    def withPotentialGrade(__v: _root_.scala.Int): EquipExtraInfo = copy(potentialGrade = __v)
    def clearPotentialAttributeMap = copy(potentialAttributeMap = _root_.scala.Seq.empty)
    def addPotentialAttributeMap(__vs: protocol.base.AttributeMap*): EquipExtraInfo = addAllPotentialAttributeMap(__vs)
    def addAllPotentialAttributeMap(__vs: Iterable[protocol.base.AttributeMap]): EquipExtraInfo = copy(potentialAttributeMap = potentialAttributeMap ++ __vs)
    def withPotentialAttributeMap(__v: _root_.scala.Seq[protocol.base.AttributeMap]): EquipExtraInfo = copy(potentialAttributeMap = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => potentialGrade
        case 2 => potentialAttributeMap
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PInt(potentialGrade)
        case 2 => _root_.scalapb.descriptors.PRepeated(potentialAttributeMap.iterator.map(_.toPMessage).toVector)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.equip.EquipExtraInfo
    // @@protoc_insertion_point(GeneratedMessage[protocol.EquipExtraInfo])
}

object EquipExtraInfo extends scalapb.GeneratedMessageCompanion[protocol.equip.EquipExtraInfo] with scalapb.HasBuilder[protocol.equip.EquipExtraInfo] with scalapb.JavaProtoSupport[protocol.equip.EquipExtraInfo, protocol.Equip.EquipExtraInfo] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.equip.EquipExtraInfo] with scalapb.HasBuilder[protocol.equip.EquipExtraInfo] with scalapb.JavaProtoSupport[protocol.equip.EquipExtraInfo, protocol.Equip.EquipExtraInfo] = this
  def toJavaProto(scalaPbSource: protocol.equip.EquipExtraInfo): protocol.Equip.EquipExtraInfo = {
    val javaPbOut = protocol.Equip.EquipExtraInfo.newBuilder
    javaPbOut.setPotentialGrade(scalaPbSource.potentialGrade)
    javaPbOut.addAllPotentialAttributeMap(_root_.scalapb.internal.compat.toIterable(scalaPbSource.potentialAttributeMap.iterator.map(protocol.base.AttributeMap.toJavaProto(_))).asJava)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Equip.EquipExtraInfo): protocol.equip.EquipExtraInfo = protocol.equip.EquipExtraInfo(
    potentialGrade = javaPbSource.getPotentialGrade.intValue,
    potentialAttributeMap = javaPbSource.getPotentialAttributeMapList.asScala.iterator.map(protocol.base.AttributeMap.fromJavaProto(_)).toSeq
  )
  def merge(`_message__`: protocol.equip.EquipExtraInfo, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.equip.EquipExtraInfo = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.equip.EquipExtraInfo] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.equip.EquipExtraInfo(
        potentialGrade = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Int],
        potentialAttributeMap = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Seq[protocol.base.AttributeMap]]).getOrElse(_root_.scala.Seq.empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = EquipProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = EquipProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 2 => __out = protocol.base.AttributeMap
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.equip.EquipExtraInfo(
    potentialGrade = 0,
    potentialAttributeMap = _root_.scala.Seq.empty
  )
  final class Builder private (
    private var __potentialGrade: _root_.scala.Int,
    private val __potentialAttributeMap: _root_.scala.collection.immutable.VectorBuilder[protocol.base.AttributeMap],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.equip.EquipExtraInfo] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __potentialGrade = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 18 =>
            __potentialAttributeMap += _root_.scalapb.LiteParser.readMessage[protocol.base.AttributeMap](_input__)
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.equip.EquipExtraInfo = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.equip.EquipExtraInfo(
        potentialGrade = __potentialGrade,
        potentialAttributeMap = __potentialAttributeMap.result(),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.equip.EquipExtraInfo, protocol.equip.EquipExtraInfo.Builder] {
    def apply(): Builder = new Builder(
      __potentialGrade = 0,
      __potentialAttributeMap = new _root_.scala.collection.immutable.VectorBuilder[protocol.base.AttributeMap],
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.equip.EquipExtraInfo): Builder = new Builder(
        __potentialGrade = _message__.potentialGrade,
        __potentialAttributeMap = new _root_.scala.collection.immutable.VectorBuilder[protocol.base.AttributeMap] ++= _message__.potentialAttributeMap,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.equip.EquipExtraInfo.Builder()
  def newBuilder(`_message__`: protocol.equip.EquipExtraInfo): Builder = protocol.equip.EquipExtraInfo.Builder(_message__)
  implicit class EquipExtraInfoLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.equip.EquipExtraInfo]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.equip.EquipExtraInfo](_l) {
    def potentialGrade: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.potentialGrade)((c_, f_) => c_.copy(potentialGrade = f_))
    def potentialAttributeMap: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Seq[protocol.base.AttributeMap]] = field(_.potentialAttributeMap)((c_, f_) => c_.copy(potentialAttributeMap = f_))
  }
  final val POTENTIALGRADE_FIELD_NUMBER = 1
  final val POTENTIALATTRIBUTEMAP_FIELD_NUMBER = 2
  def of(
    potentialGrade: _root_.scala.Int,
    potentialAttributeMap: _root_.scala.Seq[protocol.base.AttributeMap]
  ): _root_.protocol.equip.EquipExtraInfo = _root_.protocol.equip.EquipExtraInfo(
    potentialGrade,
    potentialAttributeMap
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.EquipExtraInfo])
}
