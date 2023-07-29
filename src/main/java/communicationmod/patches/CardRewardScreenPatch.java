package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class CardRewardScreenPatch {

    public static boolean doHover = false;
    public static AbstractCard hoverCard;

    @SpirePatch(
            clz=CardRewardScreen.class,
            method = "cardSelectUpdate"
    )
    public static class HoverCardPatch {

        @SpireInsertPatch(
                locator=Locator.class,
                localvars = {"c"}
        )
        public static void Insert(CardRewardScreen _instance, AbstractCard c) {
            if(doHover) {
                if(c.equals(hoverCard)) {
                    hoverCard.hb.hovered = true;
                } else {
                    c.hb.hovered = false;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "updateHoverLogic");
                int[] match = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
                match[0] += 1;
                return match;
            }
        }

    }

    @SpirePatch(
            clz=CardRewardScreen.class,
            method = "cardSelectUpdate"
    )
    public static class AcquireCardPatch {

        @SpireInsertPatch(
                locator=Locator.class,
                localvars = {"hoveredCard"}
        )
        public static void Insert(CardRewardScreen _instance, AbstractCard hoveredCard) {
            doHover = false;

            HashMap<String, Object> action = new HashMap<>();
            action.put("card", GameStateConverter.convertCardToJson(hoveredCard));
            int cardIndex = -1;
            for (int i = 0; i < _instance.rewardGroup.size(); i++) {
                if (_instance.rewardGroup.get(i).uuid.equals(hoveredCard.uuid)) {
                    cardIndex = i;
                    break;
                }
            }
            action.put("card_index", cardIndex);
            CommunicationMod.reportAction("SelectCard", action);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(CardRewardScreen.class, "skipButton");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
            }
        }

    }
}
