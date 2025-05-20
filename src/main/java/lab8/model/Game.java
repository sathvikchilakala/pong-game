package lab8.model;

public final class Game implements Field {

    private static Game INSTANCE;

    private final DataRepository state;
    private final Ball ball;

    private Game() {
        state = new DataRepository(
                WIDTH / 2, HEIGHT / 2,
                HEIGHT / 2 - PADDLE_HEIGHT / 2,
                HEIGHT / 2 - PADDLE_HEIGHT / 2,
                RIGHT, SERVER);
        ball = new Ball(state.getBallX(), state.getBallY());
    }

    public static Game get() {
        if (INSTANCE == null) INSTANCE = new Game();
        return INSTANCE;
    }

    public void tick() { moveBall(); }


    private void moveBall() {
        ball.move();
        state.setBall(ball.getX(), ball.getY());

        if (ball.getX() >= WIDTH || ball.getX() <= 0) {
            ball.reset(WIDTH / 2, HEIGHT / 2);
        }

        if (collisionWithPaddle()) ball.reverseX();
    }

    private boolean collisionWithPaddle() {
        int bx = ball.getX();
        int by = ball.getY();

        boolean hitServer =
                bx == SERVER_X + PADDLE_WIDTH &&
                by >= state.getServerPaddleY() &&
                by <= state.getServerPaddleY() + PADDLE_HEIGHT;

        boolean hitClient =
                bx == CLIENT_X - 5 &&       
                by >= state.getClientPaddleY() &&
                by <= state.getClientPaddleY() + PADDLE_HEIGHT;

        return hitServer || hitClient;
    }

    public DataRepository snapshot() { return state.clone(); }
    public void applySnapshot(DataRepository s) {
        state.setBall(s.getBallX(), s.getBallY());
        state.setServerPaddleY(s.getServerPaddleY());
        state.setClientPaddleY(s.getClientPaddleY());
    }
}
