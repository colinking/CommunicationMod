package communicationmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import communicationmod.CommunicationMod;
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
