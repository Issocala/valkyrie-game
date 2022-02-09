// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.battle

@SerialVersionUID(0L)
final case class C2S_ChangeInputDir(
    nowInput: _root_.scala.Boolean,
    nowInputDir: _root_.scala.Float,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[C2S_ChangeInputDir] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = nowInput
        __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(1, __value)
      };
      
      {
        val __value = nowInputDir
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(2, __value)
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
        val __v = nowInput
        _output__.writeBool(1, __v)
      };
      
      {
        val __v = nowInputDir
        _output__.writeFloat(2, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withNowInput(__v: _root_.scala.Boolean): C2S_ChangeInputDir = copy(nowInput = __v)
    def withNowInputDir(__v: _root_.scala.Float): C2S_ChangeInputDir = copy(nowInputDir = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => nowInput
        case 2 => nowInputDir
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PBoolean(nowInput)
        case 2 => _root_.scalapb.descriptors.PFloat(nowInputDir)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.battle.C2S_ChangeInputDir
    // @@protoc_insertion_point(GeneratedMessage[protocol.C2S_ChangeInputDir])
}

object C2S_ChangeInputDir extends scalapb.GeneratedMessageCompanion[protocol.battle.C2S_ChangeInputDir] with scalapb.HasBuilder[protocol.battle.C2S_ChangeInputDir] with scalapb.JavaProtoSupport[protocol.battle.C2S_ChangeInputDir, protocol.Battle.C2S_ChangeInputDir] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.battle.C2S_ChangeInputDir] with scalapb.HasBuilder[protocol.battle.C2S_ChangeInputDir] with scalapb.JavaProtoSupport[protocol.battle.C2S_ChangeInputDir, protocol.Battle.C2S_ChangeInputDir] = this
  def toJavaProto(scalaPbSource: protocol.battle.C2S_ChangeInputDir): protocol.Battle.C2S_ChangeInputDir = {
    val javaPbOut = protocol.Battle.C2S_ChangeInputDir.newBuilder
    javaPbOut.setNowInput(scalaPbSource.nowInput)
    javaPbOut.setNowInputDir(scalaPbSource.nowInputDir)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Battle.C2S_ChangeInputDir): protocol.battle.C2S_ChangeInputDir = protocol.battle.C2S_ChangeInputDir(
    nowInput = javaPbSource.getNowInput.booleanValue,
    nowInputDir = javaPbSource.getNowInputDir.floatValue
  )

  def merge(`_message__`: protocol.battle.C2S_ChangeInputDir, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.battle.C2S_ChangeInputDir = newBuilder(_message__).merge(_input__).result()

  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.battle.C2S_ChangeInputDir] = _root_.scalapb.descriptors.Reads {
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.battle.C2S_ChangeInputDir(
        nowInput = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Boolean],
        nowInputDir = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Float]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = BattleProto.javaDescriptor.getMessageTypes().get(11)

  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = BattleProto.scalaDescriptor.messages(11)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)

  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)

  lazy val defaultInstance = protocol.battle.C2S_ChangeInputDir(
    nowInput = false,
    nowInputDir = 0.0f
  )

  final class Builder private(
                               private var __nowInput: _root_.scala.Boolean,
                               private var __nowInputDir: _root_.scala.Float,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.battle.C2S_ChangeInputDir] {
    private var __requiredFields0: _root_.scala.Long = 0x3L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __nowInput = _input__.readBool()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 21 =>
            __nowInputDir = _input__.readFloat()
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
    def result(): protocol.battle.C2S_ChangeInputDir = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.battle.C2S_ChangeInputDir(
        nowInput = __nowInput,
        nowInputDir = __nowInputDir,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.battle.C2S_ChangeInputDir, protocol.battle.C2S_ChangeInputDir.Builder] {
    def apply(): Builder = new Builder(
      __nowInput = false,
      __nowInputDir = 0.0f,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.battle.C2S_ChangeInputDir): Builder = new Builder(
        __nowInput = _message__.nowInput,
        __nowInputDir = _message__.nowInputDir,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.battle.C2S_ChangeInputDir.Builder()
  def newBuilder(`_message__`: protocol.battle.C2S_ChangeInputDir): Builder = protocol.battle.C2S_ChangeInputDir.Builder(_message__)
  implicit class C2S_ChangeInputDirLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.battle.C2S_ChangeInputDir]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.battle.C2S_ChangeInputDir](_l) {
    def nowInput: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.nowInput)((c_, f_) => c_.copy(nowInput = f_))
    def nowInputDir: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.nowInputDir)((c_, f_) => c_.copy(nowInputDir = f_))
  }
  final val NOWINPUT_FIELD_NUMBER = 1
  final val NOWINPUTDIR_FIELD_NUMBER = 2
  def of(
    nowInput: _root_.scala.Boolean,
    nowInputDir: _root_.scala.Float
  ): _root_.protocol.battle.C2S_ChangeInputDir = _root_.protocol.battle.C2S_ChangeInputDir(
    nowInput,
    nowInputDir
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.C2S_ChangeInputDir])
}
