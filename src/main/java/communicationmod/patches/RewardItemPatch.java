package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;

import java.util.HashMap;

@SpirePatch(
        clz= RewardItem.class,
        method="claimReward"
)
public class RewardItemPatch {
    public static void Prefix(RewardItem __instance) {
        HashMap<String, Object> action = new HashMap<>();
        action.put("reward", GameStateConverter.convertRewardToJson(__instance));
        CommunicationMod.reportAction("SelectReward", action);
    }
}
