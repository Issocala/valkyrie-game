// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.skill

/** @param targetId
  *  目标战斗者
  * @param damageType
  *  位运算计算伤害类型汇总(闪避,闪避等)
  * @param damage
  *  伤害数值
  */
@SerialVersionUID(0L)
final case class DamageData(
    targetId: _root_.scala.Long,
    damageType: _root_.scala.Int,
    damage: _root_.scala.Long,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[DamageData] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = targetId
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(1, __value)
      };
      
      {
        val __value = damageType
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(2, __value)
      };
      
      {
        val __value = damage
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
        val __v = targetId
        _output__.writeInt64(1, __v)
      };
      
      {
        val __v = damageType
        _output__.writeInt32(2, __v)
      };
      
      {
        val __v = damage
        _output__.writeInt64(3, __v)
      };
      unknownFields.writeTo(_output__)
    }
    def withTargetId(__v: _root_.scala.Long): DamageData = copy(targetId = __v)
    def withDamageType(__v: _root_.scala.Int): DamageData = copy(damageType = __v)
    def withDamage(__v: _root_.scala.Long): DamageData = copy(damage = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => targetId
        case 2 => damageType
        case 3 => damage
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PLong(targetId)
        case 2 => _root_.scalapb.descriptors.PInt(damageType)
        case 3 => _root_.scalapb.descriptors.PLong(damage)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.skill.DamageData
    // @@protoc_insertion_point(GeneratedMessage[protocol.DamageData])
}

object DamageData extends scalapb.GeneratedMessageCompanion[protocol.skill.DamageData] with scalapb.HasBuilder[protocol.skill.DamageData] with scalapb.JavaProtoSupport[protocol.skill.DamageData, protocol.Skill.DamageData] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.skill.DamageData] with scalapb.HasBuilder[protocol.skill.DamageData] with scalapb.JavaProtoSupport[protocol.skill.DamageData, protocol.Skill.DamageData] = this
  def toJavaProto(scalaPbSource: protocol.skill.DamageData): protocol.Skill.DamageData = {
    val javaPbOut = protocol.Skill.DamageData.newBuilder
    javaPbOut.setTargetId(scalaPbSource.targetId)
    javaPbOut.setDamageType(scalaPbSource.damageType)
    javaPbOut.setDamage(scalaPbSource.damage)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Skill.DamageData): protocol.skill.DamageData = protocol.skill.DamageData(
    targetId = javaPbSource.getTargetId.longValue,
    damageType = javaPbSource.getDamageType.intValue,
    damage = javaPbSource.getDamage.longValue
  )
  def merge(`_message__`: protocol.skill.DamageData, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.skill.DamageData = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.skill.DamageData] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.skill.DamageData(
        targetId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Long],
        damageType = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).get.as[_root_.scala.Int],
        damage = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).get.as[_root_.scala.Long]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SkillProto.javaDescriptor.getMessageTypes().get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SkillProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.skill.DamageData(
    targetId = 0L,
    damageType = 0,
    damage = 0L
  )
  final class Builder private (
    private var __targetId: _root_.scala.Long,
    private var __damageType: _root_.scala.Int,
    private var __damage: _root_.scala.Long,
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.skill.DamageData] {
    private var __requiredFields0: _root_.scala.Long = 0x7L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 8 =>
            __targetId = _input__.readInt64()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 16 =>
            __damageType = _input__.readInt32()
            __requiredFields0 &= 0xfffffffffffffffdL
          case 24 =>
            __damage = _input__.readInt64()
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
    def result(): protocol.skill.DamageData = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.skill.DamageData(
        targetId = __targetId,
        damageType = __damageType,
        damage = __damage,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.skill.DamageData, protocol.skill.DamageData.Builder] {
    def apply(): Builder = new Builder(
      __targetId = 0L,
      __damageType = 0,
      __damage = 0L,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.skill.DamageData): Builder = new Builder(
        __targetId = _message__.targetId,
        __damageType = _message__.damageType,
        __damage = _message__.damage,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.skill.DamageData.Builder()
  def newBuilder(`_message__`: protocol.skill.DamageData): Builder = protocol.skill.DamageData.Builder(_message__)
  implicit class DamageDataLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.skill.DamageData]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.skill.DamageData](_l) {
    def targetId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.targetId)((c_, f_) => c_.copy(targetId = f_))
    def damageType: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.damageType)((c_, f_) => c_.copy(damageType = f_))
    def damage: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.damage)((c_, f_) => c_.copy(damage = f_))
  }
  final val TARGETID_FIELD_NUMBER = 1
  final val DAMAGETYPE_FIELD_NUMBER = 2
  final val DAMAGE_FIELD_NUMBER = 3
  def of(
    targetId: _root_.scala.Long,
    damageType: _root_.scala.Int,
    damage: _root_.scala.Long
  ): _root_.protocol.skill.DamageData = _root_.protocol.skill.DamageData(
    targetId,
    damageType,
    damage
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.DamageData])
}