package com.primitives;

import com.adapters.scene.SceneAdapter;
import com.scene.Scene;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlHelper {

    private static final String FOLDER_PATH = Paths
        .get(System.getProperty("user.dir"), "src", "test", "resources")
        .toString();

    /**
     * The function will return scene object from fileName.
     * @param fileName the file name of the xml store the scene object
     * @return the scene object from the xml file, if there was any error return null
     * @throws NullPointerException if file name is
     * @throws IllegalArgumentException if fileName is empty
     * @throws IOException if the file isn't exist
     * @throws JAXBException if there was any error during unmarshalling
     */
    public static Scene readSceneObjectFromXml(String fileName)
        throws NullPointerException, IllegalArgumentException, IOException, JAXBException {
        if (fileName == null) {
            throw new NullPointerException("ERROR: File name can't be null");
        }
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("ERROR: File name can't be empty");
        }
        Path path = Paths.get(FOLDER_PATH, fileName);
        if (!Files.exists(path)) {
            throw new IOException("ERROR: File doesn't exist");
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(SceneAdapter.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader stringReader = new StringReader(Files.readString(path));
        SceneAdapter sceneAdapter = (SceneAdapter)jaxbUnmarshaller.unmarshal(stringReader);

        return sceneAdapter.build();
    }
}
