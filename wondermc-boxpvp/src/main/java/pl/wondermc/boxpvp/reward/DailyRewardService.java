package pl.wondermc.boxpvp.reward;

import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;

import java.util.ArrayList;
import java.util.List;

public class DailyRewardService {

    private final List<DailyReward> dailyRewardList;

    public DailyRewardService(DailyRewardConfiguration dailyRewardConfiguration){
        this.dailyRewardList = new ArrayList<>();
        this.dailyRewardList.addAll(dailyRewardConfiguration.getDailyRewardList());


    }

    public List<DailyReward> getDailyRewardList() {
        return dailyRewardList;
    }




}
