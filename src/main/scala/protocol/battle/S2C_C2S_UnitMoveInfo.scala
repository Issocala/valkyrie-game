// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.battle

@SerialVersionUID(0L)
final case class S2C_C2S_UnitMoveInfo(
    posStatus: _root_.scala.Int,
    landOrLadderID: _root_.scala.Int,
    posX: _root_.scala.Float,
    posY: _root_.scala.Float,
    speedX: _root_.scala.Float,
    speedY: _root_.scala.Float,
    faceDir: _root_.scala.Float,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[S2C_C2S_UnitMoveInfo] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = posStatus
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(1, __value)
      };
      
      {
        val __value = landOrLadderID
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(2, __value)
      };
      
      {
        val __value = posX
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(3, __value)
      };
      
      {
        val __value = posY
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(4, __value)
      };
      
      {
        val __value = speedX
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(5, __value)
      };
      
      {
        val __value = speedY
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(6, __value)
      };
      
      {
        val __value = faceDir
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(7, __value)
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
        val __v = posStatus
        _output__.writeInt32(1, __v)
      };
      
      {
        val __v = landOrLadderID
        _output__.writeInt32(2, __v)
      };
      
      {
        val __v = posX
        _output__.writeFloat(3, __v)
      };
      
      {
        val __v = posY
        _output__.writeFloat(4, __v)
      };
      
      {
        val __v = speedX
        _output__.writeFloat(5, __v)
      };
      
      {
        val __v = speedY
        _output__.writeFloat(6, __v)
      };
      
      {
        val __v = faceDir
        _output__.writeFloat(7, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withPosStatus(__v: _root_.scala.Int): S2C_C2S_UnitMoveInfo = copy(posStatus = __v)
    def withLandOrLadderID(__v: _root_.scala.Int): S2C_C2S_UnitMoveInfo = copy(landOrLadderID = __v)
    def withPosX(__v: _root_.scala.Float): S2C_C2S_UnitMoveInfo = copy(posX = __v)
    def withPosY(__v: _root_.scala.Float): S2C_C2S_UnitMoveInfo = copy(posY = __v)
    def withSpeedX(__v: _root_.scala.Float): S2C_C2S_UnitMoveInfo = copy(speedX = __v)
    def withSpeedY(__v: _root_.scala.Float): S2C_C2S_UnitMoveInfo = copy(speedY = __v)
    def withFaceDir(__v: _root_.scala.Float): S2C_C2S_UnitMoveInfo = copy(faceDir = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => posStatus
        case 2 => landOrLadderID
        case 3 => posX
        case 4 => posY
        case 5 => speedX
        case 6 => speedY
        case 7 => faceDir
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PInt(posStatus)
        case 2 => _root_.scalapb.descriptors.PInt(landOrLadderID)
        case 3 => _root_.scalapb.descriptors.PFloat(posX)
        case 4 => _root_.scalapb.descriptors.PFloat(posY)
        case 5 => _root_.scalapb.descriptors.PFloat(speedX)
        case 6 => _root_.scalapb.descriptors.PFloat(speedY)
        case 7 => _root_.scalapb.descriptors.PFloat(faceDir)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.battle.S2C_C2S_UnitMoveInfo
    // @@protoc_insertion_point(GeneratedMessage[protocol.S2C_C2S_UnitMoveInfo])
}

object S2C_C2S_UnitMoveInfo extends scalapb.GeneratedMessageCompanion[protocol.battle.S2C_C2S_UnitMoveInfo] with scalapb.HasBuilder[protocol.battle.S2C_C2S_UnitMoveInfo] with scalapb.JavaProtoSupport[protocol.battle.S2C_C2S_UnitMoveInfo, protocol.Battle.S2C_C2S_UnitMoveInfo] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.battle.S2C_C2S_UnitMoveInfo] with scalapb.HasBuilder[protocol.battle.S2C_C2S_UnitMoveInfo] with scalapb.JavaProtoSupport[protocol.battle.S2C_C2S_UnitMoveInfo, protocol.Battle.S2C_C2S_UnitMoveInfo] = this
  def toJavaProto(scalaPbSource: protocol.battle.S2C_C2S_UnitMoveInfo): protocol.Battle.S2C_C2S_UnitMoveInfo = {
    val javaPbOut = protocol.Battle.S2C_C2S_UnitMoveInfo.newBuilder
    javaPbOut.setPosStatus(scalaPbSource.posStatus)
    javaPbOut.setLandOrLadderID(scalaPbSource.landOrLadderID)
    javaPbOut.setPosX(scalaPbSource.posX)
    javaPbOut.setPosY(scalaPbSource.posY)
    javaPbOut.setSpeedX(scalaPbSource.speedX)
    javaPbOut.setSpeedY(scalaPbSource.speedY)
    javaPbOut.setFaceDir(scalaPbSource.faceDir)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Battle.S2C_C2S_UnitMoveInfo): protocol.battle.S2C_C2S_UnitMoveInfo = protocol.battle.S2C_C2S_UnitMoveInfo(
    posStatus = javaPbSource.getPosStatus.intValue,
    landOrLadderID = javaPbSource.getLandOrLadderID.intValue,
    posX = javaPbSource.getPosX.floatValue,
    posY = javaPbSource.getPosY.floatValue,
    speedX = javaPbSource.getSpeedX.floatValue,
    speedY = javaPbSource.getSpeedY.floatValue,
    faceDir = javaPbSource.getFaceDir.floatValue
  )
  def merge(`_message__`: protocol.battle.S2C_C2S_UnitMoveInfo, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.battle.S2C_C2S_UnitMoveInfo = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.battle.S2C_C2S_UnitMoveInfo] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.battle.S2C_C2S_UnitMoveInfo(
        posStatus = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Int],
        landOrLadderID = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Int],
        posX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).get.as[_root_.scala.Float],
        posY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).get.as[_root_.scala.Float],
        speedX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(5).get).get.as[_root_.scala.Float],
        speedY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(6).get).get.as[_root_.scala.Float],
        faceDir = __fieldsMap.get(scalaDescriptor.findFieldByNumber(7).get).get.as[_root_.scala.Float]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = BattleProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = BattleProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.battle.S2C_C2S_UnitMoveInfo(
    posStatus = 0,
    landOrLadderID = 0,
    posX = 0.0f,
    posY = 0.0f,
    speedX = 0.0f,
    speedY = 0.0f,
    faceDir = 0.0f
  )
  final class Builder private (
    private var __posStatus: _root_.scala.Int,
    private var __landOrLadderID: _root_.scala.Int,
    private var __posX: _root_.scala.Float,
    private var __posY: _root_.scala.Float,
    private var __speedX: _root_.scala.Float,
    private var __speedY: _root_.scala.Float,
    private var __faceDir: _root_.scala.Float,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.battle.S2C_C2S_UnitMoveInfo] {
    private var __requiredFields0: _root_.scala.Long = 0x7fL
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __posStatus = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 16 =>
            __landOrLadderID = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffdL
          case 29 =>
            __posX = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffffbL
          case 37 =>
            __posY = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffff7L
          case 45 =>
            __speedX = _input__.readFloat()
            __requiredFields0 &= 0xffffffffffffffefL
          case 53 =>
            __speedY = _input__.readFloat()
            __requiredFields0 &= 0xffffffffffffffdfL
          case 61 =>
            __faceDir = _input__.readFloat()
            __requiredFields0 &= 0xffffffffffffffbfL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.battle.S2C_C2S_UnitMoveInfo = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.battle.S2C_C2S_UnitMoveInfo(
        posStatus = __posStatus,
        landOrLadderID = __landOrLadderID,
        posX = __posX,
        posY = __posY,
        speedX = __speedX,
        speedY = __speedY,
        faceDir = __faceDir,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.battle.S2C_C2S_UnitMoveInfo, protocol.battle.S2C_C2S_UnitMoveInfo.Builder] {
    def apply(): Builder = new Builder(
      __posStatus = 0,
      __landOrLadderID = 0,
      __posX = 0.0f,
      __posY = 0.0f,
      __speedX = 0.0f,
      __speedY = 0.0f,
      __faceDir = 0.0f,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.battle.S2C_C2S_UnitMoveInfo): Builder = new Builder(
        __posStatus = _message__.posStatus,
        __landOrLadderID = _message__.landOrLadderID,
        __posX = _message__.posX,
        __posY = _message__.posY,
        __speedX = _message__.speedX,
        __speedY = _message__.speedY,
        __faceDir = _message__.faceDir,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.battle.S2C_C2S_UnitMoveInfo.Builder()
  def newBuilder(`_message__`: protocol.battle.S2C_C2S_UnitMoveInfo): Builder = protocol.battle.S2C_C2S_UnitMoveInfo.Builder(_message__)
  implicit class S2C_C2S_UnitMoveInfoLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.battle.S2C_C2S_UnitMoveInfo]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.battle.S2C_C2S_UnitMoveInfo](_l) {
    def posStatus: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.posStatus)((c_, f_) => c_.copy(posStatus = f_))
    def landOrLadderID: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.landOrLadderID)((c_, f_) => c_.copy(landOrLadderID = f_))
    def posX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.posX)((c_, f_) => c_.copy(posX = f_))
    def posY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.posY)((c_, f_) => c_.copy(posY = f_))
    def speedX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.speedX)((c_, f_) => c_.copy(speedX = f_))
    def speedY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.speedY)((c_, f_) => c_.copy(speedY = f_))
    def faceDir: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.faceDir)((c_, f_) => c_.copy(faceDir = f_))
  }
  final val POSSTATUS_FIELD_NUMBER = 1
  final val LANDORLADDERID_FIELD_NUMBER = 2
  final val POSX_FIELD_NUMBER = 3
  final val POSY_FIELD_NUMBER = 4
  final val SPEEDX_FIELD_NUMBER = 5
  final val SPEEDY_FIELD_NUMBER = 6
  final val FACEDIR_FIELD_NUMBER = 7
  def of(
    posStatus: _root_.scala.Int,
    landOrLadderID: _root_.scala.Int,
    posX: _root_.scala.Float,
    posY: _root_.scala.Float,
    speedX: _root_.scala.Float,
    speedY: _root_.scala.Float,
    faceDir: _root_.scala.Float
  ): _root_.protocol.battle.S2C_C2S_UnitMoveInfo = _root_.protocol.battle.S2C_C2S_UnitMoveInfo(
    posStatus,
    landOrLadderID,
    posX,
    posY,
    speedX,
    speedY,
    faceDir
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.S2C_C2S_UnitMoveInfo])
}