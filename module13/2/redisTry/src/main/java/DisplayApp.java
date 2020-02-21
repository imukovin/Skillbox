import org.redisson.Redisson;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class DisplayApp {
    private static final Duration SHOW_TIME = Duration.ofSeconds(1);
    private static RedisWorker rw;

    public static void main(String[] args) throws InterruptedException {
        rw = new RedisWorker();
        try {
            rw.init();
            while (true) {
                Integer user = rw.showAndRotateUser();
                String log = String.format("- На главной странице показываем пользователя: %d", user);
                out.println(log);
                TimeUnit.SECONDS.sleep(SHOW_TIME.getSeconds());
            }
        } catch (Exception e) {
            out.println("Problem " + e.getMessage());
        }
    }

    private static class RedisWorker {
        private final static String KEY = "ONLINE_USERS";
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

        private Integer showAndRotateUser() throws InterruptedException {
            Integer user = onlineUsers.takeFirst();
            onlineUsers.add(user);
            return user;
        }
    }
}
