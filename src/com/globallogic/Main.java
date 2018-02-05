package com.globallogic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static int min;
    static int max;

    public static void main(String[] args) throws IOException {
        verticalSort();
        findBrightnessBorders();
        linesExtracted();


        return;
    }

    private static void linesExtracted() throws IOException {
        File file = new File ("lines.png");
        BufferedImage image = ImageIO.read(file);
        WritableRaster raster = image.getRaster();

        for (int i = 0; i < raster.getWidth(); i++) {
            for (int j = 0; j < raster.getHeight(); j++) {

                int[] pixel = raster.getPixel(i, j, new int[4]);
                if ( pixel[0]+pixel[1]+pixel[2] < max-min/2 ) {
                    pixel[0] = 255;
                    pixel[1] = 0;
                    pixel[2] = 0;

                    raster.setPixel(i,j, pixel);
                }
            }
        }

        image.setData(raster);
        ImageIO.write(image, "png", new File("linesExtracted.png"));
    }

    private static void findBrightnessBorders() throws IOException {
        File file = new File ("linesVerticalSorted.png");
        BufferedImage image = ImageIO.read(file);
        WritableRaster raster = image.getRaster();

        List<Integer> pixel0 = new ArrayList<>();
        List<Integer> pixel1 = new ArrayList<>();
        List<Integer> pixel2 = new ArrayList<>();

        findMinMaxBrigth(raster, pixel0, pixel1, pixel2);

        min = pixel0.get(0) + pixel1.get(0) + pixel2.get(0);
        max = pixel0.get(raster.getWidth()-1) + pixel1.get(raster.getWidth()-1) + pixel2.get(raster.getWidth()-1);

        System.out.println("min = " + min);
        System.out.println("max = " + max);
    }

    private static void findMinMaxBrigth(WritableRaster raster, List<Integer> pixel0, List<Integer> pixel1, List<Integer> pixel2) {
        for (int i = 0; i < raster.getWidth(); i++) {
            int[] pixel = raster.getPixel(i, 0, new int[4]);
            pixel0.add(pixel[0]);
            pixel1.add(pixel[1]);
            pixel2.add(pixel[2]);
        }

        Collections.sort(pixel0);
        Collections.sort(pixel1);
        Collections.sort(pixel2);
    }

    private static void verticalSort() throws IOException {
        File file = new File ("lines.png");
        BufferedImage image = ImageIO.read(file);
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
