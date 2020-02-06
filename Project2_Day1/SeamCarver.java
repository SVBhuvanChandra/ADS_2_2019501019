import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/**
 * Impelementing the SeamCarver class.
 * @author SV Bhuvan Chandra
 */
public class SeamCarver {
    /**
     * Picture object.
     */
    private Picture p;
    /**
     * Field to store the energies of the pixels.
     */
    private double[][] eArray;
    // create a seam carver object based on the given picture
    /**
     * Consturctor of SeamCarver.
     * @param picture .png file as input.
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            // throws exception.
            throw new IllegalArgumentException();
        }
        // this.p = picture;
        p = new Picture(picture);
    }
 
    // current picture
    /**
     * This method return a picture.
     * @return picture.
     */
    public Picture picture() {
        // return p;
        return new Picture(p);
    }
 
    // width of current picture
    /**
     * Method to find the width of the picture.
     * @return width.
     */
    public int width() {
        return p.width();
    }

    // height of current picture
    /**
     * Mehtod to find the height of the picture.
     * @return height.
     */
    public int height() {
        return p.height();
    }
    /**
     * This method is to find the X gradient of a pixel by using the RGB values.
     * @param x adjecent energy value
     * @param y adjecent energy value
     * @return calculated Gardient value of X horizantal axis.
     */
    private double xGradient(int x, int y) {
        Color x1 = p.get(x - 1, y);
        double r1 = x1.getRed();
        double g1 = x1.getGreen();
        double b1 = x1.getBlue();
        
        Color x2 = p.get(x + 1, y);
        double r2 = x2.getRed();
        double g2 = x2.getGreen();
        double b2 = x2.getBlue();

        double rX = Math.pow((r1 - r2), 2);
        double gX = Math.pow((g1 - g2), 2);
        double bX = Math.pow((b1 - b2), 2);

        double xG = rX + gX + bX;
        return xG;
    }
    /**
     *  This method is to find the Y gradient of a pixel by using the RGB values.
     * @param x adjecent energy value
     * @param y adjecent energy value
     * @return calculated Gardient value of X Vertical axis.
     */
    private double yGradient(int x, int y) {
        Color y1 = p.get(x, y - 1);
        double r1 = y1.getRed();
        double g1 = y1.getGreen();
        double b1 = y1.getBlue();
        
        Color y2 = p.get(x, y + 1);
        double r2 = y2.getRed();
        double g2 = y2.getGreen();
        double b2 = y2.getBlue();

        double rY = Math.pow((r1 - r2), 2);
        double gY = Math.pow((g1 - g2), 2);
        double bY = Math.pow((b1 - b2), 2);

        double yG = rY + gY + bY;
        return yG;
    }

