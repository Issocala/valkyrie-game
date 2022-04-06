// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.skill
import _root_.scalapb.internal.compat.JavaConverters._

/** 使用技能
  *
  * @param fightOrganismId
  *  技能的战斗单位id
  * @param skillId
  *  技能id
  * @param targetId
  *  目标战斗单位id
  * @param direction
  *  释放方向
  * @param skillPositionX
  *  技能横坐标
  * @param skillPositionY
  *  技能纵坐标
  * @param time
  *  释放时间戳
  */
@SerialVersionUID(0L)
final case class CS10052(
    fightOrganismId: _root_.scala.Long,
    skillId: _root_.scala.Int,
    targetId: _root_.scala.Seq[_root_.scala.Long] = _root_.scala.Seq.empty,
    direction: _root_.scala.Option[_root_.scala.Float] = _root_.scala.None,
    skillPositionX: _root_.scala.Float,
    skillPositionY: _root_.scala.Float,
    time: _root_.scala.Option[_root_.scala.Long] = _root_.scala.None,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10052] {
    private[this] def targetIdSerializedSize = {
      if (__targetIdSerializedSizeField == 0) __targetIdSerializedSizeField = {
        var __s: _root_.scala.Int = 0
        targetId.foreach(__i => __s += _root_.com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(__i))
        __s
      }
      __targetIdSerializedSizeField
    }
    @transient private[this] var __targetIdSerializedSizeField: _root_.scala.Int = 0
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = fightOrganismId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      
      {
        val __value = skillId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(2, __value)
      };
      if (targetId.nonEmpty) {
        val __localsize = targetIdSerializedSize
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__localsize) + __localsize
      }
      if (direction.isDefined) {
        val __value = direction.get
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(4, __value)
      };
      
      {
        val __value = skillPositionX
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(5, __value)
      };
      
      {
        val __value = skillPositionY
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFloatSize(6, __value)
      };
      if (time.isDefined) {
        val __value = time.get
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(7, __value)
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
        val __v = fightOrganismId
        _output__.writeInt64(1, __v)
      };
      
      {
        val __v = skillId
        _output__.writeInt32(2, __v)
      };
      if (targetId.nonEmpty) {
        _output__.writeTag(3, 2)
        _output__.writeUInt32NoTag(targetIdSerializedSize)
        targetId.foreach(_output__.writeInt64NoTag)
      };
      direction.foreach { __v =>
        val __m = __v
        _output__.writeFloat(4, __m)
      };
      
      {
        val __v = skillPositionX
        _output__.writeFloat(5, __v)
      };
      
      {
        val __v = skillPositionY
        _output__.writeFloat(6, __v)
      };
      time.foreach { __v =>
        val __m = __v
        _output__.writeInt64(7, __m)
      };
      unknownFields.writeTo(_output__)
    }
    def withFightOrganismId(__v: _root_.scala.Long): CS10052 = copy(fightOrganismId = __v)
    def withSkillId(__v: _root_.scala.Int): CS10052 = copy(skillId = __v)
    def clearTargetId = copy(targetId = _root_.scala.Seq.empty)
    def addTargetId(__vs: _root_.scala.Long*): CS10052 = addAllTargetId(__vs)
    def addAllTargetId(__vs: Iterable[_root_.scala.Long]): CS10052 = copy(targetId = targetId ++ __vs)
    def withTargetId(__v: _root_.scala.Seq[_root_.scala.Long]): CS10052 = copy(targetId = __v)
    def getDirection: _root_.scala.Float = direction.getOrElse(0.0f)
    def clearDirection: CS10052 = copy(direction = _root_.scala.None)
    def withDirection(__v: _root_.scala.Float): CS10052 = copy(direction = Option(__v))
    def withSkillPositionX(__v: _root_.scala.Float): CS10052 = copy(skillPositionX = __v)
    def withSkillPositionY(__v: _root_.scala.Float): CS10052 = copy(skillPositionY = __v)
    def getTime: _root_.scala.Long = time.getOrElse(0L)
    def clearTime: CS10052 = copy(time = _root_.scala.None)
    def withTime(__v: _root_.scala.Long): CS10052 = copy(time = Option(__v))
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => fightOrganismId
        case 2 => skillId
        case 3 => targetId
        case 4 => direction.orNull
        case 5 => skillPositionX
        case 6 => skillPositionY
        case 7 => time.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(fightOrganismId)
        case 2 => _root_.scalapb.descriptors.PInt(skillId)
        case 3 => _root_.scalapb.descriptors.PRepeated(targetId.iterator.map(_root_.scalapb.descriptors.PLong(_)).toVector)
        case 4 => direction.map(_root_.scalapb.descriptors.PFloat(_)).getOrElse(_root_.scalapb.descriptors.PEmpty)
        case 5 => _root_.scalapb.descriptors.PFloat(skillPositionX)
        case 6 => _root_.scalapb.descriptors.PFloat(skillPositionY)
        case 7 => time.map(_root_.scalapb.descriptors.PLong(_)).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.skill.CS10052
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10052])
}

