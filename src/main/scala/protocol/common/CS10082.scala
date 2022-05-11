// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.common

/** 心跳包
  */
@SerialVersionUID(0L)
final case class CS10082(
    serverTime: _root_.scala.Long,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10082] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = serverTime
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
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
        val __v = serverTime
        _output__.writeInt64(1, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withServerTime(__v: _root_.scala.Long): CS10082 = copy(serverTime = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => serverTime
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(serverTime)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.common.CS10082
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10082])
}

object CS10082 extends scalapb.GeneratedMessageCompanion[protocol.common.CS10082] with scalapb.HasBuilder[protocol.common.CS10082] with scalapb.JavaProtoSupport[protocol.common.CS10082, protocol.Common.CS10082] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.common.CS10082] with scalapb.HasBuilder[protocol.common.CS10082] with scalapb.JavaProtoSupport[protocol.common.CS10082, protocol.Common.CS10082] = this
  def toJavaProto(scalaPbSource: protocol.common.CS10082): protocol.Common.CS10082 = {
    val javaPbOut = protocol.Common.CS10082.newBuilder
    javaPbOut.setServerTime(scalaPbSource.serverTime)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Common.CS10082): protocol.common.CS10082 = protocol.common.CS10082(
    serverTime = javaPbSource.getServerTime.longValue
  )
  def merge(`_message__`: protocol.common.CS10082, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.common.CS10082 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.common.CS10082] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.common.CS10082(
        serverTime = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = CommonProto.javaDescriptor.getMessageTypes().get(3)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = CommonProto.scalaDescriptor.messages(3)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.common.CS10082(
    serverTime = 0L
  )
  final class Builder private (
    private var __serverTime: _root_.scala.Long,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.common.CS10082] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __serverTime = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffeL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.common.CS10082 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.common.CS10082(
        serverTime = __serverTime,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.common.CS10082, protocol.common.CS10082.Builder] {
    def apply(): Builder = new Builder(
      __serverTime = 0L,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.common.CS10082): Builder = new Builder(
        __serverTime = _message__.serverTime,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.common.CS10082.Builder()
  def newBuilder(`_message__`: protocol.common.CS10082): Builder = protocol.common.CS10082.Builder(_message__)
  implicit class CS10082Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.common.CS10082]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.common.CS10082](_l) {
    def serverTime: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.serverTime)((c_, f_) => c_.copy(serverTime = f_))
  }
  final val SERVERTIME_FIELD_NUMBER = 1
  def of(
    serverTime: _root_.scala.Long
  ): _root_.protocol.common.CS10082 = _root_.protocol.common.CS10082(
    serverTime
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10082])
}