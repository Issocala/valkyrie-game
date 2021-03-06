// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

@SerialVersionUID(0L)
final case class SC10306(
    positionX: _root_.scala.Float,
    positionY: _root_.scala.Float,
    organismId: _root_.scala.Long,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10306] {
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
        val __value = organismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(3, __value)
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
        val __v = organismId
        _output__.writeInt64(3, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withPositionX(__v: _root_.scala.Float): SC10306 = copy(positionX = __v)
    def withPositionY(__v: _root_.scala.Float): SC10306 = copy(positionY = __v)
    def withOrganismId(__v: _root_.scala.Long): SC10306 = copy(organismId = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => positionX
        case 2 => positionY
        case 3 => organismId
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PFloat(positionX)
        case 2 => _root_.scalapb.descriptors.PFloat(positionY)
        case 3 => _root_.scalapb.descriptors.PLong(organismId)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.scene.SC10306
    // @@protoc_insertion_point(GeneratedMessage[protocol.SC10306])
}

object SC10306 extends scalapb.GeneratedMessageCompanion[protocol.scene.SC10306] with scalapb.HasBuilder[protocol.scene.SC10306] with scalapb.JavaProtoSupport[protocol.scene.SC10306, protocol.Scene.SC10306] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.SC10306] with scalapb.HasBuilder[protocol.scene.SC10306] with scalapb.JavaProtoSupport[protocol.scene.SC10306, protocol.Scene.SC10306] = this
  def toJavaProto(scalaPbSource: protocol.scene.SC10306): protocol.Scene.SC10306 = {
    val javaPbOut = protocol.Scene.SC10306.newBuilder
    javaPbOut.setPositionX(scalaPbSource.positionX)
    javaPbOut.setPositionY(scalaPbSource.positionY)
    javaPbOut.setOrganismId(scalaPbSource.organismId)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Scene.SC10306): protocol.scene.SC10306 = protocol.scene.SC10306(
    positionX = javaPbSource.getPositionX.floatValue,
    positionY = javaPbSource.getPositionY.floatValue,
    organismId = javaPbSource.getOrganismId.longValue
  )
  def merge(`_message__`: protocol.scene.SC10306, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.SC10306 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.SC10306] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.SC10306(
        positionX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Float],
        positionY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Float],
        organismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).get.as[_root_.scala.Long]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(16)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(16)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.scene.SC10306(
    positionX = 0.0f,
    positionY = 0.0f,
    organismId = 0L
  )
  final class Builder private (
    private var __positionX: _root_.scala.Float,
    private var __positionY: _root_.scala.Float,
    private var __organismId: _root_.scala.Long,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.scene.SC10306] {
    private var __requiredFields0: _root_.scala.Long = 0x7L
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
            __organismId = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffbL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.scene.SC10306 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.scene.SC10306(
        positionX = __positionX,
        positionY = __positionY,
        organismId = __organismId,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.SC10306, protocol.scene.SC10306.Builder] {
    def apply(): Builder = new Builder(
      __positionX = 0.0f,
      __positionY = 0.0f,
      __organismId = 0L,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.scene.SC10306): Builder = new Builder(
        __positionX = _message__.positionX,
        __positionY = _message__.positionY,
        __organismId = _message__.organismId,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.scene.SC10306.Builder()
  def newBuilder(`_message__`: protocol.scene.SC10306): Builder = protocol.scene.SC10306.Builder(_message__)
  implicit class SC10306Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.SC10306]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.SC10306](_l) {
    def positionX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionX)((c_, f_) => c_.copy(positionX = f_))
    def positionY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.positionY)((c_, f_) => c_.copy(positionY = f_))
    def organismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.organismId)((c_, f_) => c_.copy(organismId = f_))
  }
  final val POSITIONX_FIELD_NUMBER = 1
  final val POSITIONY_FIELD_NUMBER = 2
  final val ORGANISMID_FIELD_NUMBER = 3
  def of(
    positionX: _root_.scala.Float,
    positionY: _root_.scala.Float,
    organismId: _root_.scala.Long
  ): _root_.protocol.scene.SC10306 = _root_.protocol.scene.SC10306(
    positionX,
    positionY,
    organismId
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10306])
}
