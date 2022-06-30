package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_COMPLETE_ACK extends game.network.game.GamePacketACK
{
	int mission_id, value, task;
    public MISSION_QUEST_COMPLETE_ACK(int mission_id, int value, int task)
    {
        super();
        this.mission_id = mission_id;
        this.value = value;
        this.task = task;
    }
    @Override
    public void writeImpl()
    {
        WriteC(mission_id + task); //255
        WriteC(value);
    }
}