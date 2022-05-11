// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.buff

@SerialVersionUID(0L)
final case class SC10072(
    fightOrganismId: _root_.scala.Long,
    buffInfo: protocol.buff.BuffInfo,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10072] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = fightOrganismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      
      {
        val __value = buffInfo
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
      };
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
        val __v = fightOrganismId
        _output__.writeInt64(1, __v)
      };
      
      {
        val __v = buffInfo
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      unknownFields.writeTo(_output__)
    }
    def withFightOrganismId(__v: _root_.scala.Long): SC10072 = copy(fightOrganismId = __v)
    def withBuffInfo(__v: protocol.buff.BuffInfo): SC10072 = copy(buffInfo = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => fightOrganismId
        case 2 => buffInfo
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(fightOrganismId)
        case 2 => buffInfo.toPMessage
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.buff.SC10072
    // @@protoc_insertion_point(GeneratedMessage[protocol.SC10072])
}

object SC10072 extends scalapb.GeneratedMessageCompanion[protocol.buff.SC10072] with scalapb.HasBuilder[protocol.buff.SC10072] with scalapb.JavaProtoSupport[protocol.buff.SC10072, protocol.Buff.SC10072] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.buff.SC10072] with scalapb.HasBuilder[protocol.buff.SC10072] with scalapb.JavaProtoSupport[protocol.buff.SC10072, protocol.Buff.SC10072] = this
  def toJavaProto(scalaPbSource: protocol.buff.SC10072): protocol.Buff.SC10072 = {
    val javaPbOut = protocol.Buff.SC10072.newBuilder
    javaPbOut.setFightOrganismId(scalaPbSource.fightOrganismId)
    javaPbOut.setBuffInfo(protocol.buff.BuffInfo.toJavaProto(scalaPbSource.buffInfo))
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Buff.SC10072): protocol.buff.SC10072 = protocol.buff.SC10072(
    fightOrganismId = javaPbSource.getFightOrganismId.longValue,
    buffInfo = protocol.buff.BuffInfo.fromJavaProto(javaPbSource.getBuffInfo)
  )
  def merge(`_message__`: protocol.buff.SC10072, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.buff.SC10072 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.buff.SC10072] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.buff.SC10072(
        fightOrganismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        buffInfo = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[protocol.buff.BuffInfo]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = BuffProto.javaDescriptor.getMessageTypes().get(3)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = BuffProto.scalaDescriptor.messages(3)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 2 => __out = protocol.buff.BuffInfo
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.buff.SC10072(
    fightOrganismId = 0L,
    buffInfo = protocol.buff.BuffInfo.defaultInstance
  )
  final class Builder private (
    private var __fightOrganismId: _root_.scala.Long,
    private var __buffInfo: _root_.scala.Option[protocol.buff.BuffInfo],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.buff.SC10072] {
    private var __requiredFields0: _root_.scala.Long = 0x3L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __fightOrganismId = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 18 =>
            __buffInfo = _root_.scala.Some(__buffInfo.fold(_root_.scalapb.LiteParser.readMessage[protocol.buff.BuffInfo](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
            __requiredFields0 &= 0xfffffffffffffffdL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.buff.SC10072 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.buff.SC10072(
        fightOrganismId = __fightOrganismId,
        buffInfo = __buffInfo.getOrElse(protocol.buff.BuffInfo.defaultInstance),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.buff.SC10072, protocol.buff.SC10072.Builder] {
    def apply(): Builder = new Builder(
      __fightOrganismId = 0L,
      __buffInfo = _root_.scala.None,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.buff.SC10072): Builder = new Builder(
        __fightOrganismId = _message__.fightOrganismId,
        __buffInfo = _root_.scala.Some(_message__.buffInfo),
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.buff.SC10072.Builder()
  def newBuilder(`_message__`: protocol.buff.SC10072): Builder = protocol.buff.SC10072.Builder(_message__)
  implicit class SC10072Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.buff.SC10072]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.buff.SC10072](_l) {
    def fightOrganismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.fightOrganismId)((c_, f_) => c_.copy(fightOrganismId = f_))
    def buffInfo: _root_.scalapb.lenses.Lens[UpperPB, protocol.buff.BuffInfo] = field(_.buffInfo)((c_, f_) => c_.copy(buffInfo = f_))
  }
  final val FIGHTORGANISMID_FIELD_NUMBER = 1
  final val BUFFINFO_FIELD_NUMBER = 2
  def of(
    fightOrganismId: _root_.scala.Long,
    buffInfo: protocol.buff.BuffInfo
  ): _root_.protocol.buff.SC10072 = _root_.protocol.buff.SC10072(
    fightOrganismId,
    buffInfo
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10072])
}
