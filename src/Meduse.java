import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Gére tous les modification relié au médeus
 */
public class Meduse extends Entity {

    private Image[] frames;
    private Image image;
    private double frameRate = 8;
    private double tempsTotal = 0;
    private boolean parterre;

    /**
     *
     * constructeur
     * @param x coordonnée en x
     * @param y coordonnée en x
     *  ay = accélération en y
     *  largeur,hauteur de meduse
     */
    public Meduse(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 50;
        this.hauteur = 50;
        this.ay = 1200;
    }


    /**
     * accélération direction gauche
     */
    public void gauche() {
        this.ax = -1200;
    }

    /**
     * accélération direction droite
     */
    public void droit() {
        this.ax = 1200;
    }


    /**
     * vitesse direction haut
     */
    public void haut() {
        if (this.parterre) {
            this.vy = -600;
        }
    }

    /**
     * vitesse et accélération durant la friction
     */
    public void arret() {
        this.vx = 0;
        this.ax = 0;
    }

    /**
     * implémente + redéfinie update
     * gère les frames par second et affiche les images
     * @param dt delta temps
     * @param fenetreY position de la fenetre en Y
     */
    @Override
    public void update(double dt, double fenetreY) {

        // Physique du personnage
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        //une vitesse maximale pour sauter, pour éviter de vitesse ridicule en rebond
        if (vy < -700) {
            vy = -700;
        }

        //une vitesse maximale pour tomber, pour éviter de vitesse ridicule en rebond
        if (vy > 700) {
            vy = 700;
        }

        if (x + largeur > HighSeaTower.largeur || x < 0) {
            vx *= -1;
        }

        x = Math.min(x, HighSeaTower.largeur - largeur);
        x = Math.max(x, 0);

        if (this.ax < 0) {
            frames = new Image[] {
                    new Image("images/jellyfish1g.png"),
                    new Image("images/jellyfish2g.png"),
                    new Image("images/jellyfish3g.png"),
                    new Image("images/jellyfish4g.png"),
                    new Image("images/jellyfish5g.png"),
                    new Image("images/jellyfish6g.png"),
            };

        }
        if (this.ax >= 0) {
            frames = new Image[] {
                    new Image("images/jellyfish1.png"),
                    new Image("images/jellyfish2.png"),
                    new Image("images/jellyfish3.png"),
                    new Image("images/jellyfish4.png"),
                    new Image("images/jellyfish5.png"),
                    new Image("images/jellyfish6.png"),
            };
        }
        image = frames[0];

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int)(tempsTotal * frameRate);

        image = frames[frame % frames.length];
    }

    /**
     *
     * @param parterre setter
     */
    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    /**
     * getter
     * @return parterre
     */
    public boolean getParterre() {
        return this.parterre;
    }

    /**
     * implémente + redéfinie draw
     * @param context
     * @param fenetreY
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {

        context.drawImage(image, x, y, largeur, hauteur);
    }

    /**
     * draw pour mode debug
     * @param context
     * @param fenetreY
     */
    public void debugDraw(GraphicsContext context, double fenetreY) {
        context.drawImage(image, x, y, largeur, hauteur);
        context.setFill(color.rgb(255, 0, 0, 0.4));
        context.fillRect(x, y, largeur, hauteur);
    }

}