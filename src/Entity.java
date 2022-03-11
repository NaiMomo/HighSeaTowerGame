import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Pour la reutilisation des variables...
 * Oblige a avoir update et draw pour tous les entités de jeu
 */
public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;

    protected double vx, vy;
    protected double ax, ay;

    protected Color color;


    /**
     *
     * @param dt delta temps
     * @param fenetreY position de la fenetre en Y
     */
    public abstract void update(double dt, double fenetreY);


    /**
     *
     * @param context
     * @param fenetreY
     */
    public abstract void draw(GraphicsContext context, double fenetreY);
}