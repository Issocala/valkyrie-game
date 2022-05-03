// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.state

@SerialVersionUID(0L)
final case class SC10062(
    organismId: _root_.scala.Long,
    stateType: _root_.scala.Int,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10062] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = organismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      
      {
        val __value = stateType
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(2, __value)
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
        val __v = stateType
        _output__.writeInt32(2, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withOrganismId(__v: _root_.scala.Long): SC10062 = copy(organismId = __v)
    def withStateType(__v: _root_.scala.Int): SC10062 = copy(stateType = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => organismId
        case 2 => stateType
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(organismId)
        case 2 => _root_.scalapb.descriptors.PInt(stateType)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.state.SC10062
    // @@protoc_insertion_point(GeneratedMessage[protocol.SC10062])
}

object SC10062 extends scalapb.GeneratedMessageCompanion[protocol.state.SC10062] with scalapb.HasBuilder[protocol.state.SC10062] with scalapb.JavaProtoSupport[protocol.state.SC10062, protocol.State.SC10062] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.state.SC10062] with scalapb.HasBuilder[protocol.state.SC10062] with scalapb.JavaProtoSupport[protocol.state.SC10062, protocol.State.SC10062] = this
  def toJavaProto(scalaPbSource: protocol.state.SC10062): protocol.State.SC10062 = {
    val javaPbOut = protocol.State.SC10062.newBuilder
    javaPbOut.setOrganismId(scalaPbSource.organismId)
    javaPbOut.setStateType(scalaPbSource.stateType)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.State.SC10062): protocol.state.SC10062 = protocol.state.SC10062(
    organismId = javaPbSource.getOrganismId.longValue,
    stateType = javaPbSource.getStateType.intValue
  )
  def merge(`_message__`: protocol.state.SC10062, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.state.SC10062 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.state.SC10062] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.state.SC10062(
        organismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        stateType = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Int]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = StateProto.javaDescriptor.getMessageTypes().get(2)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = StateProto.scalaDescriptor.messages(2)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.state.SC10062(
    organismId = 0L,
    stateType = 0
  )
  final class Builder private (
    private var __organismId: _root_.scala.Long,
    private var __stateType: _root_.scala.Int,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.state.SC10062] {
    private var __requiredFields0: _root_.scala.Long = 0x3L
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
            __stateType = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffdL
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.state.SC10062 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.state.SC10062(
        organismId = __organismId,
        stateType = __stateType,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.state.SC10062, protocol.state.SC10062.Builder] {
    def apply(): Builder = new Builder(
      __organismId = 0L,
      __stateType = 0,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.state.SC10062): Builder = new Builder(
        __organismId = _message__.organismId,
        __stateType = _message__.stateType,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.state.SC10062.Builder()
  def newBuilder(`_message__`: protocol.state.SC10062): Builder = protocol.state.SC10062.Builder(_message__)
  implicit class SC10062Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.state.SC10062]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.state.SC10062](_l) {
    def organismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.organismId)((c_, f_) => c_.copy(organismId = f_))
    def stateType: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.stateType)((c_, f_) => c_.copy(stateType = f_))
  }
  final val ORGANISMID_FIELD_NUMBER = 1
  final val STATETYPE_FIELD_NUMBER = 2
  def of(
    organismId: _root_.scala.Long,
    stateType: _root_.scala.Int
  ): _root_.protocol.state.SC10062 = _root_.protocol.state.SC10062(
    organismId,
    stateType
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10062])
}