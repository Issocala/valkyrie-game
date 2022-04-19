// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package protocol.gm
import _root_.scalapb.internal.compat.JavaConverters._

/** 请求GM命令
  *
  * @param cmd
  *   命令
  * @param args
  *   参数列表
  */
@SerialVersionUID(0L)
final case class CS10090(
    cmd: _root_.scala.Predef.String,
    args: _root_.scala.Seq[_root_.scala.Predef.String] = _root_.scala.Seq.empty,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[CS10090] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = cmd
        __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, __value)
      };
      args.foreach { __item =>
        val __value = __item
        __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, __value)
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
        val __v = cmd
        _output__.writeString(1, __v)
      };
      args.foreach { __v =>
        val __m = __v
        _output__.writeString(2, __m)
      };
      unknownFields.writeTo(_output__)
    }
    def withCmd(__v: _root_.scala.Predef.String): CS10090 = copy(cmd = __v)
    def clearArgs = copy(args = _root_.scala.Seq.empty)
    def addArgs(__vs: _root_.scala.Predef.String*): CS10090 = addAllArgs(__vs)
    def addAllArgs(__vs: Iterable[_root_.scala.Predef.String]): CS10090 = copy(args = args ++ __vs)
    def withArgs(__v: _root_.scala.Seq[_root_.scala.Predef.String]): CS10090 = copy(args = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => cmd
        case 2 => args
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(cmd)
        case 2 => _root_.scalapb.descriptors.PRepeated(args.iterator.map(_root_.scalapb.descriptors.PString(_)).toVector)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = protocol.gm.CS10090
    // @@protoc_insertion_point(GeneratedMessage[protocol.CS10090])
}

object CS10090 extends scalapb.GeneratedMessageCompanion[protocol.gm.CS10090] with scalapb.HasBuilder[protocol.gm.CS10090] with scalapb.JavaProtoSupport[protocol.gm.CS10090, protocol.Gm.CS10090] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[protocol.gm.CS10090] with scalapb.HasBuilder[protocol.gm.CS10090] with scalapb.JavaProtoSupport[protocol.gm.CS10090, protocol.Gm.CS10090] = this
  def toJavaProto(scalaPbSource: protocol.gm.CS10090): protocol.Gm.CS10090 = {
    val javaPbOut = protocol.Gm.CS10090.newBuilder
    javaPbOut.setCmd(scalaPbSource.cmd)
    javaPbOut.addAllArgs(scalaPbSource.args.asJava)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: protocol.Gm.CS10090): protocol.gm.CS10090 = protocol.gm.CS10090(
    cmd = javaPbSource.getCmd,
    args = javaPbSource.getArgsList.asScala.iterator.map(_root_.scala.Predef.identity).toSeq
  )
  def merge(`_message__`: protocol.gm.CS10090, `_input__`: _root_.com.google.protobuf.CodedInputStream): protocol.gm.CS10090 = newBuilder(_message__).merge(_input__).result()
  implicit def messageReads: _root_.scalapb.descriptors.Reads[protocol.gm.CS10090] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      protocol.gm.CS10090(
        cmd = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).get.as[_root_.scala.Predef.String],
        args = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Seq[_root_.scala.Predef.String]]).getOrElse(_root_.scala.Seq.empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = GmProto.javaDescriptor.getMessageTypes().get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = GmProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = protocol.gm.CS10090(
    cmd = "",
    args = _root_.scala.Seq.empty
  )
  final class Builder private (
    private var __cmd: _root_.scala.Predef.String,
    private val __args: _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Predef.String],
    private var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder
  ) extends _root_.scalapb.MessageBuilder[protocol.gm.CS10090] {
    private var __requiredFields0: _root_.scala.Long = 0x1L
    def merge(`_input__`: _root_.com.google.protobuf.CodedInputStream): this.type = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __cmd = _input__.readStringRequireUtf8()
            __requiredFields0 &= 0xfffffffffffffffeL
          case 18 =>
            __args += _input__.readStringRequireUtf8()
          case tag =>
            if (_unknownFields__ == null) {
              _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
            }
            _unknownFields__.parseField(tag, _input__)
        }
      }
      this
    }
    def result(): protocol.gm.CS10090 = {
      if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
      protocol.gm.CS10090(
        cmd = __cmd,
        args = __args.result(),
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
      )
    }
  }
  object Builder extends _root_.scalapb.MessageBuilderCompanion[protocol.gm.CS10090, protocol.gm.CS10090.Builder] {
    def apply(): Builder = new Builder(
      __cmd = "",
      __args = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Predef.String],
      `_unknownFields__` = null
    )
    def apply(`_message__`: protocol.gm.CS10090): Builder = new Builder(
        __cmd = _message__.cmd,
        __args = new _root_.scala.collection.immutable.VectorBuilder[_root_.scala.Predef.String] ++= _message__.args,
        `_unknownFields__` = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
    )
  }
  def newBuilder: Builder = protocol.gm.CS10090.Builder()
  def newBuilder(`_message__`: protocol.gm.CS10090): Builder = protocol.gm.CS10090.Builder(_message__)
  implicit class CS10090Lens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, protocol.gm.CS10090]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, protocol.gm.CS10090](_l) {
    def cmd: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.cmd)((c_, f_) => c_.copy(cmd = f_))
    def args: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Seq[_root_.scala.Predef.String]] = field(_.args)((c_, f_) => c_.copy(args = f_))
  }
  final val CMD_FIELD_NUMBER = 1
  final val ARGS_FIELD_NUMBER = 2
  def of(
    cmd: _root_.scala.Predef.String,
    args: _root_.scala.Seq[_root_.scala.Predef.String]
  ): _root_.protocol.gm.CS10090 = _root_.protocol.gm.CS10090(
    cmd,
    args
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[protocol.CS10090])
}