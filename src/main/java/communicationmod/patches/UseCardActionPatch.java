package communicationmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import communicationmod.CommunicationMod;
import communicationmod.EndOfTurnAction;
import communicationmod.GameStateConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class UseCardActionPatch {
        private static final Logger logger = LogManager.getLogger(UseCardActionPatch.class.getName());


        @SpirePatch(
                clz= UseCardAction.class,
                method="update"
        )
        public static class UseCardActionUpdatePatch {
//                public static void Prefix(UseCardAction __instance) {
//                        float duration = ReflectionHacks.getPrivate(__instance, AbstractGameAction.class, "duration");
//                        if (duration != 0.15F) {
//                                logger.info("UseCardActionUpdatePatch: prefix skipped for duration");
//                                return;
//                        }
//                        logger.info("UseCardActionUpdatePatch: prefix");
//                        HashMap<String, Object> action = new HashMap<>();
//                        AbstractCard card = ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");
//                        action.put("card", GameStateConverter.convertCardToJson(card));
//                        int cardIndex = -1;
//                        CardGroup hand = AbstractDungeon.player.hand;
//                        for (int i = 0; i < hand.size(); i++) {
//                                logger.info(String.format("hand: %s ==? %s", hand.group.get(i).uuid, card.uuid));
//                                if (hand.group.get(i).uuid.equals(card.uuid)) {
//                                        cardIndex = i;
//                                        break;
//                                }
//                        }
//                        action.put("card_index", cardIndex);
//                        if (__instance.target != null && __instance.target instanceof AbstractMonster) {
//                                action.put("monster", GameStateConverter.convertMonsterToJson((AbstractMonster) __instance.target));
//                                action.put("monster_index", AbstractDungeon.getCurrRoom().monsters.monsters.indexOf((AbstractMonster) __instance.target));
//                        }
//                        CommunicationMod.reportAction("UseCard", action);
//                }

                public static void Postfix(UseCardAction __instance) {
                        if (__instance.isDone) {
                                logger.info("UseCardActionUpdatePatch: postfix");
                                CommunicationMod.publishOnGameStateChange();
                        } else {
                                logger.info("UseCardActionUpdatePatch: postfix skipped not done");
                        }
                }
        }
}
