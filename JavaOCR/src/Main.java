import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import net.sourceforge.tess4j.*;

public class Main {
	public static void main(String[] args) {
		//Loading the core library 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		try{
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			int k; //k is image number
			for (k=1;k<5;k++) {
				Mat source =Imgcodecs.imread("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\res\\"+k+".jpg",Imgcodecs.CV_LOAD_IMAGE_COLOR);

				Mat destination = new Mat(source.rows(),source.cols(),source.type());
				destination = source;
				Photo.fastNlMeansDenoisingColored(source,destination, 10, 10, 7, 21);
				Imgcodecs.imwrite("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\denoise\\"+k+"d.jpg", destination);
			}
		}catch(Exception e){}
		// TODO code application logic here  	

		try {

			int k;
			for (k=1;k<5;k++) {
				File input = new File("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\denoise\\"+k+"d.jpg");
				BufferedImage image = ImageIO.read(input);	

				byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
				Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
				mat.put(0, 0, data);

				Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC3);

				// Denoising
				Mat destination = new Mat(mat1.rows(),mat1.cols(),mat1.type());
				destination = mat1;
				Photo.fastNlMeansDenoisingColored(mat1,destination, 10, 10, 7, 21);


				Imgproc.cvtColor(mat, destination, Imgproc.COLOR_RGB2GRAY);

				byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
				mat1.get(0, 0, data1);

				BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
				image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
				File ouptut = new File("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\gray\\"+k+"g.jpg");
				ImageIO.write(image1, "jpg", ouptut);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		try{
			int k;
			for (k=1;k<5;k++) {
				String file ="C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\gray\\"+k+"g.jpg";
				Mat src = Imgcodecs.imread(file);

				// Creating an empty matrix to store the result
				Mat dst = new Mat();
				Imgproc.threshold(src, dst, 160, 240, Imgproc.THRESH_BINARY);

				// Writing the image
				Imgcodecs.imwrite("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\binary\\"+k+"b.jpg", dst);

				//System.out.println("Image Processed");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
int k;
for (k=1;k<5;k++) {
		try {

			ITesseract instance = new Tesseract();  // JNA Interface Mapping
			// File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
			instance.setDatapath("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\tessdata");
			File imageFile = new File("C:\\Users\\MF_PC\\git\\JavaOCR\\JavaOCR\\binary\\"+k+"b.jpg");

			String result = instance.doOCR(imageFile);
			System.out.println(result);
			System.out.println("******************************");
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
}
	}
}