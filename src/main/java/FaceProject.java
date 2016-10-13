/**
 * Created by Safoora Yousefi on 10/5/16.
 */
import cognitivej.vision.face.scenario.FaceScenarios;
import cognitivej.vision.face.task.Face;
import cognitivej.vision.face.task.FaceLandmarks;
import cognitivej.vision.overlay.builder.ImageOverlayBuilder;

import java.io.*;
import java.util.HashMap;

public class FaceProject {
    public static void main(String[] args) throws IOException {
        FaceScenarios faceScenarios = new FaceScenarios("25ccee78336e40308a541e1b788da54f", "d85b8e78a59940f5a7517cea6b7c2ba9");
        String imageDir = "/Users/Ayine/Desktop/Research/Face/nimstims_faces";
        String outDir = "/Users/Ayine/Desktop/Research/Face/nimstims_faces/output/";

        File faceFolder = new File(imageDir);
        File[] faceFiles = faceFolder.listFiles();
        String faceName, outputFileName, lmFileName;
        for (File faceFile:faceFiles) {
            faceName = faceFile.getName();
            outputFileName = outDir + faceName.substring(0, faceName.length()-".bmp".length()) + ".txt";
            lmFileName = outDir + faceName.substring(0, faceName.length()-".bmp".length()) + "_lm.txt";
            if (faceName.substring(faceName.length()-"bmp".length()).equalsIgnoreCase("bmp")
                    && !(faceName.startsWith("01") || faceName.startsWith("02") || faceName.startsWith("15") || faceName.startsWith("16")))
            {
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFileName)));
                BufferedWriter lmbw = new BufferedWriter(new FileWriter(new File(lmFileName)));
                Face face = faceScenarios.findSingleFace(faceFile);
                HashMap<String, FaceLandmarks.Point> landmarks = face.faceLandmarks.landmarks();
                for (String lm : landmarks.keySet())
                {
                    try
                    {
                        lmbw.write(lm + ":\t" + landmarks.get(lm).x + "\t" + landmarks.get(lm).y);
                    } catch (NullPointerException e) {continue;}
                    lmbw.newLine();
                }
                lmbw.flush(); lmbw.close();
                bw.write(face.faceLandmarks.toString()); bw.newLine();
                bw.write(face.faceRectangle.toString()); bw.newLine();
                bw.write(face.faceAttributesResp.toString()); bw.newLine();
                bw.flush(); bw.close();
                //System.out.print(faces.
                //ImageOverlayBuilder.builder(IMAGE_URL).outlineFacesOnImage((List<Face>) faces).launchViewer();
                //ImageOverlayBuilder.builder(faceFiles[1]).outFaceLandmarksOnImage(faces).launchViewer();
                //System.out.println(face.faceLandmarks);
            }
        }

    }
}
