import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class RedisTest {
    private static final int WHEN_SHOW_VIP = 10;
    public static final int USERS = 20;
    private static final int SLEEP = 1;

    private static void log(int UsersOnline, int type) {
        String log = "";
        switch (type) {
            case 0:
                log = String.format("> Пользователь %d оплатил платную услугу", UsersOnline);
                break;
            case 1:
                log = String.format("- На главной странице показываем пользователя: %d", UsersOnline);
                break;
        }
        out.println(log);
    }

    public static void main(String[] args) throws InterruptedException {
        RedisStorage redis = new RedisStorage();
        redis.init();
        redis.regUsers();

        int userPosition = 1;
        int NEW_USER = 1;
        while (true) {
            if (userPosition == WHEN_SHOW_VIP) {
                userPosition = 1;
                log(redis.showRandomUser(), 0);
            } else {
                int user = (int) redis.getUsers(NEW_USER);
                log(user, 1);
                redis.addToEndQueue();
            }
            userPosition++;
            TimeUnit.SECONDS.sleep(SLEEP);
        }
        //redis.shutdown();
    }
}