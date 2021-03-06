// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.skill
import _root_.scalapb.internal.compat.JavaConverters._

/** 返回实体全部可用技能
  */
@SerialVersionUID(0L)
final case class SC10050(
    organismId: _root_.scala.Long,
    skillId: _root_.scala.Seq[_root_.scala.Int] = _root_.scala.Seq.empty,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[SC10050] {
    private[this] def skillIdSerializedSize = {
      if (__skillIdSerializedSizeField == 0) __skillIdSerializedSizeField = {
        var __s: _root_.scala.Int = 0
        skillId.foreach(__i => __s += _root_.com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(__i))
        __s
      }
      __skillIdSerializedSizeField
    }
    @transient private[this] var __skillIdSerializedSizeField: _root_.scala.Int = 0
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = organismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      if (skillId.nonEmpty) {
        val __localsize = skillIdSerializedSize
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__localsize) + __localsize
      }
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
      if (skillId.nonEmpty) {
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(skillIdSerializedSize)
        skillId.foreach(_output__.writeInt32NoTag)
      };
      unknownFields.writeTo(_output__)
    }
    def withOrganismId(__v: _root_.scala.Long): SC10050 = copy(organismId = __v)
    def clearSkillId = copy(skillId = _root_.scala.Seq.empty)
    def addSkillId(__vs: _root_.scala.Int*): SC10050 = addAllSkillId(__vs)
    def addAllSkillId(__vs: Iterable[_root_.scala.Int]): SC10050 = copy(skillId = skillId ++ __vs)
    def withSkillId(__v: _root_.scala.Seq[_root_.scala.Int]): SC10050 = copy(skillId = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => organismId
        case 2 => skillId
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(organismId)
        case 2 => _root_.scalapb.descriptors.PRepeated(skillId.iterator.map(_root_.scalapb.descriptors.PInt(_)).toVector)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.skill.SC10050
    // @@protoc_insertion_point(GeneratedMessage[protocol.SC10050])
}

object SC10050 extends scalapb.GeneratedMessageCompanion[protocol.skill.SC10050] with scalapb.HasBuilder[protocol.skill.SC10050] with scalapb.JavaProtoSupport[protocol.skill.SC10050, protocol.Skill.SC10050] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.skill.SC10050] with scalapb.HasBuilder[protocol.skill.SC10050] with scalapb.JavaProtoSupport[protocol.skill.SC10050, protocol.Skill.SC10050] = this
  def toJavaProto(scalaPbSource: protocol.skill.SC10050): protocol.Skill.SC10050 = {
    val javaPbOut = protocol.Skill.SC10050.newBuilder
    javaPbOut.setOrganismId(scalaPbSource.organismId)
    javaPbOut.addAllSkillId(_root_.scalapb.internal.compat.toIterable(scalaPbSource.skillId.iterator.map(_root_.scala.Int.box(_))).asJava)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Skill.SC10050): protocol.skill.SC10050 = protocol.skill.SC10050(
    organismId = javaPbSource.getOrganismId.longValue,
    skillId = javaPbSource.getSkillIdList.asScala.iterator.map(_.intValue).toSeq
  )
  def merge(`_message__`: protocol.skill.SC10050, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.skill.SC10050 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.skill.SC10050] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.skill.SC10050(
        organismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        skillId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Seq[_root_.scala.Int]]).getOrElse(_root_.scala.Seq.empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SkillProto.javaDescriptor.getMessageTypes().get(2)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SkillProto.scalaDescriptor.messages(2)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.skill.SC10050(
    organismId = 0L,
    skillId = _root_.scala.Seq.empty
  )
  final class Builder private (
    private var __organismId: _root_.scala.Long,
    private val __skillId: _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Int],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.skill.SC10050] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
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
            __skillId += _input__.readInt32()
          case 18 => {
            val length = _input__.readRawVarint32()
            val oldLimit = _input__.pushLimit(length)
            while (_input__.getBytesUntilLimit > 0) {
              __skillId += _input__.readInt32()
            }
            _input__.popLimit(oldLimit)
          }
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.skill.SC10050 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.skill.SC10050(
        organismId = __organismId,
        skillId = __skillId.result(),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.skill.SC10050, protocol.skill.SC10050.Builder] {
    def apply(): Builder = new Builder(
      __organismId = 0L,
      __skillId = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Int],
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.skill.SC10050): Builder = new Builder(
        __organismId = _message__.organismId,
        __skillId = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Int] ++= _message__.skillId,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.skill.SC10050.Builder()
  def newBuilder(`_message__`: protocol.skill.SC10050): Builder = protocol.skill.SC10050.Builder(_message__)
  implicit class SC10050Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.skill.SC10050]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.skill.SC10050](_l) {
    def organismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.organismId)((c_, f_) => c_.copy(organismId = f_))
    def skillId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Seq[_root_.scala.Int]] = field(_.skillId)((c_, f_) => c_.copy(skillId = f_))
  }
  final val ORGANISMID_FIELD_NUMBER = 1
  final val SKILLID_FIELD_NUMBER = 2
  def of(
    organismId: _root_.scala.Long,
    skillId: _root_.scala.Seq[_root_.scala.Int]
  ): _root_.protocol.skill.SC10050 = _root_.protocol.skill.SC10050(
    organismId,
    skillId
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.SC10050])
}
