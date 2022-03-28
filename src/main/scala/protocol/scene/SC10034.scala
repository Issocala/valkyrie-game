// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

/** 返回创建某实体
 *
 * @param organismType
 * 1-&gt;player,2-&gt;boss
 */
@SerialVersionUID(0L)
final case class SC10034(
                          organismId: _root_.scala.Long,
                          organismType: _root_.scala.Int,
                          positionX: _root_.scala.Float,
                          positionY: _root_.scala.Float,
                          face: _root_.scala.Int,
                          unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
                        ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10034] {
  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0

  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = organismId
      __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
    };

    {
      val __value = organismType
      __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(2, __value)
    };

    {
      val __value = positionX
      __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(3, __value)
    };

    {
      val __value = positionY
      __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(4, __value)
    };

    {
      val __value = face
      __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(5, __value)
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
      val __v = organismId
      _output__.writeInt64(1, __v)
    };

    {
      val __v = organismType
      _output__.writeInt32(2, __v)
    };

    {
      val __v = positionX
      _output__.writeFloat(3, __v)
    };

    {
      val __v = positionY
      _output__.writeFloat(4, __v)
    };

    {
      val __v = face
      _output__.writeInt32(5, __v)
    };
    unknownFields.writeTo(_output__)
  }

  def withOrganismId(__v: _root_.scala.Long): SC10034 = copy(organismId = __v)

  def withOrganismType(__v: _root_.scala.Int): SC10034 = copy(organismType = __v)

  def withPositionX(__v: _root_.scala.Float): SC10034 = copy(positionX = __v)

  def withPositionY(__v: _root_.scala.Float): SC10034 = copy(positionY = __v)

  def withFace(__v: _root_.scala.Int): SC10034 = copy(face = __v)

  def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)

  def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @_root_.scala.unchecked) match {
      case 1 => organismId
      case 2 => organismType
      case 3 => positionX
      case 4 => positionY
      case 5 => face
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @_root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PLong(organismId)
      case 2 => _root_.scalapb.descriptors.PInt(organismType)
      case 3 => _root_.scalapb.descriptors.PFloat(positionX)
      case 4 => _root_.scalapb.descriptors.PFloat(positionY)
      case 5 => _root_.scalapb.descriptors.PInt(face)
    }
  }

  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)

  def companion = protocol.scene.SC10034
  // @@protoc_insertion_point(GeneratedMessage[protocol.SC10034])
}

object SC10034 extends scalapb.GeneratedMessageCompanion[protocol.scene.SC10034] with scalapb.HasBuilder[protocol.scene.SC10034] with scalapb.JavaProtoSupport[protocol.scene.SC10034, protocol.Scene.SC10034] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.SC10034] with scalapb.HasBuilder[protocol.scene.SC10034] with scalapb.JavaProtoSupport[protocol.scene.SC10034, protocol.Scene.SC10034] = this
  def toJavaProto(scalaPbSource: protocol.scene.SC10034): protocol.Scene.SC10034 = {
    val javaPbOut = protocol.Scene.SC10034.newBuilder
    javaPbOut.setOrganismId(scalaPbSource.organismId)
    javaPbOut.setOrganismType(scalaPbSource.organismType)
    javaPbOut.setPositionX(scalaPbSource.positionX)
    javaPbOut.setPositionY(scalaPbSource.positionY)
    javaPbOut.setFace(scalaPbSource.face)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Scene.SC10034): protocol.scene.SC10034 = protocol.scene.SC10034(
    organismId = javaPbSource.getOrganismId.longValue,
    organismType = javaPbSource.getOrganismType.intValue,
    positionX = javaPbSource.getPositionX.floatValue,
    positionY = javaPbSource.getPositionY.floatValue,
    face = javaPbSource.getFace.intValue
  )

  def merge(`_message__`: protocol.scene.SC10034, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.SC10034 = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.SC10034] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.SC10034(
        organismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        organismType = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Int],
        positionX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).get.as[_root_.scala.Float],
        positionY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).get.as[_root_.scala.Float],
        face = __fieldsMap.get(scalaDescriptor.findFieldByNumber(5).get).get.as[_root_.scala.Int]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(11)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(11)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.scene.SC10034(
    organismId = 0L,
    organismType = 0,
    positionX = 0.0f,
    positionY = 0.0f,
    face = 0
  )

  final class Builder private(
                               private var __organismId: _root_.scala.Long,
                               private var __organismType: _root_.scala.Int,
                               private var __positionX: _root_.scala.Float,
                               private var __positionY: _root_.scala.Float,
                               private var __face: _root_.scala.Int,
                               private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
                             ) extends _root_.scalapb.MessageBuilder[protocol.scene.SC10034] {
    private var __requiredFields0: _root_.scala.Long = 0x1fL

    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __organismId = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 16 =>
            __organismType = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffdL
          case 29 =>
            __positionX = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffffbL
          case 37 =>
            __positionY = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffff7L
          case 40 =>
            __face = _input__.readInt32()
            __requiredFields0 &= 0xffffffffffffffefL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.scene.SC10034 = {
      if (__requiredFields0 != 0L) {
        throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.")
      }
      protocol.scene.SC10034(
        organismId = __organismId,
        organismType = __organismType,
        positionX = __positionX,
        positionY = __positionY,
        face = __face,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.SC10034, protocol.scene.SC10034.Builder] {
    def apply(): Builder = new Builder(
      __organismId = 0L,
      __organismType = 0,
      __positionX = 0.0f,
      __positionY = 0.0f,
      __face = 0,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.scene.SC10034): Builder = new Builder(
      __organismId = _message__.organismId,
      __organismType = _message__.organismType,
      __positionX = _message__.positionX,
      __positionY = _message__.positionY,
      __face = _message__.face,
      `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.scene.SC10034.Builder()
  def newBuilder(`_message__`: protocol.scene.SC10034): Builder = protocol.scene.SC10034.Builder(_message__)
  implicit class SC10034Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.SC10034]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.SC10034](_l) {
    def organismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.organismId)((c_, f_) => c_.copy(organismId = f_))
    def organismType: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.organismType)((c_, f_) => c_.copy(organismType = f_))
    def positionX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionX)((c_, f_) => c_.copy(positionX = f_))
    def positionY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionY)((c_, f_) => c_.copy(positionY = f_))
    def face: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.face)((c_, f_) => c_.copy(face = f_))
  }
  final val ORGANISMID_FIELD_NUMBER = 1
  final val ORGANISMTYPE_FIELD_NUMBER = 2
  final val POSITIONX_FIELD_NUMBER = 3
  final val POSITIONY_FIELD_NUMBER = 4
  final val FACE_FIELD_NUMBER = 5

  def of(
          organismId: _root_.scala.Long,
          organismType: _root_.scala.Int,
          positionX: _root_.scala.Float,
          positionY: _root_.scala.Float,
          face: _root_.scala.Int
        ): _root_.protocol.scene.SC10034 = _root_.protocol.scene.SC10034(
    organismId,
    organismType,
    positionX,
    positionY,
    face
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10034])
}
