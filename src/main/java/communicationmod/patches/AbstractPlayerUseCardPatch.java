package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="useCard",
        paramtypez={
                AbstractCard.class,
                AbstractMonster.class,
                int.class
        }
)
public class AbstractPlayerUseCardPatch {
        private static final Logger logger = LogManager.getLogger(AbstractPlayerUseCardPatch.class.getName());
        public static void Prefix(AbstractPlayer __instance, AbstractCard card, AbstractMonster monster, int energyOnUse) {
                HashMap<String, Object> action = new HashMap<>();
                action.put("card", GameStateConverter.convertCardToJson(card));
                action.put("card_index", __instance.hand.group.indexOf(card));
                if (monster != null) {
                        action.put("monster", GameStateConverter.convertMonsterToJson(monster));
                        action.put("monster_index", AbstractDungeon.getCurrRoom().monsters.monsters.indexOf(monster));
                }
                CommunicationMod.reportAction("UseCard", action);
        }
}
