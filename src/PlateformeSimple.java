import javafx.scene.paint.Color;

/**
 * PlateformeSimple
 */
public class PlateformeSimple extends Plateforme {
    public PlateformeSimple (double x, double y){
        super(x, y);
        this.color = Color.rgb(230, 134, 58, 1);
    }

    /**
     * redefinie testCollision du parent
     * @param meduse
     * @param fenetreY
     * @param vFenetreY
     * @return
     */
    @Override
    public double testCollision(Meduse meduse, double fenetreY, double vFenetreY){
        if (intersects(meduse, fenetreY) && Math.abs(meduse.y + meduse.hauteur - this.y + fenetreY) < 30
                && meduse.vy >= 0) {
            pushOut(meduse, fenetreY);
            meduse.vy = vFenetreY;
            meduse.setParterre(true);
        }
        return vFenetreY;
    }

}
