import javafx.scene.paint.Color;

/**
 * PlateformeRebondissante
 */
public class PlateformeRebondissante extends Plateforme {
    public PlateformeRebondissante (double x, double y){
        super(x, y); // Appel du constructeur parent
        this.color = Color.LIGHTGREEN;
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
            if(meduse.vy >= 100) {
                meduse.vy *= -1.5;
            }
            else{
                meduse.vy = -100;
            }
            meduse.setParterre(true);
        }
        return vFenetreY;
    }
}