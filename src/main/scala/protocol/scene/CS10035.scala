// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

/** 玩家跳跃
 */
@SerialVersionUID(0L)
final case class CS10035(
                          jumpInfo: protocol.scene.JumpInfo,
                          unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                        ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10035] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = jumpInfo
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
      val __v = jumpInfo
      _output__.writeTag(1, 2)
      _output__.writeUInt32NoTag(__v.serializedSize)
      __v.writeTo(_output__)
    };
    unknownFields.writeTo(_output__)
  }

  def withJumpInfo(__v: protocol.scene.JumpInfo): CS10035 = copy(jumpInfo = __v)

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => jumpInfo
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => jumpInfo.toPMessage
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.scene.CS10035
  // @@protoc_insertion_point(GeneratedMessage[protocol.CS10035])
}

object CS10035 extends scalapb.GeneratedMessageCompanion[protocol.scene.CS10035] with scalapb.HasBuilder[protocol.scene.CS10035] with scalapb.JavaProtoSupport[protocol.scene.CS10035, protocol.Scene.CS10035] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.CS10035] with scalapb.HasBuilder[protocol.scene.CS10035] with scalapb.JavaProtoSupport[protocol.scene.CS10035, protocol.Scene.CS10035] = this

  def toJavaProto(scalaPbSource: protocol.scene.CS10035): protocol.Scene.CS10035 = {
    val javaPbOut = protocol.Scene.CS10035.newBuilder
    javaPbOut.setJumpInfo(protocol.scene.JumpInfo.toJavaProto(scalaPbSource.jumpInfo))
    javaPbOut.build
  }

  def fromJavaProto(javaPbSource: protocol.Scene.CS10035): protocol.scene.CS10035 = protocol.scene.CS10035(
    jumpInfo = protocol.scene.JumpInfo.fromJavaProto(javaPbSource.getJumpInfo)
  )

  def merge(`_message__`: protocol.scene.CS10035, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.CS10035 = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.CS10035] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.CS10035(
        jumpInfo = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[protocol.scene.JumpInfo]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(12)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(12)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = protocol.scene.JumpInfo
    }
    __out
  }

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.scene.CS10035(
    jumpInfo = protocol.scene.JumpInfo.defaultInstance
  )

  final class Builder private(
                               private var __jumpInfo: _root_.scala.Option[protocol.scene.JumpInfo],
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.scene.CS10035] {
    private var __requiredFields0: _root_.scala.Long = 0x1L

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __jumpInfo = _root_.scala.Some(__jumpInfo.fold(_root_.scalapb.LiteParser.readMessage[protocol.scene.JumpInfo](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
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

    def result(): protocol.scene.CS10035 = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.scene.CS10035(
        jumpInfo = __jumpInfo.getOrElse(protocol.scene.JumpInfo.defaultInstance),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }

  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.CS10035, protocol.scene.CS10035.Builder] {
    def apply(): Builder = new Builder(
      __jumpInfo = _root_.scala.None,
      `_unknownFields__` = null
    )

    def apply(`_message__`: protocol.scene.CS10035): Builder = new Builder(
      __jumpInfo = _root_.scala.Some(_message__.jumpInfo),
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }

  def newBuilder: Builder = protocol.scene.CS10035.Builder()

  def newBuilder(`_message__`: protocol.scene.CS10035): Builder = protocol.scene.CS10035.Builder(_message__)

  implicit class CS10035Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.CS10035]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.CS10035](_l) {
    def jumpInfo: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.JumpInfo] = field(_.jumpInfo)((c_, f_) => c_.copy(jumpInfo = f_))
  }

  final val JUMPINFO_FIELD_NUMBER = 1

  def of(
          jumpInfo: protocol.scene.JumpInfo
        ): _root_.protocol.scene.CS10035 = _root_.protocol.scene.CS10035(
    jumpInfo
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10035])
}
