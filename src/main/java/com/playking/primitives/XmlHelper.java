package com.playking.primitives;

import com.playking.adapter.SceneAdapter;
import com.playking.scene.Scene;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlHelper {

    private static final String FOLDER_PATH = System.getProperty("user.dir") +
                                              "/src/test/resources/";

    /**
     * The function will return scene object from fileName.
     * @param fileName the file name of the xml store the scene object
     * @return the scene object from the xml file, if there was any error return null
     * @throws NullPointerException if file name is
     * @throws IllegalArgumentException if fileName is empty
     * @throws IOException if the file isn't exist
     */
    public static Scene readSceneObjectFromXml(String fileName)
        throws NullPointerException, IllegalArgumentException, IOException {
        if (fileName == null) {
            throw new NullPointerException("ERROR: File name can't be null");
        }
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("ERROR: File name can't be empty");
        }
        String path = FOLDER_PATH + fileName;
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("ERROR: File doesn't exist");
        }
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(SceneAdapter.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            SceneAdapter sceneAdapter = (SceneAdapter)jaxbUnmarshaller.unmarshal(
                new StringReader(Files.readString(Path.of(path))));

            return sceneAdapter.build();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
