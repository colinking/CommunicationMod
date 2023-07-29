package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class MapPatch {
        private static final Logger logger = LogManager.getLogger(MapPatch.class.getName());

        @SpirePatch(
                clz= MapGenerator.class,
                method="_createPaths",
                paramtypez={
                        ArrayList.class,
                        MapEdge.class,
                        Random.class
                }
        )
        private static class CreatePathsPatch {
                public static void Prefix(ArrayList nodes, MapEdge edge, Random rng) {
                        logger.info(String.format("_createPaths edge: (%d, %d) -> (%d, %d)", edge.srcX, edge.srcY, edge.dstX, edge.dstY));
                }
        }

        @SpirePatch(
                clz= MapGenerator.class,
                method="getCommonAncestor"
        )
        private static class GetAncestorPatch {
//                public static void Prefix(MapRoomNode node1, MapRoomNode node2, int max_depth) {
//                        logger.info(String.format("getCommonAncestor((%d, %d), (%d, %d), %d)", node1.x, node1.y, node2.x, node2.y, max_depth));
//                }

                public static MapRoomNode Postfix(MapRoomNode __result, MapRoomNode node1, MapRoomNode node2, int max_depth) {
                        String result = "null";
                        if (__result != null) {
                                result = String.format("(%d, %d)", __result.x, __result.y);
                        }
                        logger.info(String.format("getCommonAncestor((%d, %d), (%d, %d), %d) -> %s", node1.x, node1.y, node2.x, node2.y, max_depth, result));
                        return __result;
                }
        }

        @SpirePatch(
                clz= MapGenerator.class,
                method="randRange"
        )
        private static class RandRangePatch {
                public static int Postfix(int __result, Random rng, int min, int max) {
                        logger.info(String.format("randRange(%d, %d) -> %d", min, max, __result));
                        return __result;
                }
        }
}
