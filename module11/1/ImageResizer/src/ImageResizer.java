import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {
    private static final String OUT_FOLDER = "outFolder/";
    private static final int OUTPUT_WIDTH = 300;
    private static final int MEDIUM_WIDTH = OUTPUT_WIDTH * 2;

    private File file;

    ImageResizer(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = ImageIO.read(file);

            BufferedImage firstStepScale = getNewImage(image, AffineTransformOp.TYPE_NEAREST_NEIGHBOR, MEDIUM_WIDTH);
            BufferedImage outputImage = getNewImage(firstStepScale, AffineTransformOp.TYPE_BICUBIC, OUTPUT_WIDTH);

            File newFile = new File(OUT_FOLDER + file.getName());
            ImageIO.write(outputImage, "jpg", newFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Thread num: " + Thread.currentThread().getName());
    }

    private BufferedImage getNewImage(BufferedImage image, int method, int width) {
        int size = Math.max(image.getWidth(), image.getHeight());
        int height = (int) Math.round(image.getHeight() / (image.getWidth() / (double) width));
        double trans = 1.0 / ((double) size / width);

        AffineTransform tr = AffineTransform.getScaleInstance(trans, trans);
        AffineTransformOp op = new AffineTransformOp(tr, method);

        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        op.filter(image, newImage);

        return newImage;
    }
}