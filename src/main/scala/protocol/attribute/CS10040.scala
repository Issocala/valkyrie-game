// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.attribute

/** 请求战斗数值
  */
@SerialVersionUID(0L)
final case class CS10040(
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10040] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
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
      unknownFields.writeTo(_output__)
    }
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = throw new MatchError(__fieldNumber)
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = throw new MatchError(__field)
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.attribute.CS10040
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10040])
}

object CS10040 extends scalapb.GeneratedMessageCompanion[protocol.attribute.CS10040] with scalapb.HasBuilder[protocol.attribute.CS10040] with scalapb.JavaProtoSupport[protocol.attribute.CS10040, protocol.Attribute.CS10040] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.attribute.CS10040] with scalapb.HasBuilder[protocol.attribute.CS10040] with scalapb.JavaProtoSupport[protocol.attribute.CS10040, protocol.Attribute.CS10040] = this
  def toJavaProto(scalaPbSource: protocol.attribute.CS10040): protocol.Attribute.CS10040 = {
    val javaPbOut = protocol.Attribute.CS10040.newBuilder
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Attribute.CS10040): protocol.attribute.CS10040 = protocol.attribute.CS10040(
  )
  def merge(`_message__`: protocol.attribute.CS10040, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.attribute.CS10040 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.attribute.CS10040] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.attribute.CS10040(
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = AttributeProto.javaDescriptor.getMessageTypes().get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = AttributeProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.attribute.CS10040(
  )
  final class Builder private (
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.attribute.CS10040] {
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.attribute.CS10040 = {
      protocol.attribute.CS10040(
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.attribute.CS10040, protocol.attribute.CS10040.Builder] {
    def apply(): Builder = new Builder(
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.attribute.CS10040): Builder = new Builder(
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.attribute.CS10040.Builder()
  def newBuilder(`_message__`: protocol.attribute.CS10040): Builder = protocol.attribute.CS10040.Builder(_message__)
  implicit class CS10040Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.attribute.CS10040]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.attribute.CS10040](_l) {
  }
  def of(
  ): _root_.protocol.attribute.CS10040 = _root_.protocol.attribute.CS10040(
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10040])
}
