package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz= CardGroup.class,
        method="getRandomCard",
        paramtypez={
                boolean.class,
        }
)
public class CardGroupGetRandomCardPatch {
        private static final Logger logger = LogManager.getLogger(CardGroupGetRandomCardPatch.class.getName());

        public static void Prefix(CardGroup __instance, boolean useRng) {
                logger.info(String.format("Getting random card: size=%d", __instance.group.size()));
                for (AbstractCard card : __instance.group) {
                        logger.info(String.format("Card group: %s", card.name));
                }
        }
}
