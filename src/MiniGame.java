import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class MiniGame extends Application {
    // magic numbers
    static final int DEFAULT_VELOCITY = 150;
    static final int W_WIDTH = 1280;
    static final int W_HEIGHT = 640;
    static final int BASELINE = 300;
    public boolean hasShot = false;
    public List<ImageView> fireBallArray = new ArrayList<ImageView>();
    public List<ImageView> enemyArray = new ArrayList<ImageView>();
    public int scores = 0;
    public int lives = 3;
    public int speedTime = 8000;
    public Random rd = new Random();
    public int enemies = 15;

    ImageView getBackgroundImage(){
        Image backgroundImage = new Image("file:src/resources/img/background2.png");
        ImageView iv = new ImageView(backgroundImage);
        iv.setFitWidth(W_WIDTH);
        iv.setFitHeight(W_HEIGHT);
        return iv;
    }

    void labelZoom(Label label){
        label.setFont(Font.loadFont("file:src/resources/SHOWG.TTF", 30));
        label.setTextFill(Color.WHITE);
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label.setScaleX(1.5);
                label.setScaleY(1.5);
            }
        });
        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                label.setScaleX(1);
                label.setScaleY(1);
            }
        });
        label.setTextAlignment(TextAlignment.CENTER);
    }

    void labelBlack(Label label){
        label.setFont(Font.loadFont("file:src/resources/SHOWG.TTF", 20));
        label.setTextFill(Color.BLACK);
        label.setTextAlignment(TextAlignment.CENTER);
    }

    void fireBallShoot(Pane rootLevel1, boolean isRight){
        // set fireball
        Image imageFireBall = new Image("file:src/resources/img/fireball2.gif");
        ImageView ivFireBall = new ImageView(imageFireBall);
        ivFireBall.setLayoutX(700);
        ivFireBall.setLayoutY(460);
        if(!isRight){
            ivFireBall.setScaleX(-1);
            ivFireBall.setLayoutX(600);
        }
        fireBallArray.add(ivFireBall);
        rootLevel1.getChildren().add(ivFireBall);

        // test fireball animation
        Path path = new Path();
        int shootDistance = 200;
        if(getLevel(speedTime) == 2){
            shootDistance = 150;
        } else if (getLevel(speedTime) == 3){
            shootDistance = 100;
        }
        if(!isRight){
            shootDistance *= -1;
        }
        path.getElements().add(new MoveTo(0,0));
        path.getElements().add(new LineTo(shootDistance,0));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setPath(path);
        pathTransition.setNode(ivFireBall);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rootLevel1.getChildren().contains(ivFireBall)){
                    scores--;
                }
                fireBallArray.remove(ivFireBall);
                rootLevel1.getChildren().remove(ivFireBall);
            }
        });
        pathTransition.play();
        String fireCastPath = "src/resources/sound/firecast.wav";
        Media sound = new Media(new File(fireCastPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    void EnemyContinue(Pane rootLevel1, boolean isRight){
        // set enemy
        Image imageEnemy = new Image("file:src/resources/img/enemy.png");
        ImageView ivEnemy = new ImageView(imageEnemy);
        ivEnemy.setLayoutX(490-ivEnemy.getLayoutBounds().getWidth());
        ivEnemy.setLayoutY(330);
        ivEnemy.setScaleX(-1);
        if(!isRight){
            ivEnemy.setScaleX(1);
            ivEnemy.setLayoutX(1000-ivEnemy.getLayoutBounds().getWidth());
        }
        enemyArray.add(ivEnemy);
        rootLevel1.getChildren().add(ivEnemy);

        // test enemy animation
        Path path = new Path();
        int shootDistance = 250;
        if(!isRight){
            shootDistance = -30;
        }

        double fromX = ivEnemy.getLayoutBounds().getWidth() / 2;
        double fromY = ivEnemy.getLayoutBounds().getHeight() / 2;
        path.getElements().add(new MoveTo(fromX,fromY));
        path.getElements().add(new LineTo(shootDistance,fromY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setPath(path);
        pathTransition.setNode(ivEnemy);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rootLevel1.getChildren().contains(ivEnemy)){
                    enemyArray.remove(ivEnemy);
                    rootLevel1.getChildren().remove(ivEnemy);
                    lives--;
                }
            }
        });
        pathTransition.play();
    }

    void EnemyStepBack(Pane rootLevel1, boolean isRight){
        // set enemy
        Image imageEnemy = new Image("file:src/resources/img/enemy.png");
        ImageView ivEnemy = new ImageView(imageEnemy);
        ivEnemy.setLayoutX(520-ivEnemy.getLayoutBounds().getWidth() / 2);
        ivEnemy.setLayoutY(330);
        ivEnemy.setScaleX(-1);
        if(!isRight){
            ivEnemy.setScaleX(1);
            ivEnemy.setLayoutX(800-ivEnemy.getLayoutBounds().getWidth() / 2);
        }
        enemyArray.add(ivEnemy);
        rootLevel1.getChildren().add(ivEnemy);

        // test enemy animation
        Path path = new Path();
        int shootDistance = -30;
        if(!isRight){
            shootDistance = 200;
        }

        double fromX = ivEnemy.getLayoutBounds().getWidth() / 2;
        double fromY = ivEnemy.getLayoutBounds().getHeight() / 2;
        path.getElements().add(new MoveTo(fromX,fromY));
        path.getElements().add(new LineTo(shootDistance,fromY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setPath(path);
        pathTransition.setNode(ivEnemy);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rootLevel1.getChildren().contains(ivEnemy)){
                    enemyArray.remove(ivEnemy);
                    rootLevel1.getChildren().remove(ivEnemy);
                    EnemyContinue(rootLevel1, isRight);
                }
            }
        });
        pathTransition.play();
    }

    void spawnEnemy(Pane rootLevel1, boolean isRight){
        // set enemy
        Image imageEnemy = new Image("file:src/resources/img/enemy.png");
        ImageView ivEnemy = new ImageView(imageEnemy);
        ivEnemy.setLayoutX(-250);
        ivEnemy.setLayoutY(330);
        ivEnemy.setScaleX(-1);
        if(!isRight){
            ivEnemy.setScaleX(1);
            ivEnemy.setLayoutX(1300);
        }
        enemyArray.add(ivEnemy);
        rootLevel1.getChildren().add(ivEnemy);

        // test enemy animation
        Path path = new Path();
        int shootDistance = 770;
        if(!isRight){
            shootDistance = -500;
        }

        double fromX = ivEnemy.getLayoutBounds().getWidth() / 2;
        double fromY = ivEnemy.getLayoutBounds().getHeight() / 2;
        path.getElements().add(new MoveTo(fromX,fromY));
        path.getElements().add(new LineTo(shootDistance,fromY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(speedTime));
        pathTransition.setPath(path);
        pathTransition.setNode(ivEnemy);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rootLevel1.getChildren().contains(ivEnemy)){
                    enemyArray.remove(ivEnemy);
                    rootLevel1.getChildren().remove(ivEnemy);
                    EnemyStepBack(rootLevel1, isRight);
                    lives--;
                }
            }
        });
        pathTransition.play();
    }

    void checkBounds(Pane rootLevel1){
        Iterator<ImageView> fireBallIterator = fireBallArray.iterator();
        Iterator<ImageView> enemyIterator = enemyArray.iterator();
        while(fireBallIterator.hasNext()){
            ImageView fireBall = fireBallIterator.next();
            while(enemyIterator.hasNext()){
                ImageView enemy = enemyIterator.next();
                if(fireBall.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                    rootLevel1.getChildren().remove(fireBall);
                    rootLevel1.getChildren().remove(enemy);
                    fireBallIterator.remove();
                    enemyIterator.remove();
                    // kill sound
                    String killSoundPath = "src/resources/sound/kill_sound.wav";
                    Media sound = new Media(new File(killSoundPath).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    enemies--;
                    // kill, then score +1
                    scores++;
                }
            }
        }
    }

    int getLevel(int speed){
        if(speed == 8000){
            return 1;
        } else if (speed == 5000){
            return 2;
        } else if (speed == 3000){
            return 3;
        }
        return 0;
    }

    int getLeftEnemies(){
        int leftLevel = enemies;
        if(getLevel(speedTime) == 1){
            leftLevel -= 10;
        } else if(getLevel(speedTime) == 2){
            leftLevel -= 5;
        }
        return leftLevel;
    }

    @Override
    public void start(Stage stage) {
        // Intro scene
        StackPane rootIntro = new StackPane();
        Scene sceneIntro = new Scene(rootIntro , W_WIDTH, W_HEIGHT);

        // Level1 scene
        Pane rootLevel1 = new Pane();
        Scene sceneLevel1 = new Scene(rootLevel1 , W_WIDTH, W_HEIGHT);

        // Game Over scene
        StackPane rootGameOver = new StackPane();
        Scene sceneGameOver = new Scene(rootGameOver, W_WIDTH, W_HEIGHT);

        // Win Scene
        StackPane rootWin = new StackPane();
        Scene sceneWin = new Scene(rootWin, W_WIDTH, W_HEIGHT);

        // add background image to sceneIntro and sceneLevel1
        rootIntro.getChildren().add(getBackgroundImage());
        rootLevel1.getChildren().add(getBackgroundImage());
        rootGameOver.getChildren().add(getBackgroundImage());
        rootWin.getChildren().add(getBackgroundImage());

        // add VBox for sceneIntro
        VBox vBoxIntro = new VBox();
        // logo image
        Image logoImage = new Image("file:src/resources/img/logo.png");
        ImageView ivLogo = new ImageView(logoImage);
        vBoxIntro.getChildren().add(ivLogo);
        // student info
        Label studentInfo = new Label("PRODUCED BY YESHU WU\nSTUDENT NUMBER: 20663999");
        labelZoom(studentInfo);
        vBoxIntro.getChildren().add(studentInfo);
        // control rule
        Label controlRule = new Label("CONTROLS\nPRESS LEFT OR RIGHT TO FIRE");
        labelZoom(controlRule);
        vBoxIntro.getChildren().add(controlRule);
        // start and quit rule
        Label startRule = new Label("START GAME - ENTER\nQUIT - Q");
        labelZoom(startRule);
        vBoxIntro.getChildren().add(startRule);
        // place vBoxIntro
        vBoxIntro.setSpacing(15);
        vBoxIntro.setAlignment(Pos.CENTER);
        rootIntro.getChildren().add(vBoxIntro);

        // add VBox for game over screen
        VBox vBoxGameOver = new VBox();
        // game over scores
        Label gameOverScores = new Label("You lose this game.\nYour score is: " + scores);
        labelZoom(gameOverScores);
        vBoxGameOver.getChildren().add(gameOverScores);
        // instruction
        Label restartRule = new Label("restart game - enter\nquit - q");
        labelZoom(restartRule);
        vBoxGameOver.getChildren().add(restartRule);
        // place vBoxGameOver
        vBoxGameOver.setSpacing(30);
        vBoxGameOver.setAlignment(Pos.CENTER);
        rootGameOver.getChildren().add(vBoxGameOver);

        // add VBox for win screen
        VBox vBoxWin = new VBox();
        // game over scores
        Label winScores = new Label("You win this game!\nYour score is: " + scores);
        labelZoom(winScores);
        vBoxWin.getChildren().add(winScores);
        // instruction
        Label winRestart = new Label("restart game - enter\nquit - q");
        labelZoom(winRestart);
        vBoxWin.getChildren().add(winRestart);
        // place vBoxGameOver
        vBoxWin.setSpacing(30);
        vBoxWin.setAlignment(Pos.CENTER);
        rootWin.getChildren().add(vBoxWin);

        // add HBox for sceneLevel1
        HBox hBoxNumber = new HBox();
        // scores
        Label scoresLabel = new Label("Your score: " + scores);
        labelBlack(scoresLabel);
        hBoxNumber.getChildren().add(scoresLabel);
        // lives
        Label livesLabel = new Label("Your lives: " + lives);
        labelBlack(livesLabel);
        hBoxNumber.getChildren().add(livesLabel);
        // level
        Label levelLabel = new Label("Your Level: " + getLevel(speedTime));
        labelBlack(levelLabel);
        hBoxNumber.getChildren().add(levelLabel);
        // enemies left
        Label enemiesLabel = new Label("Enemies left: " + getLeftEnemies());
        labelBlack(enemiesLabel);
        hBoxNumber.getChildren().add(enemiesLabel);
        // congrats
        Label levelCongrats = new Label("Congrats! You've reached Level "
                + getLevel(speedTime) + "\nYou may continue or quit by pressing Q.");
        labelBlack(levelCongrats);
        hBoxNumber.getChildren().add(levelCongrats);
        levelCongrats.setVisible(false);
        // set hBox
        hBoxNumber.setSpacing(15);
        hBoxNumber.setAlignment(Pos.CENTER);
        rootLevel1.getChildren().add(hBoxNumber);

        // spawn enemies
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
                    if (rd.nextFloat() < 0.25){
                        spawnEnemy(rootLevel1, rd.nextBoolean());
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        Timeline timelineCongrats = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    levelCongrats.setVisible(false);
                })
        );

        // check bound timer
        AnimationTimer checkBoundTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkBounds(rootLevel1);
                if(enemies <= 0){
                    String failPath = "src/resources/sound/won.wav";
                    Media sound = new Media(new File(failPath).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    stage.setScene(sceneWin);
                    timeline.stop();
                    this.stop();
                } else if (enemies <= 5){
                    levelCongrats.setVisible(true);
                    timelineCongrats.play();
                    speedTime = 3000;
                } else if (enemies <= 10){
                    levelCongrats.setVisible(true);
                    timelineCongrats.play();
                    speedTime = 5000;
                }
                scoresLabel.setText("Your score: " + scores);
                livesLabel.setText("Your lives: " + lives);
                levelLabel.setText("Your level: " + getLevel(speedTime));
                enemiesLabel.setText("Enemies left: " + getLeftEnemies());
                levelCongrats.setText("Congrats! You've reached Level "
                        + getLevel(speedTime) + "\nYou may continue or quit by pressing Q.");
                gameOverScores.setText("You lose this game.\nYour score is: " + scores);
                winScores.setText("You win this game!\nYour score is: " + scores);
                if(lives <= 0){
                    stage.setScene(sceneGameOver);
                    timeline.stop();
                    this.stop();
                    String failPath = "src/resources/sound/fail.wav";
                    Media sound = new Media(new File(failPath).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            }
        };

        // keyboard handler for scene Intro
        sceneIntro.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        stage.setScene(sceneLevel1);
                        timeline.play();
                        checkBoundTimer.start();
                        break;
                    case DIGIT1:
                        stage.setScene(sceneLevel1);
                        timeline.play();
                        checkBoundTimer.start();
                        speedTime = 8000;
                        break;
                    case DIGIT2:
                        stage.setScene(sceneLevel1);
                        timeline.play();
                        checkBoundTimer.start();
                        levelCongrats.setVisible(false);
                        enemies = 10;
                        speedTime = 5000;
                        break;
                    case DIGIT3:
                        stage.setScene(sceneLevel1);
                        timeline.play();
                        checkBoundTimer.start();
                        levelCongrats.setVisible(false);
                        enemies = 5;
                        speedTime = 3000;
                        break;
                    case Q:
                        stage.close();
                        break;
                }
            }
        });

        // set player image
        Image imagePlayer = new Image("file:src/resources/img/idle.png");
        ImageView ivPlayer = new ImageView(imagePlayer);
        ivPlayer.setLayoutX(540);
        ivPlayer.setLayoutY(300);
        rootLevel1.getChildren().add(ivPlayer);

        // player fire image
        Image imagePlayerFire = new Image("file:src/resources/img/fire.png");

        // keyboard handler for scene Level 1
        sceneLevel1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        ivPlayer.setScaleX(-1);
                        ivPlayer.setImage(imagePlayerFire);
                        if(!hasShot){
                            fireBallShoot(rootLevel1, true);
                            hasShot = true;
                        }
                        break;
                    case LEFT:
                        ivPlayer.setScaleX(1);
                        ivPlayer.setImage(imagePlayerFire);
                        if(!hasShot){
                            fireBallShoot(rootLevel1, false);
                            hasShot = true;
                        }
                        break;
                    case Q:
                        stage.close();
                        break;
                }
            }
        });

        // keyboard release
        sceneLevel1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                    case LEFT:
                        ivPlayer.setImage(imagePlayer);
                        hasShot = false;
                        break;
                }
            }
        });

        sceneGameOver.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        speedTime = 8000;
                        enemies = 15;
                        scores = 0;
                        lives = 3;
                        for(ImageView fireBall : fireBallArray){
                            rootLevel1.getChildren().remove(fireBall);
                        }
                        for(ImageView enemy : enemyArray){
                            rootLevel1.getChildren().remove(enemy);
                        }
                        fireBallArray.clear();
                        enemyArray.clear();
                        stage.setScene(sceneIntro);
                        timeline.play();
                        checkBoundTimer.start();
                        break;
                    case Q:
                        stage.close();
                        break;
                }
            }
        });

        sceneWin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        speedTime = 8000;
                        enemies = 15;
                        scores = 0;
                        lives = 3;
                        for(ImageView fireBall : fireBallArray){
                            rootLevel1.getChildren().remove(fireBall);
                        }
                        for(ImageView enemy : enemyArray){
                            rootLevel1.getChildren().remove(enemy);
                        }
                        fireBallArray.clear();
                        enemyArray.clear();
                        stage.setScene(sceneIntro);
                        timeline.play();
                        checkBoundTimer.start();
                        break;
                    case Q:
                        stage.close();
                        break;
                }
            }
        });

        // Attach the scene to the stage and show it
        stage.setTitle("Monster VS Monsters");
        stage.setScene(sceneIntro);
        stage.show();
    }
}
