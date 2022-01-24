// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.user

/** 登录
  */
@SerialVersionUID(0L)
final case class CS10011(
    account: protocol.base.AccountInfo,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10011] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = account
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
        val __v = account
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      unknownFields.writeTo(_output__)
    }
    def withAccount(__v: protocol.base.AccountInfo): CS10011 = copy(account = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => account
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => account.toPMessage
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.user.CS10011
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10011])
}

object CS10011 extends scalapb.GeneratedMessageCompanion[protocol.user.CS10011] with scalapb.HasBuilder[protocol.user.CS10011] with scalapb.JavaProtoSupport[protocol.user.CS10011, protocol.User.CS10011] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.user.CS10011] with scalapb.HasBuilder[protocol.user.CS10011] with scalapb.JavaProtoSupport[protocol.user.CS10011, protocol.User.CS10011] = this
  def toJavaProto(scalaPbSource: protocol.user.CS10011): protocol.User.CS10011 = {
    val javaPbOut = protocol.User.CS10011.newBuilder
    javaPbOut.setAccount(protocol.base.AccountInfo.toJavaProto(scalaPbSource.account))
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.User.CS10011): protocol.user.CS10011 = protocol.user.CS10011(
    account = protocol.base.AccountInfo.fromJavaProto(javaPbSource.getAccount)
  )
  def merge(`_message__`: protocol.user.CS10011, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.user.CS10011 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.user.CS10011] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.user.CS10011(
        account = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[protocol.base.AccountInfo]
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = UserProto.javaDescriptor.getMessageTypes().get(2)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = UserProto.scalaDescriptor.messages(2)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = protocol.base.AccountInfo
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.user.CS10011(
    account = protocol.base.AccountInfo.defaultInstance
  )
  final class Builder private (
    private var __account: _root_.scala.Option[protocol.base.AccountInfo],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.user.CS10011] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __account = _root_.scala.Some(__account.fold(_root_.scalapb.LiteParser.readMessage[protocol.base.AccountInfo](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
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
    def result(): protocol.user.CS10011 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.user.CS10011(
        account = __account.getOrElse(protocol.base.AccountInfo.defaultInstance),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.user.CS10011, protocol.user.CS10011.Builder] {
    def apply(): Builder = new Builder(
      __account = _root_.scala.None,
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.user.CS10011): Builder = new Builder(
        __account = _root_.scala.Some(_message__.account),
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.user.CS10011.Builder()
  def newBuilder(`_message__`: protocol.user.CS10011): Builder = protocol.user.CS10011.Builder(_message__)
  implicit class CS10011Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.user.CS10011]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.user.CS10011](_l) {
    def account: _root_.scalapb.lenses.Lens[UpperPB, protocol.base.AccountInfo] = field(_.account)((c_, f_) => c_.copy(account = f_))
  }
  final val ACCOUNT_FIELD_NUMBER = 1
  def of(
    account: protocol.base.AccountInfo
  ): _root_.protocol.user.CS10011 = _root_.protocol.user.CS10011(
    account
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10011])
}