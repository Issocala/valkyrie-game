// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.skill

@SerialVersionUID(0L)
final case class SC10051(
    skillId: _root_.scala.Int,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10051] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = skillId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(1, __value)
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
        val __v = skillId
        _output__.writeInt32(1, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withSkillId(__v: _root_.scala.Int): SC10051 = copy(skillId = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => skillId
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PInt(skillId)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.skill.SC10051
    // @@protoc_insertion_point(GeneratedMessage[protocol.SC10051])
}

object SC10051 extends scalapb.GeneratedMessageCompanion[protocol.skill.SC10051] with scalapb.HasBuilder[protocol.skill.SC10051] with scalapb.JavaProtoSupport[protocol.skill.SC10051, protocol.Skill.SC10051] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.skill.SC10051] with scalapb.HasBuilder[protocol.skill.SC10051] with scalapb.JavaProtoSupport[protocol.skill.SC10051, protocol.Skill.SC10051] = this
  def toJavaProto(scalaPbSource: protocol.skill.SC10051): protocol.Skill.SC10051 = {
    val javaPbOut = protocol.Skill.SC10051.newBuilder
    javaPbOut.setSkillId(scalaPbSource.skillId)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Skill.SC10051): protocol.skill.SC10051 = protocol.skill.SC10051(
    skillId = javaPbSource.getSkillId.intValue
  )
  def merge(`_message__`: protocol.skill.SC10051, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.skill.SC10051 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.skill.SC10051] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.skill.SC10051(
        skillId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Int]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SkillProto.javaDescriptor.getMessageTypes().get(4)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SkillProto.scalaDescriptor.messages(4)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.skill.SC10051(
    skillId = 0
  )
  final class Builder private (
    private var __skillId: _root_.scala.Int,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.skill.SC10051] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __skillId = _input__.readInt32()
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
    def result(): protocol.skill.SC10051 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.skill.SC10051(
        skillId = __skillId,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.skill.SC10051, protocol.skill.SC10051.Builder] {
    def apply(): Builder = new Builder(
      __skillId = 0,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.skill.SC10051): Builder = new Builder(
        __skillId = _message__.skillId,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.skill.SC10051.Builder()
  def newBuilder(`_message__`: protocol.skill.SC10051): Builder = protocol.skill.SC10051.Builder(_message__)
  implicit class SC10051Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.skill.SC10051]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.skill.SC10051](_l) {
    def skillId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.skillId)((c_, f_) => c_.copy(skillId = f_))
  }
  final val SKILLID_FIELD_NUMBER = 1
  def of(
    skillId: _root_.scala.Int
  ): _root_.protocol.skill.SC10051 = _root_.protocol.skill.SC10051(
    skillId
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10051])
}
