package application.module.scene.operate;

import com.google.protobuf.GeneratedMessageV3;

/**
 * @author Luo Yong
 * @date 2022-4-14
 * @Source 1.0
 */
public record AoiSendMessageToClient(int protoId, GeneratedMessageV3 message, long organismId) {
}
