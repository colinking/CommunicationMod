package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import communicationmod.CommunicationMod;
import communicationmod.GameStateConverter;
import communicationmod.GameStateListener;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(
        clz= RoomEventDialog.class,
        method="update"
)

public class RoomEventDialogPatch {

    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"i"}
    )
    public static void Insert(RoomEventDialog _instance, int i) {
        GameStateListener.registerStateChange();

        HashMap<String, Object> action = new HashMap<>();
        action.put("index", i);
        CommunicationMod.reportAction("SelectOption", action);
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(RoomEventDialog.class, "selectedOption");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
        }
    }

}
