package communicationmod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.HashMap;

public class EndOfTurnAction extends AbstractGameAction {
    public void update() {
        GameStateListener.signalTurnEnd();
        this.isDone = true;

        HashMap<String, Object> action = new HashMap<>();
        CommunicationMod.reportAction("EndTurn", action);
    }
}