object CS10052 extends scalapb.GeneratedMessageCompanion[protocol.skill.CS10052] with scalapb.HasBuilder[protocol.skill.CS10052] with scalapb.JavaProtoSupport[protocol.skill.CS10052, protocol.Skill.CS10052] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.skill.CS10052] with scalapb.HasBuilder[protocol.skill.CS10052] with scalapb.JavaProtoSupport[protocol.skill.CS10052, protocol.Skill.CS10052] = this
  def toJavaProto(scalaPbSource: protocol.skill.CS10052): protocol.Skill.CS10052 = {
    val javaPbOut = protocol.Skill.CS10052.newBuilder
    javaPbOut.setFightOrganismId(scalaPbSource.fightOrganismId)
    javaPbOut.setSkillId(scalaPbSource.skillId)
    javaPbOut.addAllTargetId(_root_.scalapb.internal.compat.toIterable(scalaPbSource.targetId.iterator.map(_root_.scala.Long.box(_))).asJava)
    scalaPbSource.direction.foreach(javaPbOut.setDirection)
    javaPbOut.setSkillPositionX(scalaPbSource.skillPositionX)
    javaPbOut.setSkillPositionY(scalaPbSource.skillPositionY)
    scalaPbSource.time.foreach(javaPbOut.setTime)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Skill.CS10052): protocol.skill.CS10052 = protocol.skill.CS10052(
    fightOrganismId = javaPbSource.getFightOrganismId.longValue,
    skillId = javaPbSource.getSkillId.intValue,
    targetId = javaPbSource.getTargetIdList.asScala.iterator.map(_.longValue).toSeq,
    direction = if (javaPbSource.hasDirection) Some(javaPbSource.getDirection.floatValue) else _root_.scala.None,
    skillPositionX = javaPbSource.getSkillPositionX.floatValue,
    skillPositionY = javaPbSource.getSkillPositionY.floatValue,
    time = if (javaPbSource.hasTime) Some(javaPbSource.getTime.longValue) else _root_.scala.None
  )
  def merge(`_message__`: protocol.skill.CS10052, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.skill.CS10052 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.skill.CS10052] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.skill.CS10052(
        fightOrganismId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        skillId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Int],
        targetId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).map(_.as[_root_.scala.Seq[_root_.scala.Long]]).getOrElse(_root_.scala.Seq.empty),
        direction = __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).flatMap(_.as[_root_.scala.Option[_root_.scala.Float]]),
        skillPositionX = __fieldsMap.get(scalaDescriptor.findFieldByNumber(5).get).get.as[_root_.scala.Float],
        skillPositionY = __fieldsMap.get(scalaDescriptor.findFieldByNumber(6).get).get.as[_root_.scala.Float],
        time = __fieldsMap.get(scalaDescriptor.findFieldByNumber(7).get).flatMap(_.as[_root_.scala.Option[_root_.scala.Long]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SkillProto.javaDescriptor.getMessageTypes().get(4)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SkillProto.scalaDescriptor.messages(4)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.skill.CS10052(
    fightOrganismId = 0L,
    skillId = 0,
    targetId = _root_.scala.Seq.empty,
    direction = _root_.scala.None,
    skillPositionX = 0.0f,
    skillPositionY = 0.0f,
    time = _root_.scala.None
  )
  final class Builder private (
    private var __fightOrganismId: _root_.scala.Long,
    private var __skillId: _root_.scala.Int,
    private val __targetId: _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Long],
    private var __direction: _root_.scala.Option[_root_.scala.Float],
    private var __skillPositionX: _root_.scala.Float,
    private var __skillPositionY: _root_.scala.Float,
    private var __time: _root_.scala.Option[_root_.scala.Long],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.skill.CS10052] {
    private var __requiredFields0: _root_.scala.Long = 0xfL
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __fightOrganismId = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 16 =>
            __skillId = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffdL
          case 24 =>
            __targetId += _input__.readInt64()
          case 26 => {
            val length = _input__.readRawVarint32()
            val oldLimit = _input__.pushLimit(length)
            while (_input__.getBytesUntilLimit > 0) {
              __targetId += _input__.readInt64()
            }
            _input__.popLimit(oldLimit)
          }
          case 37 =>
            __direction = Option(_input__.readFloat())
          case 45 =>
            __skillPositionX = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffffbL
          case 53 =>
            __skillPositionY = _input__.readFloat()
            __requiredFields0 &= 0xfffffffffffffff7L
          case 56 =>
            __time = Option(_input__.readInt64())
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.skill.CS10052 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.skill.CS10052(
        fightOrganismId = __fightOrganismId,
        skillId = __skillId,
        targetId = __targetId.result(),
        direction = __direction,
        skillPositionX = __skillPositionX,
        skillPositionY = __skillPositionY,
        time = __time,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.skill.CS10052, protocol.skill.CS10052.Builder] {
    def apply(): Builder = new Builder(
      __fightOrganismId = 0L,
      __skillId = 0,
      __targetId = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Long],
      __direction = _root_.scala.None,
      __skillPositionX = 0.0f,
      __skillPositionY = 0.0f,
      __time = _root_.scala.None,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.skill.CS10052): Builder = new Builder(
        __fightOrganismId = _message__.fightOrganismId,
        __skillId = _message__.skillId,
        __targetId = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Long] ++= _message__.targetId,
        __direction = _message__.direction,
        __skillPositionX = _message__.skillPositionX,
        __skillPositionY = _message__.skillPositionY,
        __time = _message__.time,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.skill.CS10052.Builder()
  def newBuilder(`_message__`: protocol.skill.CS10052): Builder = protocol.skill.CS10052.Builder(_message__)
  implicit class CS10052Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.skill.CS10052]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.skill.CS10052](_l) {
    def fightOrganismId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.fightOrganismId)((c_, f_) => c_.copy(fightOrganismId = f_))
    def skillId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.skillId)((c_, f_) => c_.copy(skillId = f_))
    def targetId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Seq[_root_.scala.Long]] = field(_.targetId)((c_, f_) => c_.copy(targetId = f_))
    def direction: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.getDirection)((c_, f_) => c_.copy(direction = Option(f_)))
    def optionalDirection: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[_root_.scala.Float]] = field(_.direction)((c_, f_) => c_.copy(direction = f_))
    def skillPositionX: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.skillPositionX)((c_, f_) => c_.copy(skillPositionX = f_))
    def skillPositionY: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Float] = field(_.skillPositionY)((c_, f_) => c_.copy(skillPositionY = f_))
    def time: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.getTime)((c_, f_) => c_.copy(time = Option(f_)))
    def optionalTime: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[_root_.scala.Long]] = field(_.time)((c_, f_) => c_.copy(time = f_))
  }
  final val FIGHTORGANISMID_FIELD_NUMBER = 1
  final val SKILLID_FIELD_NUMBER = 2
  final val TARGETID_FIELD_NUMBER = 3
  final val DIRECTION_FIELD_NUMBER = 4
  final val SKILLPOSITIONX_FIELD_NUMBER = 5
  final val SKILLPOSITIONY_FIELD_NUMBER = 6
  final val TIME_FIELD_NUMBER = 7
  def of(
    fightOrganismId: _root_.scala.Long,
    skillId: _root_.scala.Int,
    targetId: _root_.scala.Seq[_root_.scala.Long],
    direction: _root_.scala.Option[_root_.scala.Float],
    skillPositionX: _root_.scala.Float,
    skillPositionY: _root_.scala.Float,
    time: _root_.scala.Option[_root_.scala.Long]
  ): _root_.protocol.skill.CS10052 = _root_.protocol.skill.CS10052(
    fightOrganismId,
    skillId,
    targetId,
    direction,
    skillPositionX,
    skillPositionY,
    time
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10052])
}
