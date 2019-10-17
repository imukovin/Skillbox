import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class ImageResizer extends Thread {
    private static final String OUT_FOLDER = "outFolder/";
    private static final int SIZE_FIRST_CHANGE_W = 600;
    private static final int SIZE_SECOND_CHANGE_W = 300;

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

                int size = 0;
                if (image.getWidth() > image.getHeight()) {
                    size = image.getWidth();
                } else {
                    size = image.getHeight();
                }

                int firstChangeHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) SIZE_FIRST_CHANGE_W));

                double trans=1.0/(size/SIZE_FIRST_CHANGE_W);

                AffineTransform tr = new AffineTransform ();
                tr.scale(trans, trans);
                AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                Double w = (double) SIZE_FIRST_CHANGE_W;
                Double h = (double) firstChangeHeight;
                BufferedImage image2 = new BufferedImage(w.intValue(), h.intValue(), image.getType());
                op.filter(image, image2);

                if (image2.getWidth() > image2.getHeight()) {
                    size = image2.getWidth();
                } else {
                    size = image2.getHeight();
                }

                double trans1=1.0/(size/SIZE_SECOND_CHANGE_W);
                int secondChangeHeight = (int) Math.round(image2.getHeight() / (image2.getWidth() / (double) SIZE_SECOND_CHANGE_W));

                AffineTransform tr1 = new AffineTransform ();
                tr1.scale(trans1, trans1);
                AffineTransformOp op1 = new AffineTransformOp(tr1, AffineTransformOp.TYPE_BICUBIC);
                Double w1 = (double) SIZE_SECOND_CHANGE_W;
                Double h1 = (double) secondChangeHeight;
                BufferedImage image3 = new BufferedImage(w1.intValue(), h1.intValue(), image2.getType());
                op1.filter(image2, image3);

                try {
                    File newFile = new File(OUT_FOLDER + file.getName());
                    ImageIO.write(image3, "jpg", newFile);
                } catch (IOException e) {

                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Время: " + (System.currentTimeMillis() - start));
    }
}
