package communicationmod.patches;

import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CombatRewardScreenPatch {

    @SpirePatch(
            clz= CombatRewardScreen.class,
            method="rewardViewUpdate"
    )
    public static class RewardUpdatePatch {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static void Insert(CombatRewardScreen _instance) {
            // This will deal with linked relics / keys
            for(RewardItem reward : _instance.rewards) {
                if (reward.isDone) {
                    return;
                }
            }
            GameStateListener.registerStateChange();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(CombatRewardScreen.class, "setLabel");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
            }
        }
    }

    @SpirePatch(
            clz= CombatRewardScreen.class,
            method="rewardViewUpdate"
    )
    public static class ClaimRewardPatch {


        @SpireInsertPatch(
                locator= Locator.class,
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

}
