import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import reward.*;

public class RewardTest {
    
    @Test
    public void GetterY(){
        BonusReward reward = new BonusReward(50, 5, 3);
        assertEquals(3, reward.getY());
    }

    @Test
    public void GetterX(){
        GeneralReward reward = new GeneralReward(5, 4, 3);
        assertEquals(4, reward.getX());
    }

    @Test
    public void GetterScore(){
        Punishment reward = new Punishment(500, 6, 7);
        assertEquals(500, reward.getScoreValue());
    }

    @Test
    public void GetterCollected(){
        BonusReward reward = new BonusReward(50, 5, 3);
        assertEquals(false, reward.isCollected());
    }

    @Test
    public void Collected(){
        BonusReward reward = new BonusReward(50, 5, 3);
        reward.collect();
        assertTrue(reward.isCollected());
    }
    
}
