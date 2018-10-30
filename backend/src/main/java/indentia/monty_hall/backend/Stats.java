package indentia.monty_hall.backend;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;

import java.util.HashMap;
import java.util.Map;

import static indentia.monty_hall.backend.Stats.StatKey.LOSSES;
import static indentia.monty_hall.backend.Stats.StatKey.ROUNDS;
import static indentia.monty_hall.backend.Stats.StatKey.WINS;
import static indentia.monty_hall.backend.Stats.StatKey.WIN_PERCENTAGE;
import static java.util.Collections.unmodifiableMap;

public class Stats {

    private Map<StatKey, Integer> stats = new HashMap<>();

    Stats() {
        stats.put(WINS, 0);
        stats.put(LOSSES, 0);
        stats.put(ROUNDS, 0);
    }

    private void increment(StatKey key) {
        stats.put(key, stats.get(key) + 1);
    }

    void addWin() {
        increment(WINS);
        increment(ROUNDS);
        updateWinPercentage();
    }

    void addLoss() {
        increment(LOSSES);
        increment(ROUNDS);
        updateWinPercentage();
    }

    private void updateWinPercentage() {
        stats.put(WIN_PERCENTAGE, (int) (((double) stats.get(WINS) / (double) stats.get(ROUNDS)) * 100.0));
    }

    @Override
    public String toString() {
        return stats.toString();
    }

    public Map<StatKey, Integer> getStats() {
        return unmodifiableMap(stats);
    }

    enum StatKey {
        ROUNDS,
        WINS,
        LOSSES,
        WIN_PERCENTAGE
    }
}
