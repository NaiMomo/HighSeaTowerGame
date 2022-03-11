import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Pour créer les bulles
 */
public class Bulles extends Entity {
    private double x, y, vy, rayon;
    private Color color;


    /**
     *
     * constructeur
     * @param baseX coordonnée x de bulle
     * vx,vy, vitesse en x et y
     * rayon de bulle
     * couleur des bulles
     */

    public Bulles(double baseX) {

        x = baseX;
        y = 550;
        vy = (Math.random() * 450) + 350;
        this.vx = 0;
        rayon = (Math.random() * 40) + 10;
        color = Color.rgb(0, 0, 255, 0.4);
    }

    /**
     *implémente + redéfinie update
     * @param dt delta temps
     * @param y position bulle
     */
    public void update(double dt, double y) {
        this.y += -(dt * vy);
    }

    /**
     * implémente + redéfinie draw
     * @param context a dessiner
     * @param fenetreY position
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillOval(x - rayon / 2, y - rayon / 2,
                rayon, rayon);
    }
}