package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardGroupGetRandomCardPatch {
        private static final Logger logger = LogManager.getLogger(CardGroupGetRandomCardPatch.class.getName());

        @SpirePatch(
                clz= CardGroup.class,
                method="getRandomCard",
                paramtypez={
                        boolean.class,
                }
        )
        public static class GetRandomCardPatch {
                public static void Prefix(CardGroup __instance, boolean useRng) {
                        logger.info(String.format("Getting random card: size=%d", __instance.group.size()));
                        for (AbstractCard card : __instance.group) {
                                logger.info(String.format("Card group: %s", card.name));
                        }
                }
        }

        @SpirePatch(
                clz= CardGroup.class,
                method="getRandomCard",
                paramtypez={
                        Random.class,
                }
        )
        public static class GetRandomCardRNGPatch {
                public static void Prefix(CardGroup __instance, Random rng) {
                        logger.info(String.format("Getting random card with RNG: size=%d", __instance.group.size()));
                        for (AbstractCard card : __instance.group) {
                                logger.info(String.format("Card group: %s", card.name));
                        }
                }
        }
}
