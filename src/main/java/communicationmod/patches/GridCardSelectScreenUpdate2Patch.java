package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(
        clz= GridCardSelectScreen.class,
        method="update"
)
public class GridCardSelectScreenUpdate2Patch {

    @SpireInsertPatch(
            locator=Locator.class
    )
    public static void Insert(GridCardSelectScreen _instance){
        HashMap<String, Object> action = new HashMap<>();
        ArrayList<Object> cards = new ArrayList<>();
        for (AbstractCard card : _instance.selectedCards) {
            cards.add(GameStateConverter.convertCardToJson(card));
        }
        action.put("cards", cards);

        ArrayList<Integer> cardIndexes = new ArrayList<>();
        for (AbstractCard card : _instance.selectedCards) {
            int cardIndex = -1;
            for (int i = 0; i < _instance.targetGroup.size(); i++) {
                if (_instance.targetGroup.group.get(i).uuid.equals(card.uuid)) {
                    cardIndex = i;
                    break;
                }
            }
            cardIndexes.add(cardIndex);
        }
        action.put("card_indexes", cardIndexes);

        CommunicationMod.reportAction("SelectCards", action);
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "closeCurrentScreen");
            return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
        }
    }

}
