package application.module.fight.base.context;

import java.util.Map;

/**
 * @author Luo Yong
 * @date 2022-3-15
 * @Source 1.0
 */
public record TargetParameter(long targetId, Map<Short, Long> attributeMap, int targetLevel, byte OrganismType) {
}
