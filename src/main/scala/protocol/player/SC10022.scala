// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.player

/** 选角结果
 */
@SerialVersionUID(0L)
final case class SC10022(
                          success: _root_.scala.Boolean,
                          roleId: _root_.scala.Option[_root_.scala.Long] = _root_.scala.None,
                          content: _root_.scala.Option[_root_.scala.Predef.String] = _root_.scala.None,
                          unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                        ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10022] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = success
      __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(1, __value)
    };
    if (roleId.isDefined) {
      val __value = roleId.get
      __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(2, __value)
    };
    if (content.isDefined) {
      val __value = content.get
      __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(3, __value)
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
      val __v = success
      _output__.writeBool(1, __v)
    };
    roleId.foreach { __v =>
      val __m = __v
      _output__.writeInt64(2, __m)
    };
    content.foreach { __v =>
      val __m = __v
      _output__.writeString(3, __m)
    };
    unknownFields.writeTo(_output__)
  }

  def withSuccess(__v: _root_.scala.Boolean): SC10022 = copy(success = __v)

  def getRoleId: _root_.scala.Long = roleId.getOrElse(0L)

  def clearRoleId: SC10022 = copy(roleId = _root_.scala.None)

  def withRoleId(__v: _root_.scala.Long): SC10022 = copy(roleId = Option(__v))

  def getContent: _root_.scala.Predef.String = content.getOrElse("")

  def clearContent: SC10022 = copy(content = _root_.scala.None)

  def withContent(__v: _root_.scala.Predef.String): SC10022 = copy(content = Option(__v))

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => success
      case 2 => roleId.orNull
      case 3 => content.orNull
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PBoolean(success)
      case 2 => roleId.map(_root_.scalapb.descriptors.PLong(_)).getOrElse(_root_.scalapb.descriptors.PEmpty)
      case 3 => content.map(_root_.scalapb.descriptors.PString(_)).getOrElse(_root_.scalapb.descriptors.PEmpty)
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.player.SC10022
  // @@protoc_insertion_point(GeneratedMessage[protocol.SC10022])
}

object SC10022 extends scalapb.GeneratedMessageCompanion[protocol.player.SC10022] with scalapb.HasBuilder[protocol.player.SC10022] with scalapb.JavaProtoSupport[protocol.player.SC10022, protocol.Player.SC10022] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.player.SC10022] with scalapb.HasBuilder[protocol.player.SC10022] with scalapb.JavaProtoSupport[protocol.player.SC10022, protocol.Player.SC10022] = this

  def toJavaProto(scalaPbSource: protocol.player.SC10022): protocol.Player.SC10022 = {
    val javaPbOut = protocol.Player.SC10022.newBuilder
    javaPbOut.setSuccess(scalaPbSource.success)
    scalaPbSource.roleId.foreach(javaPbOut.setRoleId)
    scalaPbSource.content.foreach(javaPbOut.setContent)
    javaPbOut.build
  }

  def fromJavaProto(javaPbSource: protocol.Player.SC10022): protocol.player.SC10022 = protocol.player.SC10022(
    success = javaPbSource.getSuccess.booleanValue,
    roleId = if (javaPbSource.hasRoleId) Some(javaPbSource.getRoleId.longValue) else _root_.scala.None,
    content = if (javaPbSource.hasContent) Some(javaPbSource.getContent) else _root_.scala.None
  )

  def merge(`_message__`: protocol.player.SC10022, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.player.SC10022 = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.player.SC10022] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.player.SC10022(
        success = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Boolean],
        roleId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).flatMap(_.as[_root_.scala.Option[_root_.scala.Long]]),
        content = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).flatMap(_.as[_root_.scala.Option[_root_.scala.Predef.String]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = PlayerProto.javaDescriptor.getMessageTypes().get(6)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = PlayerProto.scalaDescriptor.messages(6)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.player.SC10022(
    success = false,
    roleId = _root_.scala.None,
    content = _root_.scala.None
  )

  final class Builder private(
                               private var __success: _root_.scala.Boolean,
                               private var __roleId: _root_.scala.Option[_root_.scala.Long],
                               private var __content: _root_.scala.Option[_root_.scala.Predef.String],
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.player.SC10022] {
    private var __requiredFields0: _root_.scala.Long = 0x1L

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __success = _input__.readBool()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 16 =>
            __roleId = Option(_input__.readInt64())
          case 26 =>
            __content = Option(_input__.readStringRequireUtf8())
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }

    def result(): protocol.player.SC10022 = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.player.SC10022(
        success = __success,
        roleId = __roleId,
        content = __content,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }

  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.player.SC10022, protocol.player.SC10022.Builder] {
    def apply(): Builder = new Builder(
      __success = false,
      __roleId = _root_.scala.None,
      __content = _root_.scala.None,
      `_unknownFields__` = null
    )

    def apply(`_message__`: protocol.player.SC10022): Builder = new Builder(
      __success = _message__.success,
      __roleId = _message__.roleId,
      __content = _message__.content,
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }

  def newBuilder: Builder = protocol.player.SC10022.Builder()

  def newBuilder(`_message__`: protocol.player.SC10022): Builder = protocol.player.SC10022.Builder(_message__)

  implicit class SC10022Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.player.SC10022]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.player.SC10022](_l) {
    def success: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.success)((c_, f_) => c_.copy(success = f_))

    def roleId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.getRoleId)((c_, f_) => c_.copy(roleId = Option(f_)))

    def optionalRoleId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[_root_.scala.Long]] = field(_.roleId)((c_, f_) => c_.copy(roleId = f_))

    def content: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.getContent)((c_, f_) => c_.copy(content = Option(f_)))

    def optionalContent: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[_root_.scala.Predef.String]] = field(_.content)((c_, f_) => c_.copy(content = f_))
  }

  final val SUCCESS_FIELD_NUMBER = 1
  final val ROLEID_FIELD_NUMBER = 2
  final val CONTENT_FIELD_NUMBER = 3

  def of(
          success: _root_.scala.Boolean,
          roleId: _root_.scala.Option[_root_.scala.Long],
          content: _root_.scala.Option[_root_.scala.Predef.String]
        ): _root_.protocol.player.SC10022 = _root_.protocol.player.SC10022(
    success,
    roleId,
    content
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10022])
}
