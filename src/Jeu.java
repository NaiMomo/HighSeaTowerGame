import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Modèle de jeu appelé par le contrôleur
 */
public class Jeu {

    // Largeur, hauteur du niveau, tempsTotal
    private double width = 350, height = 480;
    private double tempsTotal = 0;
    private double deltaTemps;

    // nombre de groupe de bulles , nombre de bulle
    // sec = pour ajout de bulles chaque 3 secondes
    private double groupe = 3;
    private double nbrBulle = 5;
    private int sec = 3;

    // Origine de la fenêtre
    private double fenetreY = 0;
    private double vFenetreY = 50;
    private double aFenetreY = 2;


    // Entités dans le jeu
    private Meduse meduse;
    private PlateformeSimple plateformeInitiale;
    private Queue < Plateforme > plateformes;
    private ArrayList < Bulles > bulles;


    /**
     * Constructeur qui initialise les entités au debut de la partie
     */
    public Jeu() {
        meduse = new Meduse(150, 430);
        plateformeInitiale = new PlateformeSimple(0, 480);
        plateformeInitiale.setLargeur(350);
        plateformes = new LinkedList < > ();
        for (int i = 0; i < 5; i++) {
            ajouterPlateforme(Math.random() * 350, i * -100 + 480);
        }

        for (Plateforme plateforme: plateformes) {
            plateforme.update(0, fenetreY);
        }

        ajouterBulles();
    }


    /**
     * Ajoute les bulles
     */
    public void ajouterBulles() {
        bulles = new ArrayList < > ();
        for (int i = 0; i < groupe; i++) {
            double baseX = (Math.random() * width) + 0; // prend une baseX random pour chaque groupe
            for (int j = 0; j < nbrBulle; j++) {
                double x = (Math.random() * baseX + 20) + baseX - 20;
                if (x > 0 || x < 350) { //ajout des bulles seulement si dans [0-350] pour x
                    bulles.add(new Bulles(x)); //
                }
            }
        }
    }


    /**
     *
     * ajout des platefomes aleatoire avec les probabilité
     * @param x
     * @param y
     */
    public void ajouterPlateforme(double x, double y) {
        double random = Math.random();
        if (random < 0.65) {
            plateformes.add(new PlateformeSimple(x, y));
        }
        if (random >= 0.65 && random < 0.85) {
            plateformes.add(new PlateformeRebondissante(x, y));
        }
        if (random >= 0.85 && random < 0.95) {
            plateformes.add(new PlateformeAccelerante(x, y));
        }
        if (random >= 0.95 && random < 1.0) {
            plateformes.add(new PlateformeSolide(x, y));
        }
    }


    //appel les méthodes de meduse pour changer vitesse et accélération
    public void haut() {
        meduse.haut();
    }
    public void gauche() {
        meduse.gauche();
    }
    public void droit() {
        meduse.droit();
    }
    public void arret() {
        meduse.arret();
    }


    /**
     * update le tempstotal et ajoute les entités au moment de
     * tempstotal demandé
     * appel update des chaque entité pour chaque fram
     * @param dt
     */
    public void update(double dt) {
        meduse.setParterre(false);
        tempsTotal += dt;
        vFenetreY = 50 + tempsTotal * aFenetreY;
        if (fenetreY > -50) {
            plateformeInitiale.update(dt, fenetreY);
            vFenetreY = plateformeInitiale.testCollision(meduse, fenetreY, vFenetreY);
        }
        //vFenetreY défini par la testCollision pour que l'effet de plateformeAccelerante fonctionne
        for (Plateforme plateforme: plateformes) {
            plateforme.update(dt, fenetreY);
            vFenetreY = plateforme.testCollision(meduse, fenetreY, vFenetreY);
        }
        if (tempsTotal > sec) {
            this.sec += 3;
            ajouterBulles();
        }
        for (Bulles b: bulles) {
            b.update(dt, fenetreY);
        }

        if (plateformes.peek().y > fenetreY + height + 50) {
            ajouterPlateforme(Math.random() * 350, plateformes.poll().y - 500);
        }
        meduse.update(dt, fenetreY);
        fenetreY -= dt * vFenetreY;
        if (meduse.y < 120) {
            fenetreY -= 10;
            meduse.y += 10;
        }
    }


    /**
     *
     * @return redémarre le jeu en fonction de la position de méduse
     */
    public boolean restart() {
        return meduse.y > 480;
    }


    /**
     * Similaire que update mais pour mode débug
     * @param dt
     */
    public void debugUpdate(double dt) {
        meduse.setParterre(false);
        tempsTotal += dt;
        deltaTemps = dt;
        vFenetreY = 0;
        aFenetreY = 0;
        if (fenetreY > -50) {
            plateformeInitiale.update(dt, fenetreY);
            plateformeInitiale.testCollision(meduse, fenetreY, vFenetreY);
        }
        for (Plateforme plateforme: plateformes) {
            plateforme.update(dt, fenetreY);
            plateforme.testCollision(meduse, fenetreY, vFenetreY);
        }
        if (tempsTotal > sec) {
            this.sec += 3;
            ajouterBulles(); // utilise la meme baseX pour ajoutter chque bulles
        }
        for (Bulles b: bulles) {
            b.update(dt, fenetreY);
        }

        if (plateformes.peek().y > fenetreY + height + 50) {
            ajouterPlateforme(Math.random() * 350, plateformes.poll().y - 500);
        }
        meduse.update(dt, fenetreY);
        if (meduse.y < 120) {
            fenetreY -= 10;
            meduse.y += 10;
        }
    }


    /**
     * Pour dessiner les entités sur le canvas
     * @param context
     */
    public void draw(GraphicsContext context) {
        meduse.draw(context, fenetreY);
        plateformeInitiale.draw(context, fenetreY);

        Iterator < Plateforme > iterator = plateformes.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw(context, fenetreY);
        }

        for (Bulles r: bulles) {
            r.draw(context, fenetreY);
        }

        context.setFill(Color.WHITE);
        context.setFont(Font.font(30));
        context.fillText((int) - fenetreY + "m", 140, 50);
    }

    /**
     * Similaire que draw mais pour mode débug
     * @param context
     */
    public void debugDraw(GraphicsContext context) {
        meduse.debugDraw(context, fenetreY);
        plateformeInitiale.debugDraw(context, meduse, fenetreY);

        Iterator < Plateforme > iterator = plateformes.iterator();
        while (iterator.hasNext()) {
            iterator.next().debugDraw(context, meduse, fenetreY);
        }

        for (Bulles r: bulles) {
            r.draw(context, fenetreY);
        }

        context.setFill(Color.WHITE);
        context.setFont(Font.font(30));
        context.fillText((int) - fenetreY + "m", 140, 50);
        context.setFill(Color.WHITE);
        context.setFont(Font.font(10));
        context.fillText("position = (" + (int) meduse.x + ", " + (int) meduse.y + ")", 5, 15);
        context.fillText("v = (" + (int) meduse.vx + ", " + (int) -(meduse.vy - 1200*deltaTemps) + ")", 5, 30);
        context.fillText("a = (" + (int) meduse.ax + ", " + -(int) meduse.ay + ")", 5, 45);
        if (meduse.getParterre()) {
            context.fillText("Touche le sol : oui", 5, 60);
        } else {
            context.fillText("Touche le sol : non", 5, 60);
        }
    }
}