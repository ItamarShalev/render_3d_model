package renderer;

import com.playking.primitives.Color;
import com.playking.renderer.ImageWriter;
import org.junit.jupiter.api.Test;

/**
 * Testing ImageWriter Class.
 */
public class ImageWriterTests {

    /**
     * Test method for {@link ImageWriter#writePixel(int, int, Color)}.
     * Simple test to create image with yellow-red image matrix.
     */
    @Test
    public void testWritePixel() {
        int nx = 800;
        int ny = 500;
        int rows = 16;
        int columns = 10;
        boolean isOnTheLine = true;
        ImageWriter imageWriter = new ImageWriter("ImageBasic", nx, ny);

        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                isOnTheLine = i % (nx / rows) == 0 || j % (ny / columns) == 0;
                if (isOnTheLine) {
                    imageWriter.writePixel(j, i, Color.RED);
                } else {
                    imageWriter.writePixel(j, i, Color.YELLOW);
                }
            }
        }
        imageWriter.writeToImage();
    }
}
