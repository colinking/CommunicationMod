package communicationmod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RefreshHandAction extends AbstractGameAction {
    public void update() {
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
