package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MapNodePatch {
        private static final Logger logger = LogManager.getLogger(MapNodePatch.class.getName());

        @SpirePatch(
                clz= MapRoomNode.class,
                method="addEdge",
                paramtypez={
                        MapEdge.class,
                }
        )
        public static class AddEdgePatch {
                public static void Prefix(MapRoomNode __instance, MapEdge edge) {
                        logger.info(String.format("addEdge (node %d,%d) edge: (%d, %d) -> (%d, %d)", __instance.x, __instance.y, edge.srcX, edge.srcY, edge.dstX, edge.dstY));
                }
        }

        @SpirePatch(
                clz= MapRoomNode.class,
                method="delEdge",
                paramtypez={
                        MapEdge.class,
                }
        )
        public static class DelEdgePatch {
                public static void Prefix(MapRoomNode __instance, MapEdge edge) {
                        logger.info(String.format("delEdge (node %d,%d) edge: (%d, %d) -> (%d, %d)", __instance.x, __instance.y, edge.srcX, edge.srcY, edge.dstX, edge.dstY));
                }
        }
}
