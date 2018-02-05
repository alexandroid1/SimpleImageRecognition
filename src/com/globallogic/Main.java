package com.globallogic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        verticalSort();
        return;
    }

    private static void verticalSort() throws IOException {
        BufferedImage image = ImageIO.read(Main.class.getResource("lines.png"));
        WritableRaster raster = image.getRaster();

        for (int i = 0; i < raster.getWidth(); i++) {
            linesSortByHeight(raster, i);
        }

        image.setData(raster);
        ImageIO.write(image, "png", new File("linesVerticalSorted.png"));
    }

    private static void linesSortByHeight(WritableRaster raster, int i) {

        java.util.List<Integer> pixel0 = new ArrayList<>();
        java.util.List<Integer> pixel1 = new ArrayList<>();
        java.util.List<Integer> pixel2 = new ArrayList<>();


        for (int j = 0; j < raster.getHeight(); j++) {

            int[] pixel = raster.getPixel(i, j, new int[4]);

            pixel0.add(pixel[0]);
            pixel1.add(pixel[1]);
            pixel2.add(pixel[2]);
        }

        Collections.sort(pixel0);
        Collections.sort(pixel1);
        Collections.sort(pixel2);


        for (int j = 0; j < raster.getHeight(); j++) {

            int[] pixel = raster.getPixel(i, j, new int[4]);

            pixel[0] = pixel0.get(j);
            pixel[1] = pixel1.get(j);
            pixel[2] = pixel2.get(j);

            raster.setPixel(i,j, pixel);
        }
    }
}
