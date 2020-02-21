import org.redisson.Redisson;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDeque;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class PromoteApp {
    private static final Duration PROMOTE_TIME = Duration.ofSeconds(10);
    private static RedisWorker rw;

    public static void main(String[] args) throws InterruptedException {
        rw = new RedisWorker();
        try {
            rw.init();
            rw.regUsers();
            while (true) {
                Integer promoteUser = rw.promoteUser();
                String log = String.format("> Пользователь %d оплатил платную услугу", promoteUser);
                out.println(log);
                TimeUnit.SECONDS.sleep(PROMOTE_TIME.getSeconds());
            }
        } catch (Exception e) {
            out.println("Problem " + e.getMessage());
        }
    }

    private static class RedisWorker {
        private final static String KEY = "ONLINE_USERS";
        public static final int USERS_COUNT = 20;
        private RedissonClient redisson;
        private RBlockingDeque<Integer> onlineUsers;

        private void init() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            try {
                redisson = Redisson.create(config);
            } catch (RedisConnectionException Exc) {
                out.println("Не удалось подключиться к Redis");
                out.println(Exc.getMessage());
            }
            onlineUsers = redisson.getBlockingDeque(KEY);
        }

        private void regUsers() {
            for (int i = 1; i < USERS_COUNT; i++) {
                onlineUsers.add(i);
            }
        }

        private Integer promoteUser() throws InterruptedException {
            int promoteUser = (int) (Math.random() * USERS_COUNT);
            onlineUsers.remove(promoteUser);
            onlineUsers.putFirst(promoteUser);
            return promoteUser;
        }

        private List<Integer> getListUsers() {
            return onlineUsers.readAll();
        }
    }
}
