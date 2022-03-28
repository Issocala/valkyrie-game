// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

/** 玩家退出场景
 */
@SerialVersionUID(0L)
final case class CS10031(
                          sceneId: _root_.scala.Long,
                          unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                        ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10031] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = sceneId
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
      val __v = sceneId
      _output__.writeInt64(1, __v)
    };
    unknownFields.writeTo(_output__)
  }

  def withSceneId(__v: _root_.scala.Long): CS10031 = copy(sceneId = __v)

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => sceneId
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PLong(sceneId)
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.scene.CS10031
  // @@protoc_insertion_point(GeneratedMessage[protocol.CS10031])
}

object CS10031 extends scalapb.GeneratedMessageCompanion[protocol.scene.CS10031] with scalapb.HasBuilder[protocol.scene.CS10031] with scalapb.JavaProtoSupport[protocol.scene.CS10031, protocol.Scene.CS10031] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.CS10031] with scalapb.HasBuilder[protocol.scene.CS10031] with scalapb.JavaProtoSupport[protocol.scene.CS10031, protocol.Scene.CS10031] = this
  def toJavaProto(scalaPbSource: protocol.scene.CS10031): protocol.Scene.CS10031 = {
    val javaPbOut = protocol.Scene.CS10031.newBuilder
    javaPbOut.setSceneId(scalaPbSource.sceneId)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Scene.CS10031): protocol.scene.CS10031 = protocol.scene.CS10031(
    sceneId = javaPbSource.getSceneId.longValue
  )

  def merge(`_message__`: protocol.scene.CS10031, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.CS10031 = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.CS10031] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.CS10031(
        sceneId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(5)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(5)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.scene.CS10031(
    sceneId = 0L
  )

  final class Builder private(
                               private var __sceneId: _root_.scala.Long,
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.scene.CS10031] {
    private var __requiredFields0: _root_.scala.Long = 0x1L

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __sceneId = _input__.readInt64()
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
    def result(): protocol.scene.CS10031 = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.scene.CS10031(
        sceneId = __sceneId,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.CS10031, protocol.scene.CS10031.Builder] {
    def apply(): Builder = new Builder(
      __sceneId = 0L,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.scene.CS10031): Builder = new Builder(
      __sceneId = _message__.sceneId,
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.scene.CS10031.Builder()
  def newBuilder(`_message__`: protocol.scene.CS10031): Builder = protocol.scene.CS10031.Builder(_message__)
  implicit class CS10031Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.CS10031]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.CS10031](_l) {
    def sceneId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.sceneId)((c_, f_) => c_.copy(sceneId = f_))
  }
  final val SCENEID_FIELD_NUMBER = 1

  def of(
          sceneId: _root_.scala.Long
        ): _root_.protocol.scene.CS10031 = _root_.protocol.scene.CS10031(
    sceneId
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10031])
}
