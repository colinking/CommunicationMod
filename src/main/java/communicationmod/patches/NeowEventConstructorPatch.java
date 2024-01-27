package communicationmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.neow.NeowEvent;
import communicationmod.CommandExecutor;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@SpirePatch(
        clz= NeowEvent.class,
        method=SpirePatch.CONSTRUCTOR,
        paramtypez={
                boolean.class
        }
)
public class NeowEventConstructorPatch {
        public static void Postfix(NeowEvent __instance, boolean isDone) {
                if (CommandExecutor.isMiniBlessing != null) {
                        int bossCount = CommandExecutor.isMiniBlessing ? 0 : 1;
                        ReflectionHacks.setPrivate(__instance, NeowEvent.class, "bossCount", bossCount);
                }
        }
}
