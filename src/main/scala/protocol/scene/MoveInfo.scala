// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

/** @param face
  *  面部朝向(0代表看向右边(默认),1代表看向左边)
  * @param direction
  *  人物移动方向(0右,90下,-90上,180左)
  */
@SerialVersionUID(0L)
final case class MoveInfo(
    positionX: _root_.scala.Float,
    positionY: _root_.scala.Float,
    face: _root_.scala.Int,
    direction: _root_.scala.Float,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[MoveInfo] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = positionX
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(1, __value)
      };
      
      {
        val __value = positionY
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(2, __value)
      };
      
      {
        val __value = face
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(3, __value)
      };
      
      {
        val __value = direction
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(4, __value)
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
        val __v = positionX
        _output__.writeFloat(1, __v)
      };
      
      {
        val __v = positionY
        _output__.writeFloat(2, __v)
      };
      
      {
        val __v = face
        _output__.writeInt32(3, __v)
      };
      
      {
        val __v = direction
        _output__.writeFloat(4, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withPositionX(__v: _root_.scala.Float): MoveInfo = copy(positionX = __v)
    def withPositionY(__v: _root_.scala.Float): MoveInfo = copy(positionY = __v)
    def withFace(__v: _root_.scala.Int): MoveInfo = copy(face = __v)
    def withDirection(__v: _root_.scala.Float): MoveInfo = copy(direction = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => positionX
        case 2 => positionY
        case 3 => face
        case 4 => direction
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PFloat(positionX)
        case 2 => _root_.scalapb.descriptors.PFloat(positionY)
        case 3 => _root_.scalapb.descriptors.PInt(face)
        case 4 => _root_.scalapb.descriptors.PFloat(direction)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.scene.MoveInfo
    // @@protoc_insertion_point(GeneratedMessage[protocol.MoveInfo])
}

object MoveInfo extends scalapb.GeneratedMessageCompanion[protocol.scene.MoveInfo] with scalapb.HasBuilder[protocol.scene.MoveInfo] with scalapb.JavaProtoSupport[protocol.scene.MoveInfo, protocol.Scene.MoveInfo] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.MoveInfo] with scalapb.HasBuilder[protocol.scene.MoveInfo] with scalapb.JavaProtoSupport[protocol.scene.MoveInfo, protocol.Scene.MoveInfo] = this
  def toJavaProto(scalaPbSource: protocol.scene.MoveInfo): protocol.Scene.MoveInfo = {
    val javaPbOut = protocol.Scene.MoveInfo.newBuilder
    javaPbOut.setPositionX(scalaPbSource.positionX)
    javaPbOut.setPositionY(scalaPbSource.positionY)
    javaPbOut.setFace(scalaPbSource.face)
    javaPbOut.setDirection(scalaPbSource.direction)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Scene.MoveInfo): protocol.scene.MoveInfo = protocol.scene.MoveInfo(
    positionX = javaPbSource.getPositionX.floatValue,
    positionY = javaPbSource.getPositionY.floatValue,
    face = javaPbSource.getFace.intValue,
    direction = javaPbSource.getDirection.floatValue
  )
  def merge(`_message__`: protocol.scene.MoveInfo, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.MoveInfo = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.MoveInfo] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.MoveInfo(
        positionX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Float],
        positionY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Float],
        face = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).get.as[_root_.scala.Int],
        direction = __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).get.as[_root_.scala.Float]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.scene.MoveInfo(
    positionX = 0.0f,
    positionY = 0.0f,
    face = 0,
    direction = 0.0f
  )
  final class Builder private (
    private var __positionX: _root_.scala.Float,
    private var __positionY: _root_.scala.Float,
    private var __face: _root_.scala.Int,
    private var __direction: _root_.scala.Float,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.scene.MoveInfo] {
    private var __requiredFields0: _root_.scala.Long = 0xfL
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 13 =>
            __positionX = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 21 =>
            __positionY = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffffdL
          case 24 =>
            __face = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffbL
          case 37 =>
            __direction = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffff7L
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.scene.MoveInfo = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.scene.MoveInfo(
        positionX = __positionX,
        positionY = __positionY,
        face = __face,
        direction = __direction,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.MoveInfo, protocol.scene.MoveInfo.Builder] {
    def apply(): Builder = new Builder(
      __positionX = 0.0f,
      __positionY = 0.0f,
      __face = 0,
      __direction = 0.0f,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.scene.MoveInfo): Builder = new Builder(
        __positionX = _message__.positionX,
        __positionY = _message__.positionY,
        __face = _message__.face,
        __direction = _message__.direction,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.scene.MoveInfo.Builder()
  def newBuilder(`_message__`: protocol.scene.MoveInfo): Builder = protocol.scene.MoveInfo.Builder(_message__)
  implicit class MoveInfoLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.MoveInfo]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.MoveInfo](_l) {
    def positionX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionX)((c_, f_) => c_.copy(positionX = f_))
    def positionY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionY)((c_, f_) => c_.copy(positionY = f_))
    def face: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.face)((c_, f_) => c_.copy(face = f_))
    def direction: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.direction)((c_, f_) => c_.copy(direction = f_))
  }
  final val POSITIONX_FIELD_NUMBER = 1
  final val POSITIONY_FIELD_NUMBER = 2
  final val FACE_FIELD_NUMBER = 3
  final val DIRECTION_FIELD_NUMBER = 4
  def of(
    positionX: _root_.scala.Float,
    positionY: _root_.scala.Float,
    face: _root_.scala.Int,
    direction: _root_.scala.Float
  ): _root_.protocol.scene.MoveInfo = _root_.protocol.scene.MoveInfo(
    positionX,
    positionY,
    face,
    direction
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.MoveInfo])
}
