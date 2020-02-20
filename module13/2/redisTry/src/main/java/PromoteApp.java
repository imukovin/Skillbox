import org.redisson.Redisson;
import org.redisson.api.RDeque;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class PromoteApp {
    private static final int PROMOTE_TIME = 10;
    private static RedisWorker rw;

    public static void main(String[] args) throws InterruptedException {
        rw = new RedisWorker();

        while (true) {
            Integer promoteUser = rw.promoteUser();
            String log = String.format("> Пользователь %d оплатил платную услугу", promoteUser);
            out.println(log);
            TimeUnit.SECONDS.sleep(PROMOTE_TIME);
        }
    }

    private static class RedisWorker {
        private final static String KEY = "ONLINE_USERS";
        public static final int USERS_COUNT = 20;
        private RedissonClient redisson;
        private RDeque<Integer> onlineUsers;

        RedisWorker() {
            init();
            regUsers();
        }

        private void init() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            try {
                redisson = Redisson.create(config);
            } catch (RedisConnectionException Exc) {
                out.println("Не удалось подключиться к Redis");
                out.println(Exc.getMessage());
            }
            RKeys rKeys = redisson.getKeys();
            onlineUsers = redisson.getDeque(KEY);
            rKeys.delete(KEY);
        }

        private void regUsers() {
            for (int i = 1; i < USERS_COUNT; i++) {
                onlineUsers.add(i);
            }
        }

        private Integer promoteUser() {
            int promoteUser = (int) (Math.random() * USERS_COUNT);
            onlineUsers.remove(promoteUser);
            onlineUsers.addFirst(promoteUser);
            return promoteUser;
        }

        private List<Integer> getListUsers() {
            return onlineUsers.readAll();
        }
    }
}
