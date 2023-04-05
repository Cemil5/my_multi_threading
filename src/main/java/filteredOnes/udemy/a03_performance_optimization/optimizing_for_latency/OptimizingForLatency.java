package filteredOnes.udemy.a03_performance_optimization.optimizing_for_latency;
/*
 * MIT License
 *
 * Copyright (c) 2019 Michael Pogrebinsky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Optimizing for Latency Part 2 - Image Processing
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
class OptimizingForLatency {
    //    public static final String SOURCE_FILE = System.getProperty("user.dir") + "/src/main/java/filteredOnes/udemy/performance_optimization/optimizing_for_latency/many-flowers.jpg";
    public static final String SOURCE_FILE = "./src/main/java/filteredOnes/udemy/performance_optimization/optimizing_for_latency/many-flowers.jpg";
    public static final String DESTINATION_FILE = System.getProperty("user.dir") + "/src/main/java/filteredOnes/udemy/performance_optimization/optimizing_for_latency/result.jpg";

    public static void main(String[] args) throws IOException {
//        BufferedReader file = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/java/restaurantSim/data2.txt"));
//        System.out.println(file.readLine());

        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
//        recolorSingleThreaded(originalImage, resultImage);
        int numberOfThreads = 1;
        recolorMultithreaded(originalImage, resultImage, numberOfThreads);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.valueOf(duration));
    }

    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int xOrigin = 0;
                int yOrigin = height * threadMultiplier;

                recolorImage(originalImage, resultImage, xOrigin, yOrigin, width, height);
            });

            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    /*
We perform the opposite of color component extraction. We take each component and shift it to the right place in the ARGB pixel representation.
Blue is placed at the lowest byte so we simply bitwise OR the pixel color representation with the blue component
Green needs to be placed at the second byte, so it is first shifted 1 byte (8 bits) to the left, and then is bitwise ORed with the pixel color
Similarly, red needs to be placed at the third byte so its component is shifted 2 bytes (16 bits) to the left, and then it is bitwise ORed with the pixel color

The final step is to set the transparency level to the highest, making the color completely opaque (0 levels mean fully transparent, 255 means fully opaque).
That is achieved by setting the left-most byte, representing the alpha component to 0xFF which is 1111 1111 in binary.
     */
    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;    // logical OR
        rgb |= green << 8;  // logical OR and bit left shift
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

/*
In order to get a particular component (red, green, or blue), we need to first get rid of all the other color components in the pixel,
while keeping the desired component.
To achieve this we apply a bitmask.
A bitmask defines which bits we want to keep, and which bits we want to clear.
We apply a bitwise AND with 0x00 (0000 0000 in binary) to get rid of a component since X AND 0 = 0, for any X.
We apply a bitwise AND with 0xFF (1111 1111 in binary) to keep the value of a component since X AND 1 = X, for any X.

However, after applying a bitmask we are not done. We still need to shift the byte representing our component to the lowest byte.
For example in the getRed(..) method, after we apply the bitmask on 0x76543210 we end up with 0x00540000, but what we need is 0x00000054
So we need to shift all the bits in the result of the bitmask to the right., using the >> operator.
 */

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;    // bit right shift
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
