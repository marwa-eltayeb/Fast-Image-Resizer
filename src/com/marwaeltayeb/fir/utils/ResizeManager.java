package com.marwaeltayeb.fir.utils;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResizeManager {

    public static void resizeImages(List<File> originalImages, String outputDirectory , int width, int height, String drawableDirectory) {

        String directory = drawableDirectory;
        if(drawableDirectory == null || drawableDirectory.isEmpty()){
            directory = "drawable";
        }

        try {
            for (File originalImage : originalImages) {
                BufferedImage origBuffImg = ImageIO.read(originalImage);
                int type = origBuffImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : origBuffImg.getType();

                BufferedImage resizedBuffImg = new BufferedImage(width, height, type);
                Graphics2D g = resizedBuffImg.createGraphics();
                g.drawImage(origBuffImg, 0, 0, width, height, null);
                g.dispose();

                String finalPath;
                if (directory.equalsIgnoreCase("mipmap")) {
                    finalPath = outputDirectory + "/res-" + directory + "/mipmap-" + width + "x" + height + "/";
                } else {
                    finalPath = outputDirectory + "/res-" + directory + "/drawable-" + width + "x" + height + "/";
                }

                Path path = Paths.get(finalPath);
                // Create folders inside folder
                Files.createDirectories(path);

                String fileNameWithOutExt = originalImage.getName().substring(0, originalImage.getName().lastIndexOf("."));
                String newFile = finalPath + fileNameWithOutExt + "_" + width + "x" + height + "." + getExtension(originalImage.getAbsolutePath());
                ImageIO.write(resizedBuffImg, getExtension(originalImage.getAbsolutePath()), new File(newFile));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(String f) {
        String extension = "";

        int i = f.lastIndexOf('.');
        if (i > 0) {
            extension = f.substring(i + 1);
        }
        return extension.toLowerCase();
    }
}
