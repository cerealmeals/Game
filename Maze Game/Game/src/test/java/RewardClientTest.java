import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import reward.*;

public class RewardClientTest {

    RewardClient RC = RewardClient.getInstance(20);
    
    @BeforeEach
    public void setup(){
        RC.clear();
    }
    @Test
    public void GetterRewards(){ 
        List<Reward> correct = new ArrayList<>();
        assertEquals(correct, RC.getRewards());
    }
    @Test
    public void GetterNumRewards(){
        assertEquals(15, RC.getNumRewards());
    }

    @Test
    public void SetterNumRewards(){
        RC.setNumRewards(5);
        assertEquals(RC.getNumRewards(), 5);
    }

    @Test
    public void NormalGenerateTest(){
        //RC.clear();
        RC.generateRewards();
        assertEquals(RC.getNumRewards(),RC.getRewards().size());
    }
    
    @Test
    public void ZeroGenerateTest(){
        //RC.clear();
        RC.setNumRewards(0);
        RC.generateRewards();
        assertEquals(0, RC.getRewards().size());
    }

    @Test
    public void OneGenerateTest(){
        //RC.clear();
        RC.setNumRewards(1);
        RC.generateRewards();
        assertEquals(1, RC.getRewards().size());
    }

    @Test
    public void NormalCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Bonus", 50, 5, 5));
        assertEquals(50,RC.collectReward(5, 5));
    }

    @Test
    public void BigXCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Bonus", 50, 21, 5));
        assertEquals(0,RC.collectReward(5, 5));
    }

    @Test
    public void BigYCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Punishment", 50, 5, 21));
        assertEquals(0,RC.collectReward(5, 5));
    }

    @Test
    public void smallXCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Regular", 50, -21, 5));
        assertEquals(0,RC.collectReward(5, 5));
    }

    @Test
    public void smallYCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Bonus", 50, 5, -5));
        assertEquals(0,RC.collectReward(5, 5));
    }

    @Test
    public void InvalidLocationCollectTest(){
        RC.getRewards().add(RewardFactory.createReward("Bonus", 50, 21, 5));
        assertEquals(0,RC.collectReward(4, 4));
    }

    @Test
    public void RemoveTest(){
        RC.getRewards().add(RewardFactory.createReward("Bonus", 50, 5, 5));
        RC.collectReward(5, 5);
        List<Reward> correct = new ArrayList<>();
        assertEquals(correct, RC.getRewards());
    }

    @Test void ClearTest(){
        RC.generateRewards();
        RC.clear();
        List<Reward> correct = new ArrayList<>();
        assertEquals(correct, RC.getRewards());
    }
}