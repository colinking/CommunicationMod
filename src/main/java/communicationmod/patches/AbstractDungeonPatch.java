package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class AbstractDungeonPatch {
    private static final Logger logger = LogManager.getLogger(AbstractDungeonPatch.class.getName());

    @SpirePatch(
            clz=AbstractDungeon.class,
            method="initializeRelicList"
    )
    public static class InitializeRelicListPatch {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert(AbstractDungeon __instance) {
            // Print out various pools
            logger.info(String.format("Common relic pool: %s", AbstractDungeon.commonRelicPool));
            logger.info(String.format("Uncommon relic pool: %s", AbstractDungeon.uncommonRelicPool));
            logger.info(String.format("Rare relic pool: %s", AbstractDungeon.rareRelicPool));
            logger.info(String.format("Shop relic pool: %s", AbstractDungeon.shopRelicPool));
            logger.info(String.format("Boss relic pool: %s", AbstractDungeon.bossRelicPool));
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(Collections.class, "shuffle");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
            }
        }
    }
}
