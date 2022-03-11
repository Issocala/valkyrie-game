package application.module.player.operate.info;

import application.module.player.data.domain.PlayerEntity;
import application.util.CommonOperateTypeInfo;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-3-11
 * @Source 1.0
 */
public record PlayerGetManyInfo(CommonOperateTypeInfo commonOperateTypeInfo, List<PlayerEntity> playerEntityList) {
}
