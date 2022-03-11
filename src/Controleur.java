import javafx.scene.canvas.GraphicsContext;

/**
 * Contrôle les échanges entre HighSeaTower et le jeu
 */
public class Controleur {
    Jeu jeu;
    private boolean restart = false;

    /**
     * Demmare la partie
     */
    public Controleur() {
        jeu = new Jeu();
    }

    /**
     *
     * @param context dessine sur le canvas
     */
    void draw(GraphicsContext context) {
        jeu.draw(context);
    }

    /**
     *
     * @param context pour le mode debug
     */
    void debugDraw(GraphicsContext context) {
        jeu.debugDraw(context);
    }

    /**
     *
     * @param deltaTime
     */
    void update(double deltaTime) {
        jeu.update(deltaTime);
        restart = jeu.restart();
        if (restart) {
            jeu = new Jeu();
            jeu.update(0);
        }
    }

    /**
     *
     * @param dt update le temps pour debug
     */
    void debugUpdate(double dt) {
        jeu.debugUpdate(dt);
        restart = jeu.restart();
        if (restart) {
            jeu = new Jeu();
            jeu.update(0);
        }
    }

    /**
     *
     * @param restart
     */
    void setRestart(boolean restart) {
        this.restart = restart;
    }

    /**
     *
     * @return restart
     */
    boolean getRestart() {
        return restart;
    }

    /**
     *états de meduse
     */
    void haut() {
        jeu.haut();
    }
    void gauche() {
        jeu.gauche();
    }
    void droit() {
        jeu.droit();
    }
    void arret() {
        jeu.arret();
    }
}