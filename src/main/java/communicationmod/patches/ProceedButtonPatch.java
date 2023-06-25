package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(
        clz= ProceedButton.class,
        method="update"
)
public class ProceedButtonPatch {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert(ProceedButton _instance) {
                CommunicationMod.reportAction("Proceed");
        }

        private static class Locator extends SpireInsertLocator {
                public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                        Matcher matcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCurrRoom");
                        return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
                }
        }
}
