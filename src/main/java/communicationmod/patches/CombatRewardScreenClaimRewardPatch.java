package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@SpirePatch(
        clz= CombatRewardScreen.class,
        method="rewardViewUpdate"
)
public class CombatRewardScreenClaimRewardPatch {


    @SpireInsertPatch(
            locator=Locator.class,
            localvars = {"r"}
    )
    public static void Insert(CombatRewardScreen _instance, RewardItem r) {
        HashMap<String, Object> action = new HashMap<>();
        action.put("reward", GameStateConverter.convertRewardToJson(r));
        CommunicationMod.reportAction("SelectReward", action);
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(Iterator.class, "remove");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
        }
    }
}
