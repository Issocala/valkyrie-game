// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.battle

@SerialVersionUID(0L)
final case class S2C_LeftLimit(
                                leftLimit: _root_.scala.Int,
                                unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                              ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[S2C_LeftLimit] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = leftLimit
      __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(1, __value)
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
      val __v = leftLimit
      _output__.writeInt32(1, __v)
    };
    unknownFields.writeTo(_output__)
  }

  def withLeftLimit(__v: _root_.scala.Int): S2C_LeftLimit = copy(leftLimit = __v)

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => leftLimit
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PInt(leftLimit)
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.battle.S2C_LeftLimit
  // @@protoc_insertion_point(GeneratedMessage[protocol.S2C_LeftLimit])
}

object S2C_LeftLimit extends scalapb.GeneratedMessageCompanion[protocol.battle.S2C_LeftLimit] with scalapb.HasBuilder[protocol.battle.S2C_LeftLimit] with scalapb.JavaProtoSupport[protocol.battle.S2C_LeftLimit, protocol.Battle.S2C_LeftLimit] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.battle.S2C_LeftLimit] with scalapb.HasBuilder[protocol.battle.S2C_LeftLimit] with scalapb.JavaProtoSupport[protocol.battle.S2C_LeftLimit, protocol.Battle.S2C_LeftLimit] = this

  def toJavaProto(scalaPbSource: protocol.battle.S2C_LeftLimit): protocol.Battle.S2C_LeftLimit = {
    val javaPbOut = protocol.Battle.S2C_LeftLimit.newBuilder
    javaPbOut.setLeftLimit(scalaPbSource.leftLimit)
    javaPbOut.build
  }

  def fromJavaProto(javaPbSource: protocol.Battle.S2C_LeftLimit): protocol.battle.S2C_LeftLimit = protocol.battle.S2C_LeftLimit(
    leftLimit = javaPbSource.getLeftLimit.intValue
  )

  def merge(`_message__`: protocol.battle.S2C_LeftLimit, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.battle.S2C_LeftLimit = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.battle.S2C_LeftLimit] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.battle.S2C_LeftLimit(
        leftLimit = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Int]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = BattleProto.javaDescriptor.getMessageTypes().get(8)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = BattleProto.scalaDescriptor.messages(8)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.battle.S2C_LeftLimit(
    leftLimit = 0
  )

  final class Builder private(
                               private var __leftLimit: _root_.scala.Int,
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.battle.S2C_LeftLimit] {
    private var __requiredFields0: _root_.scala.Long = 0x1L

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __leftLimit = _input__.readInt32()
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

    def result(): protocol.battle.S2C_LeftLimit = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.battle.S2C_LeftLimit(
        leftLimit = __leftLimit,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }

  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.battle.S2C_LeftLimit, protocol.battle.S2C_LeftLimit.Builder] {
    def apply(): Builder = new Builder(
      __leftLimit = 0,
      `_unknownFields__` = null
    )

    def apply(`_message__`: protocol.battle.S2C_LeftLimit): Builder = new Builder(
      __leftLimit = _message__.leftLimit,
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }

  def newBuilder: Builder = protocol.battle.S2C_LeftLimit.Builder()

  def newBuilder(`_message__`: protocol.battle.S2C_LeftLimit): Builder = protocol.battle.S2C_LeftLimit.Builder(_message__)

  implicit class S2C_LeftLimitLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.battle.S2C_LeftLimit]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.battle.S2C_LeftLimit](_l) {
    def leftLimit: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.leftLimit)((c_, f_) => c_.copy(leftLimit = f_))
  }

  final val LEFTLIMIT_FIELD_NUMBER = 1

  def of(
          leftLimit: _root_.scala.Int
        ): _root_.protocol.battle.S2C_LeftLimit = _root_.protocol.battle.S2C_LeftLimit(
    leftLimit
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.S2C_LeftLimit])
}