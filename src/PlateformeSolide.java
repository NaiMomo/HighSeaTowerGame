import javafx.scene.paint.Color;

/**
 * PlateformeSolide
 */
public class PlateformeSolide extends Plateforme {
    public PlateformeSolide (double x, double y){
        super(x, y);
        this.color = Color.rgb(184, 15, 36, 1);
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
        if (intersects(meduse, fenetreY) && Math.abs(meduse.y - this.y + fenetreY) < 30
                && meduse.vy < 0 && !meduse.getParterre()) {
            double deltaY = meduse.y + meduse.hauteur - this.y + fenetreY;
            meduse.y += deltaY;
            meduse.vy = 0;
        }
        return vFenetreY;
    }
}
