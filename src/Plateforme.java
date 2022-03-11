import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe parent pour toutes les plateformes
 */
public abstract class Plateforme extends Entity {
    private Color tempColor;

    /**
     * constructeur
     * @param x coordonnée en x
     * @param y coordonnée en x
     */
    public Plateforme(double x, double y) {
        this.x = x;
        this.y = y;
        this.hauteur = 10;
        this.largeur = Math.random() * 95 + 80;
    }

    /**
     * @param largeur setter
     */
    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    /**
     *
     * intersection meduse et plateforme
     * @param meduse
     * @param fenetreY
     * @return booléen
     */
        public boolean intersects(Meduse meduse, double fenetreY) {
            return !(
                    x + largeur < meduse.x ||
                            meduse.x + meduse.largeur < this.x ||
                            y - fenetreY + hauteur < meduse.y ||
                            meduse.y + meduse.hauteur < this.y - fenetreY);
        }

    /**
     * implémente + redéfinie update
     * @param dt delta temps
     * @param fenetreY position de la fenetre en Y
     */
    @Override
    public void update(double dt, double fenetreY) {
        if (x + largeur > HighSeaTower.largeur) {
            x = HighSeaTower.largeur - largeur;
        }
        if (x + largeur < 0) {
            x = 0 + largeur;
        }
    }

    /**
     * implémente + redéfinie draw
     * @param context
     * @param fenetreY
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillRect(x, y - fenetreY, largeur, hauteur);
    }

    /**
     * draw pour debug
     * @param context
     * @param meduse
     * @param fenetreY
     */
    public void debugDraw(GraphicsContext context, Meduse meduse, double fenetreY) {
        if (this.intersects(meduse, fenetreY) && Math.abs(meduse.y + meduse.hauteur - this.y + fenetreY) < 30 &&
                meduse.vy >= 0 || intersects(meduse, fenetreY) && Math.abs(meduse.y - this.y + fenetreY) < 30 &&
                meduse.vy < 0 && !meduse.getParterre() && this instanceof PlateformeSolide) {
            tempColor = color.YELLOW;
        } else {
            tempColor = this.color;
        }
        context.setFill(tempColor);
        context.fillRect(x, y - fenetreY, largeur, hauteur);
    }

    /**
     * Résout la collision
     * @param meduse
     * @param fenetreY
     */
    public void pushOut(Meduse meduse, double fenetreY) {
        double deltaY = meduse.y + meduse.hauteur - this.y + fenetreY;
        meduse.y -= deltaY;
    }

    /**
     * test collision entre méduse et plateforme
     * @param meduse
     * @param fenetreY
     * @param vFenetreY
     * @return
     */
    public abstract double testCollision(Meduse meduse, double fenetreY, double vFenetreY);
}