package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;

import java.util.HashMap;

@SpirePatch(
        clz= MapRoomNode.class,
        method="playNodeSelectedSound"
)
public class MapRoomNodeSelectPatch {
    public static void Prefix(MapRoomNode _instance) {

        HashMap<String, Object> action = new HashMap<>();
        action.put("node", GameStateConverter.convertMapRoomNodeToJson(_instance));
        CommunicationMod.reportAction("SelectMapNode", action);
    }
}
