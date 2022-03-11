
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Declaration des variables globales
 */
public class HighSeaTower extends Application {
    public static final int largeur = 350, hauteur = 480;
    private long lastTime = 0;
    private boolean restart = false;
    private boolean debug = false;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage scene principal
     */

    @Override
    public void start(Stage primaryStage) {


        Scene scene = new Scene(largeur, hauteur, Color.DARKBLUE);


        //ajout de 1ere image avant le debut de la partie
        Image image = new Image("/images/jellyfish1.png");
        primaryStage.getIcons().add(image);

        /*
         * Crerer le controleur
         * envoie le temps et le context a le controleur
         */
        Controleur controleur = new Controleur();

        GraphicsContext context = canvas.getGraphicsContext2D();
        controleur.update(0);
        controleur.draw(context);



        //Timer
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                //Saute le premier appel
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                //delta temp
                double dt = (now - lastTime) * 1e-9;

                restart = controleur.getRestart();
                context.clearRect(0, 0, largeur, hauteur);

                /*
                * réinitialise tout après la fin du partie
                * et arrête le timer
                */
                if (restart) {
                    dt = 0;
                    now = 0;
                    lastTime = 0;
                    stop();
                    controleur.setRestart(false);
                }


                //active mode debug
                if (!debug) {
                    controleur.update(dt);
                    controleur.draw(context);
                    lastTime = now;
                }

                //desactive mode debug
                if (debug) {
                    controleur.debugUpdate(dt);
                    controleur.debugDraw(context);
                    lastTime = now;
                }
            }
        };

        /*
         * Vérifier les touches enfoncées par l'utilisateur
         * démarre la minuterie puis appele le contrôleur
         * aussi detecte si l'utlisateur demande le mode debug
         */
        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case LEFT:
                    timer.start();
                    controleur.gauche();
                    break;
                case RIGHT:
                    timer.start();
                    controleur.droit();
                    break;
                case UP:
                case SPACE:
                    timer.start();
                    controleur.haut();
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
                case T:
                    debug = !debug;
            }
        });


        //utile pour friction de meduse avec les plateformes
        scene.setOnKeyReleased((e) -> {
            switch (e.getCode()) {
                case LEFT:
                case RIGHT:
                    controleur.arret();
            }
        });

        primaryStage.setTitle("High Sea Tower");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}