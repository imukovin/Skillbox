import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import static java.lang.System.out;

public class RedisStorage {
    private final static String KEY = "ONLINE_USERS";
    private RedissonClient redisson;
    private RScoredSortedSet<String> onlineUsers;

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        RKeys rKeys = redisson.getKeys();
        onlineUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    void regUsers() {
        for (int i = 1; i <= RedisTest.USERS; i++) {
            onlineUsers.addScore(String.valueOf(i), i);
        }
    }

    double getUsers(int pos) {
        return onlineUsers.getScore(String.valueOf(pos));
    }

    void addToEndQueue() {
        int currentFirstUser = (int) (double) onlineUsers.getScore("1");
        displaceListUsers(0, currentFirstUser);
    }

    int showRandomUser() {
        int numOfUsers = RedisTest.USERS;
        int userKey = (int) (Math.random() * ++numOfUsers);
        int userValue = (int) (double) onlineUsers.getScore(String.valueOf(userKey));
        displaceListUsers(userKey, userValue);
        return userValue;
    }

    void displaceListUsers(int from, int displaceUser) {
        for (int i = from; i < RedisTest.USERS; i++) {
            int pos = i + 1;
            double currentUser = onlineUsers.getScore(String.valueOf(pos));
            onlineUsers.remove(String.valueOf(i));
            onlineUsers.addScore(String.valueOf(i), currentUser);
        }
        onlineUsers.remove(String.valueOf(RedisTest.USERS));
        onlineUsers.addScore(String.valueOf(RedisTest.USERS), displaceUser);
    }
}
