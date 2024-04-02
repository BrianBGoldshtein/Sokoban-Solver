public class Location {
    private int[] locs;

    public Location(int row, int col) {
        locs = new int[2];
        setRow(row);
        setCol(col);
    }

    public int getRow() {
        return locs[0];
    }
    public int getCol() {
        return locs[1];
    }
    public void setRow(int r) {
        locs[0] = r;
    }
    public void setCol(int c) {
        locs[1] = c;
    }

    public Location clone() {
        return new Location(this.getRow(), this.getCol());
    }


    public Location getNewLoc(Direction direction) {
        Location targetLoc = this.clone();

        switch(direction) {
            case UP: targetLoc.setRow(targetLoc.getRow() - 1); break;
            case DOWN: targetLoc.setRow(targetLoc.getRow() + 1); break;
            case RIGHT: targetLoc.setCol(targetLoc.getCol() + 1); break;
            case LEFT: targetLoc.setCol(targetLoc.getCol() - 1); break;
        }

        return targetLoc;
    }

    public boolean equals(Location other) {
        return getRow() == other.getRow() && getCol() == other.getCol();
    }

    public String toString() {
        return getRow() + ", " + getCol();
    }
}
