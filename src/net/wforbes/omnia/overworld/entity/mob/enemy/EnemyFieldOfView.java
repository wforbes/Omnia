package net.wforbes.omnia.overworld.entity.mob.enemy;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class EnemyFieldOfView {
/*
*fd: direção para a qual o inimigo está olhando.
*fovRange: alcance do campo de visão.
*fovDEdges: ângulos que delimitam o campo de visão.
*fovEdgePoints: pontos extremos do campo de visão.
*fovPolyPoints: vértices do polígono utilizado para detectar o jogador
*/
    private int fd;
    private double xFP;
    private double yFP;

    
    private double[] fovQEdges = new double[4];
    private int[] fovDEdges = new int[2];

    // alcance do campo de visao
    private double fovRange;

    private double[] fovEdgeRadians = new double[2];
    private Point2D[] fovEdgePoints = new Point2D[2];

    private Line fovLine;
    private Line[] fovEdgeLines = new Line[2];

    private Point2D[] fovPolyPoints = new Point2D[4];

    public EnemyFieldOfView(double fovRange) {
        this.fovRange = fovRange;
    }
/* 
reinicializa as variáveis utilizadas no cálculo do campo de visão
é necessário porque o campo de visão é recalculado conforme o inimigo muda de posição ou direção.
*/


    private void clearFOVVars() {
    this.xFP = 0.0;
    this.yFP = 0.0;

    this.fovQEdges[0] = 0.0;
    this.fovQEdges[1] = 0.0;
    this.fovQEdges[2] = 0.0;
    this.fovQEdges[3] = 0.0;

    this.fovDEdges[0] = 0;
    this.fovDEdges[1] = 0;

    this.fovLine = new Line();

    this.fovEdgeRadians[0] = 0;
    this.fovEdgeRadians[1] = 0;

    this.fovEdgePoints[0] = new Point2D(0, 0);
    this.fovEdgePoints[1] = new Point2D(0, 0);

    this.fovEdgeLines[0] = new Line();
    this.fovEdgeLines[1] = new Line();
}

// responsável por calcular o campo de visão do inimigo
public void setAggroFOV(int facingDir, double x, double y,
                       double xmap, double ymap) {

    this.fd = facingDir;
    this.clearFOVVars();

    switch (this.fd) {
        case 0: // Norte
            yFP = -fovRange;
            fovDEdges[0] = 150;
            fovDEdges[1] = 210;
            break;

        case 1: // Sul
            yFP = fovRange;
            fovDEdges[0] = 30;
            fovDEdges[1] = 330;
            break;

        case 2: // Oeste
            xFP = -fovRange;
            fovDEdges[0] = 240;
            fovDEdges[1] = 300;
            break;

        case 3: // Leste
            xFP = fovRange;
            fovDEdges[0] = 60;
            fovDEdges[1] = 120;
            break;

        default:
            System.out.println("error on fd switch");
            break;
    }

    double offset = fovQEdges[0] + fovQEdges[1]
                  + fovQEdges[2] + fovQEdges[3];

    this.fovLine = new Line(
        x + xmap,
        y + ymap,
        x + xmap + xFP + offset,
        y + ymap + yFP + offset
    );

    this.fovEdgeRadians[0] = fovDEdges[0] * (Math.PI / 180);

    this.fovEdgePoints[0] = new Point2D(
        x + Math.sin(fovEdgeRadians[0]) * fovRange,
        y + Math.cos(fovEdgeRadians[0]) * fovRange
    );

    this.fovEdgeLines[0] = new Line(
        x + xmap,
        y + ymap,
        fovEdgePoints[0].getX(),
        fovEdgePoints[0].getY()
    );

    this.fovEdgeRadians[1] = fovDEdges[1] * (Math.PI / 180);

    this.fovEdgePoints[1] = new Point2D(
        x + Math.sin(fovEdgeRadians[1]) * fovRange,
        y + Math.cos(fovEdgeRadians[1]) * fovRange
    );

    this.fovEdgeLines[1] = new Line(
        x + xmap,
        y + ymap,
        fovEdgePoints[1].getX(),
        fovEdgePoints[1].getY()
    );

    this.fovPolyPoints = new Point2D[4];

    this.fovPolyPoints[0] = new Point2D(
        fovLine.getStartX() - xmap,
        fovLine.getStartY() - ymap
    );

    this.fovPolyPoints[1] = new Point2D(
        fovEdgePoints[0].getX(),
        fovEdgePoints[0].getY()
    );

    this.fovPolyPoints[2] = new Point2D(
        fovLine.getEndX() - xmap,
        fovLine.getEndY() - ymap
    );

    this.fovPolyPoints[3] = new Point2D(
        fovEdgePoints[1].getX(),
        fovEdgePoints[1].getY()
    );
}


// implementa o algoritmo Ray Casting, utilizado para verificar se um ponto está dentro de um polígono.
public boolean contains(Point2D point) {
    boolean result = false;

    for (int i = 0, j = fovPolyPoints.length - 1;
         i < fovPolyPoints.length; j = i++) {

        Point2D current = fovPolyPoints[i];
        Point2D previous = fovPolyPoints[j];

        if ((current.getY() > point.getY()) !=
            (previous.getY() > point.getY()) &&

            point.getX() <
                (previous.getX() - current.getX()) *
                (point.getY() - current.getY()) /
                (previous.getY() - current.getY()) +
                current.getX()) {

            result = !result;
        }
    }

    return result;
}

    public double getYFP() {
    return yFP;
}

    public double getFovQEdgeSum() {
    return fovQEdges[0] + fovQEdges[1]
         + fovQEdges[2] + fovQEdges[3];
}

    public Line getFovLine() {
    return fovLine;
}

    public Point2D getFovEdgePoint(int index) {
    return fovEdgePoints[index];
}

    public Point2D getFovPolyPoint(int index) {
        return fovPolyPoints[index];
}

public double getFovQEdge(int index) {
    return fovQEdges[index];
}
        
}
