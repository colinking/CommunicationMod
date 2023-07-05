package communicationmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class PotionPopUpPatch {
        private static final Logger logger = LogManager.getLogger(PotionPopUpPatch.class.getName());

        @SpirePatch(
                clz= PotionPopUp.class,
                method="updateInput"
        )
        public static class PotionPopUpUpdateInputPatch {
                @SpireInsertPatch(
                        locator= PotionPopUpUpdateInputPatch.Locator.class
                )
                public static void Insert(PotionPopUp __instance) {
                        HashMap<String, Object> action = new HashMap<>();
                        AbstractPotion potion = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "potion");
                        action.put("potion", GameStateConverter.convertPotionToJson(potion));
                        action.put("potion_index", AbstractDungeon.player.potions.indexOf(potion));
                        CommunicationMod.reportAction("UsePotion", action);
                }

                private static class Locator extends SpireInsertLocator {
                        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "use");
                                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
                        }
                }
        }

        @SpirePatch(
                clz= PotionPopUp.class,
                method="updateTargetMode"
        )
        public static class PotionPopUpUpdateTargetModePatch {
                @SpireInsertPatch(
                        locator= PotionPopUpUpdateTargetModePatch.Locator.class
                )
                public static void Insert(PotionPopUp __instance) {
                        HashMap<String, Object> action = new HashMap<>();
                        AbstractPotion potion = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "potion");
                        action.put("potion", GameStateConverter.convertPotionToJson(potion));
                        action.put("potion_index", AbstractDungeon.player.potions.indexOf(potion));
                        AbstractMonster monster = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "hoveredMonster");
                        action.put("monster", GameStateConverter.convertMonsterToJson(monster));
                        action.put("monster_index", AbstractDungeon.getCurrRoom().monsters.monsters.indexOf(monster));
                        CommunicationMod.reportAction("UsePotion", action);
                }

                private static class Locator extends SpireInsertLocator {
                        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "use");
                                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
                        }
                }
        }
}
