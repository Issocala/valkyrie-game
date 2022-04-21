package application.module.scene.operate.event;

import application.module.organism.Organism;
import com.cala.orm.message.DataBase;
import com.cala.orm.message.Event;

import java.util.List;

/**
 * @author Luo Yong
 * @date 2022-4-13
 * @Source 1.0
 */
public record CreateOrganismEntityAfter(List<Organism> organisms) implements DataBase, Event {
}
