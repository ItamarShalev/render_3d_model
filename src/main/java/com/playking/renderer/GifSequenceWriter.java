package com.playking.renderer;


import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;


/**
 * Helper to write sequence of images as a gif.
 */
public class GifSequenceWriter {

    protected ImageWriter writer;
    protected ImageWriteParam params;
    protected IIOMetadata metadata;


    public GifSequenceWriter(String outPath, int delay, boolean loop) throws IOException {
        writer = ImageIO.getImageWritersBySuffix("gif").next();
        params = writer.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(
            TYPE_3BYTE_BGR);
        metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

        configureRootMetadata(delay, loop);

        writer.setOutput(new FileImageOutputStream(new File(outPath)));
        writer.prepareWriteSequence(null);
    }

    public GifSequenceWriter(String outPath) throws IOException {
        this(outPath, 0, true);
    }

    /**
     * Convert images sequence to gif file.
     * @param outPath where to save the gif
     * @param imagesPath all the images to convert to gif
     * @param delay in ms
     * @param loop if the gif will be in loop
     * @throws IOException if some image doesn't exist
     */
    public static void convertImagesFromSequence(String outPath, int delay, boolean loop,
                                                 String... imagesPath) throws IOException {
        if (imagesPath == null || imagesPath.length == 0) {
            return;
        }
        GifSequenceWriter writer = new GifSequenceWriter(outPath, delay, loop);
        for (String imagePath : imagesPath) {
            writer.writeToSequence(imagePath);
        }
        writer.close();
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return (IIOMetadataNode)rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return (node);
    }

    private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
        commentsNode.setAttribute("CommentExtension", "Created by: https://memorynotfound.com");

        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");

        int loopContinuously = loop ? 0 : 1;
        child.setUserObject(new byte[] {
            0x1, (byte)(loopContinuously & 0xFF), (byte)((loopContinuously >> 8) & 0xFF)
        });
        appExtensionsNode.appendChild(child);
        metadata.setFromTree(metaFormatName, root);
    }

    /**
     * Write single image to the gif.
     * @param image the image to write
     * @throws IOException if the image isn't exist
     */
    public void writeToSequence(RenderedImage image) throws IOException {
        writer.writeToSequence(new IIOImage(image, null, metadata), params);
    }

    /**
     * Write single image to the gif.
     * @param imagePath the image to write
     * @throws IOException if the image isn't exist
     */
    public void writeToSequence(String imagePath) throws IOException {
        writeToSequence(ImageIO.read(new File(imagePath)));
    }

    /**
     * Convert images sequence to gif file.
     * @param originPath the base path, it will load as origin + min .... origin + max
     * @param min count from
     * @param max count to, include the max
     * @throws IOException if there isn't image with name like originPath[min...max]
     */
    public void writeToSequence(String originPath, int min, int max) throws IOException {
        for (int i = min; i < max; i++) {
            writeToSequence(originPath + i + ".png");
        }
    }

    /**
     * Delete all the file of originPath[min..max].
     * @param originPath the base path, it will load as origin + min .... origin + max
     * @param min count from
     * @param max count to, include the max
     */
    public void deleteSequence(String originPath, int min, int max) {
        for (int i = min; i < max; i++) {
            new File(originPath + i).delete();
        }
    }

    /**
     * Close the file.
     * @throws IOException if can't be closed
     */
    public void close() throws IOException {
        writer.endWriteSequence();
    }
}
