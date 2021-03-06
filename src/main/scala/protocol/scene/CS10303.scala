// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.scene

/** 玩家停止移动
  */
@SerialVersionUID(0L)
final case class CS10303(
    organismId: _root_.scala.Long,
    stopInfo: protocol.scene.StopInfo,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10303] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = organismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      
      {
        val __value = stopInfo
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
        val __v = organismId
        _output__.writeInt64(1, __v)
      };
      
      {
        val __v = stopInfo
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      unknownFields.writeTo(_output__)
    }
    def withOrganismId(__v: _root_.scala.Long): CS10303 = copy(organismId = __v)
    def withStopInfo(__v: protocol.scene.StopInfo): CS10303 = copy(stopInfo = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => organismId
        case 2 => stopInfo
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(organismId)
        case 2 => stopInfo.toPMessage
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.scene.CS10303
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10303])
}

object CS10303 extends scalapb.GeneratedMessageCompanion[protocol.scene.CS10303] with scalapb.HasBuilder[protocol.scene.CS10303] with scalapb.JavaProtoSupport[protocol.scene.CS10303, protocol.Scene.CS10303] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.scene.CS10303] with scalapb.HasBuilder[protocol.scene.CS10303] with scalapb.JavaProtoSupport[protocol.scene.CS10303, protocol.Scene.CS10303] = this
  def toJavaProto(scalaPbSource: protocol.scene.CS10303): protocol.Scene.CS10303 = {
    val javaPbOut = protocol.Scene.CS10303.newBuilder
    javaPbOut.setOrganismId(scalaPbSource.organismId)
    javaPbOut.setStopInfo(protocol.scene.StopInfo.toJavaProto(scalaPbSource.stopInfo))
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Scene.CS10303): protocol.scene.CS10303 = protocol.scene.CS10303(
    organismId = javaPbSource.getOrganismId.longValue,
    stopInfo = protocol.scene.StopInfo.fromJavaProto(javaPbSource.getStopInfo)
  )
  def merge(`_message__`: protocol.scene.CS10303, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.scene.CS10303 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.scene.CS10303] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.scene.CS10303(
        organismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        stopInfo = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[protocol.scene.StopInfo]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SceneProto.javaDescriptor.getMessageTypes().get(10)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SceneProto.scalaDescriptor.messages(10)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 2 => __out = protocol.scene.StopInfo
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.scene.CS10303(
    organismId = 0L,
    stopInfo = protocol.scene.StopInfo.defaultInstance
  )
  final class Builder private (
    private var __organismId: _root_.scala.Long,
    private var __stopInfo: _root_.scala.Option[protocol.scene.StopInfo],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.scene.CS10303] {
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
          case 18 =>
            __stopInfo = _root_.scala.Some(__stopInfo.fold(_root_.scalapb.LiteParser.readMessage[protocol.scene.StopInfo](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
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
    def result(): protocol.scene.CS10303 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.scene.CS10303(
        organismId = __organismId,
        stopInfo = __stopInfo.getOrElse(protocol.scene.StopInfo.defaultInstance),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.scene.CS10303, protocol.scene.CS10303.Builder] {
    def apply(): Builder = new Builder(
      __organismId = 0L,
      __stopInfo = _root_.scala.None,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.scene.CS10303): Builder = new Builder(
        __organismId = _message__.organismId,
        __stopInfo = _root_.scala.Some(_message__.stopInfo),
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.scene.CS10303.Builder()
  def newBuilder(`_message__`: protocol.scene.CS10303): Builder = protocol.scene.CS10303.Builder(_message__)
  implicit class CS10303Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.CS10303]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.scene.CS10303](_l) {
    def organismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.organismId)((c_, f_) => c_.copy(organismId = f_))
    def stopInfo: _root_.scalapb.lenses.Lens[UpperPB, protocol.scene.StopInfo] = field(_.stopInfo)((c_, f_) => c_.copy(stopInfo = f_))
  }
  final val ORGANISMID_FIELD_NUMBER = 1
  final val STOPINFO_FIELD_NUMBER = 2
  def of(
    organismId: _root_.scala.Long,
    stopInfo: protocol.scene.StopInfo
  ): _root_.protocol.scene.CS10303 = _root_.protocol.scene.CS10303(
    organismId,
    stopInfo
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10303])
}
