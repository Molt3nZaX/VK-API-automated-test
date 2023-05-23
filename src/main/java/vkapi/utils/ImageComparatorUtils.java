package vkapi.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static aquality.selenium.browser.AqualityServices.getLogger;

public class ImageComparatorUtils {
    public static float comparePicture(byte[] actualPicture, String pathToExpectedPicture) {
        getLogger().info("Compare 2 pictures.");
        BufferedImage actualImage;
        try {
            InputStream inputStream = new ByteArrayInputStream(actualPicture);
            actualImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            getLogger().error("File (picture) could not be read");
            throw new RuntimeException(e);
        }
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(pathToExpectedPicture);
        ImageComparison imageComparisonResult = new ImageComparison(expectedImage, ImageComparisonUtil.toBufferedImage(actualImage));
        ImageComparisonResult result = imageComparisonResult.compareImages();
        float differencePercent = result.getDifferencePercent();
        getLogger().info("Difference percent =" + differencePercent);
        return differencePercent;
    }
}