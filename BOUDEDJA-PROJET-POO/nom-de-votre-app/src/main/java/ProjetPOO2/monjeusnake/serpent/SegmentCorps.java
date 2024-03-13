package ProjetPOO2.monjeusnake.serpent;

import ProjetPOO2.monjeusnake.Point;

public class SegmentCorps extends Segment {

    public SegmentCorps(Point position) {
        super(position);
    }

    @Override
    public void deplacerVers(Point nouvellePosition) {

        setPosition(nouvellePosition);

    }

    public void deplacer(Segment segmentPrecedent) {
        deplacerVers(segmentPrecedent.getPosition());
    }

    @Override
    public void deplacer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deplacer'");
    }
}
