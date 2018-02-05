package com.globallogic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(Main.class.getResource("lines.png"));

        WritableRaster raster = image.getRaster();
        
        for (int i = 0; i < raster.getWidth(); i++) {
            for (int j = 0; j < raster.getHeight(); j++) {

                int[] pixel = raster.getPixel(i, j, new int[4]);

                // condition
                if ( (pixel[0] < 255)||(pixel[1] < 255)||(pixel[2] < 255) ) {
                    pixel[0] = 255;
                    pixel[1] = 0;
                    pixel[2] = 0;
                }

                raster.setPixel(i,j, pixel);
            }

        }

        image.setData(raster);
        ImageIO.write(image, "png", new File ("linesOutput.png"));

    }
}
