import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer extends Thread {
    private static final String OUT_FOLDER = "outFolder/";
    private static final int OUTPUT_WIDTH = 300;
    private static final int MEDIUM_WIDTH = OUTPUT_WIDTH * 2;

    private File[] files;

    ImageResizer(File[] files) {
        this.files = files;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }

                BufferedImage firstStepScale = getNewImage(image, 1, MEDIUM_WIDTH);
                BufferedImage outputImage = getNewImage(firstStepScale, 2, OUTPUT_WIDTH);

                File newFile = new File(OUT_FOLDER + file.getName());
                ImageIO.write(outputImage, "jpg", newFile);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Время: " + (System.currentTimeMillis() - start));
    }

    private BufferedImage getNewImage(BufferedImage image, int numOfMethod, int width) {
        int size = Math.max(image.getWidth(), image.getHeight());
        int height = (int) Math.round(image.getHeight() / (image.getWidth() / (double) width));
        double trans = 1.0 / (size / width);

        AffineTransform tr = AffineTransform.getScaleInstance(trans, trans);
        AffineTransformOp op;
        if (numOfMethod == 1) {
            op = new AffineTransformOp(tr, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        } else {
            op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
        }

        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        op.filter(image, newImage);

        return newImage;
    }
}