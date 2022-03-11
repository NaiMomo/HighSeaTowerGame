import javafx.scene.paint.Color;

/**
 * PlateformeAccelerante
 */
public class PlateformeAccelerante extends Plateforme {
    public PlateformeAccelerante(double x, double y) {
        super(x, y); // Appel du constructeur parent
        this.color = Color.rgb(230, 221, 58, 1);
    }


    /**
     * redefinie testCollision du parent
     * @param meduse
     * @param fenetreY
     * @param vFenetreY
     * @return
     */
    @Override
    public double testCollision(Meduse meduse, double fenetreY, double vFenetreY) {
        if (intersects(meduse, fenetreY) && Math.abs(meduse.y + meduse.hauteur - this.y + fenetreY) < 30
                && meduse.vy >= 0) {
            pushOut(meduse, fenetreY);
            vFenetreY *= 3;
            meduse.vy = vFenetreY;
            meduse.setParterre(true);
        }
        return vFenetreY;
    }
}