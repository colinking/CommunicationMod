package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;

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
        public static void Prefix(AbstractPlayer __instance, AbstractCard card, AbstractMonster monster, int energyOnUse) {
                HashMap<String, Object> action = new HashMap<>();
                action.put("card", GameStateConverter.convertCardToJson(card));
                if (monster != null) {
                        action.put("monster", GameStateConverter.convertMonsterToJson(monster));
                }
                CommunicationMod.reportAction("UseCard", action);
        }
}
