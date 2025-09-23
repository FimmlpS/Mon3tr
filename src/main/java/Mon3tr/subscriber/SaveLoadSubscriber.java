package Mon3tr.subscriber;

import basemod.interfaces.StartGameSubscriber;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public interface SaveLoadSubscriber extends StartGameSubscriber {

    @Override
    public default void receiveStartGame() {
        if (CardCrawlGame.loadingSave) {
            onLoadGame();
        }
    }

    /**
     * 只有在继续游戏时才会触发，初次开始游戏不会触发。
     */
    abstract void onLoadGame();

}