    // energy of pixel at column x and row y
    /**
     * Method to find the energy of the pixel.
     * @param x adjecent pixel index
     * @param y adjecent pixel index
     * @return calculated energy.
     */
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            // Throws exception.
            throw new IllegalArgumentException();
        }
        if (x == 0 || y == 0 || x == width()-1 || y == height() - 1) {
            return 1000.0;
        }
        else {
            return Math.sqrt(xGradient(x, y) + yGradient(x, y));
        } 
    }

    // This method helps us set the energy matrix
    /**
     * Method to store the energies of the adject pixcels in a 2D matrix.
     */
    private void energyMatrix() {
        /**
         * initial energy value.
         */
        double en = 1000.0;
        /**
         * Field to store the energies as a matrix.
         */
        eArray = new double[height()][width()];

        for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				if (i == 0 || i == height() && j == 0 || j == width()) {
				eArray[i][j] = en;
				}
				eArray[i][j] = energy(j, i);
			}
		}
	}
    /**
     * Method to find the best path using topoligical sorting method.
     * @param ene energy matrix.
     */
	private void topologicalSort(double[][] ene) {
        /**
         * Height of the matrix.
         */
        int hei = ene.length;
        /**
         * Width of the matrix.
         */
        int wid = ene[0].length;
        // System.out.println("Printing :::: "+hei+":::"+wid);
		for (int i = 1; i < hei; i++) {
		   for (int j = 0; j < wid; j++) {
			  if (j == 0) {
				 ene[i][j] += ene[i-1][j];
			  } else if (j == wid - 1) {
				 ene[i][j] += Math.min(ene[i - 1][j], ene[i - 1][j - 1]);
			  } else {
				 double m1 = Math.min(ene[i - 1][j - 1], ene[i - 1][j]);
				 double m2 = Math.min(m1, ene[i - 1][j+1]);
				 ene[i][j] += m2;
			  }
		   }
		}
	}
	/**
     * Method to find the seam path.
     * @param eneM energy matrix.
     * @return array of indices of the pixcels.
     */
	private int[] seamVerticalPath(double[][] eneM) {
        /**
         * Heigh of the matrix.
         */
        int hi = eneM.length;
        /**
         * Width of the matrix.
         */
        int wi = eneM[0].length;
        /**
         * Filed to store the indices as an array.
         */
        int[] seamPath = new int[hi];
		topologicalSort(eneM);
        seamPath[hi - 1] = 0;
        
		for (int i = 0; i < wi; i++) {
			if (eneM[hi - 1][i] < eneM[hi - 1][seamPath[hi - 1]]) {
                seamPath[hi - 1] = i;
            }
        }
        
		for (int rr = hi - 2; rr >= 0; rr--) {
			int cc = seamPath[rr + 1];
			seamPath[rr] = cc;
			if (cc > 0 && eneM[rr][cc - 1] < eneM[rr][seamPath[rr]]) {
                seamPath[rr] = cc - 1;
            }
			if (cc < (wi - 2) && eneM[rr][cc + 1] < eneM[rr][seamPath[rr]]) {
                seamPath[rr] = cc + 1;
            }
		}
		return seamPath;
	}
 
    // sequence of indices for vertical seam
    /**
     * Method to find the Vertical seam of the picture.
     * @return array of indices as seam.
     */
    public int[] findVerticalSeam() {
		energyMatrix();
		double[][] array = eArray;
		return seamVerticalPath(array);
	}

    // sequence of indices for horizontal seam
    /**
     * Method to find the Horizantal seam of the picture.
     * @return array of indices as seam.
     */
    public int[] findHorizontalSeam() {
		energyMatrix();
        double[][] array = new double[width()][height()];
        // System.out.println("width is : "+width()+" Height is : "+height());
        // Performing Matrix transpose.
		for (int i = 0; i < width(); i++) { 
			for (int j = 0; j < height(); j++) {
			   array[i][j] = energy(i, j);
			}
		}
		return seamVerticalPath(array);
	}


    // remove horizontal seam from current picture
    /**
     * Method to remove the horizantal seam of the picture.
     * @param seam input seam as an array.
     */
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null || height() <= 1 || seam.length != width() || seam[0] < 0 || seam[0] > height() - 1) {
            // throws exceptions.
		    throw new IllegalArgumentException();
		}
		for (int i = 0; i < seam.length - 1; i++) {
			if (seam[i] < 0 || seam[i + 1] < 0 || seam[i] > height() - 1 || seam[i + 1] > width() - 1) {
                // throws exception.
				throw new IllegalArgumentException();
			}
			if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                // throws exception
				throw new IllegalArgumentException();
			}
        }
        /**
         * New picture object.
         */
		Picture newPic = new Picture(width(), height() - 1);
		for (int w = 0; w < width(); w++) {
			for (int h = 0; h < seam[w]; h++) {
				newPic.set(w, h, p.get(w, h));
			}

			for (int h = seam[w] + 1; h < height(); h++) {
				newPic.set(w, h - 1, p.get(w, h));
			}

		}
		p = newPic;
	}

    // remove vertical seam from current picture
    /**
     * Method to remove the vertical seam of the picture.
     * @param seam input seam as an array.
     */
	public void removeVerticalSeam(int[] seam) {
		if (seam == null || width() <= 1 || seam.length != height() || seam[0] < 0 || seam[0] > width() - 1) {
            // throws exception.
		    throw new IllegalArgumentException();
		}
		for (int i = 0; i < seam.length - 1; i++) {
			if (seam[i] < 0 || seam[i + 1] < 0 || seam[i] > width() - 1 || seam[i + 1] > width() - 1) {
                // throws exception.
				throw new IllegalArgumentException();
			}
			if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                // throws exception.
				throw new IllegalArgumentException();
			}
        }
        /**
         * New picture objcet.
         */
		Picture newPic = new Picture(width() - 1, height());

		for (int h = 0; h < height(); h++) {
			for (int w = 0; w < seam[h]; w++) {
				newPic.set(w, h, p.get(w, h));
			}
			for (int w = seam[h] + 1; w < width(); w++) {
				newPic.set(w - 1, h, p.get(w, h));
			}
		}
		p = newPic;
    }
    //  unit testing (optional)
    /**
     * Impelementing main method.
     * @param args input arguments from terminal.
     */
    public static void main(String[] args) {
        // main method.
     }
 }