// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.player

/** 选择角色
 */
@SerialVersionUID(0L)
final case class CS10022(
                          roleId: _root_.scala.Long,
                          unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                        ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10022] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = roleId
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
      val __v = roleId
      _output__.writeInt64(1, __v)
    };
    unknownFields.writeTo(_output__)
  }

  def withRoleId(__v: _root_.scala.Long): CS10022 = copy(roleId = __v)

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => roleId
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PLong(roleId)
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.player.CS10022
  // @@protoc_insertion_point(GeneratedMessage[protocol.CS10022])
}

object CS10022 extends scalapb.GeneratedMessageCompanion[protocol.player.CS10022] with scalapb.HasBuilder[protocol.player.CS10022] with scalapb.JavaProtoSupport[protocol.player.CS10022, protocol.Player.CS10022] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.player.CS10022] with scalapb.HasBuilder[protocol.player.CS10022] with scalapb.JavaProtoSupport[protocol.player.CS10022, protocol.Player.CS10022] = this

  def toJavaProto(scalaPbSource: protocol.player.CS10022): protocol.Player.CS10022 = {
    val javaPbOut = protocol.Player.CS10022.newBuilder
    javaPbOut.setRoleId(scalaPbSource.roleId)
    javaPbOut.build
  }

  def fromJavaProto(javaPbSource: protocol.Player.CS10022): protocol.player.CS10022 = protocol.player.CS10022(
    roleId = javaPbSource.getRoleId.longValue
  )

  def merge(`_message__`: protocol.player.CS10022, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.player.CS10022 = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.player.CS10022] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.player.CS10022(
        roleId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = PlayerProto.javaDescriptor.getMessageTypes().get(5)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = PlayerProto.scalaDescriptor.messages(5)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.player.CS10022(
    roleId = 0L
  )

  final class Builder private(
                               private var __roleId: _root_.scala.Long,
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.player.CS10022] {
    private var __requiredFields0: _root_.scala.Long = 0x1L

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __roleId = _input__.readInt64()
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

    def result(): protocol.player.CS10022 = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.player.CS10022(
        roleId = __roleId,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }

  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.player.CS10022, protocol.player.CS10022.Builder] {
    def apply(): Builder = new Builder(
      __roleId = 0L,
      `_unknownFields__` = null
    )

    def apply(`_message__`: protocol.player.CS10022): Builder = new Builder(
      __roleId = _message__.roleId,
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }

  def newBuilder: Builder = protocol.player.CS10022.Builder()

  def newBuilder(`_message__`: protocol.player.CS10022): Builder = protocol.player.CS10022.Builder(_message__)

  implicit class CS10022Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.player.CS10022]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.player.CS10022](_l) {
    def roleId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.roleId)((c_, f_) => c_.copy(roleId = f_))
  }

  final val ROLEID_FIELD_NUMBER = 1

  def of(
          roleId: _root_.scala.Long
        ): _root_.protocol.player.CS10022 = _root_.protocol.player.CS10022(
    roleId
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10022])
}
