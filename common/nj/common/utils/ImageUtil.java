package nj.common.utils;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 创建图片缩略图(等比缩放)
	 * 
	 * @param src
	 *            源图片文件完整路径
	 * @param dist
	 *            目标图片文件完整路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 */
	public void createThumbnail(String src, String dist, float width,
			float height) {
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				logger.error("文件不存在");
				return;
			}
			ImageIO.setUseCache(true);
			ImageInputStream ios = new FileImageInputStream(srcfile);
			BufferedImage image = ImageIO.read(ios);

			// 获得缩放的比例
			double ratio = 1.0;
			double wratio = 1.0;
			double hratio = 1.0;

			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				//				if (image.getHeight() > image.getWidth()) {
				//					ratio = height / image.getHeight();
				//				} else {
				//					ratio = width / image.getWidth();
				//				}
				hratio = height / image.getHeight();
				wratio = width / image.getWidth();
			}
			if(hratio>wratio){
				ratio=hratio;
			}else{
				ratio=wratio;
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);

			BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream os = new FileOutputStream(dist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();
			logger.info("创建缩略图成功");
		} catch (Exception e) {
			logger.error("创建缩略图发生异常" + e.getMessage());
		}
	}

	public void createThumbnail(File srcfile, String dist, float width,
			float height) {
		try {
			BufferedImage image = ImageIO.read(srcfile);

			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				if (image.getHeight() > image.getWidth()) {
					ratio = height / image.getHeight();
				} else {
					ratio = width / image.getWidth();
				}
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);

			BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream os = new FileOutputStream(dist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();
			logger.info("创建缩略图成功");
		} catch (Exception e) {
			logger.error("创建缩略图发生异常" + e.getMessage());
		}
	}
	
	/**
	 * 计算经纬度，生成缩略图
	 * @param map
	 * @return
	 * @throws FileNotFoundException 
	 */
	public void createThumbnail(Map map,String outFile_path) throws FileNotFoundException {
		InputStream in = null;
		try {
			File thumbFile = new File(outFile_path);
			in = new FileInputStream(outFile_path);
			//计算图片拍摄设备、拍摄时间
	        ImageUtil.getImgExif(map, outFile_path);
	        //生成缩略图
			String imageName = FileUtil.getTimeName(thumbFile.getName());
			String compressPath = SysUtil.getValue("web_root") + "static/help_pic/"+DateUtil.getday()+"/cs" + imageName;
			map.put("thumbnail", "static/help_pic/"+DateUtil.getday()+"/cs" + imageName);
			new ImageUtil().createThumbnail(in, compressPath, 600, 600);
		} catch (BmpException e) {
			map.put("thumbnail", "");
			//File f = new File(StringUtils.error_path);
			//new ImageUtil().createThumbnail(f, compressPath, 100, 100);
			//retMap.put("FileType", fmime + "_bad");
		} finally {
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 生产头像缩略图
	 * @param map
	 * @return
	 * @throws FileNotFoundException 
	 */
	public void createPhotoThumbnail(Map map,String outFile_path) throws FileNotFoundException {
		InputStream in = null;
		try {
			File thumbFile = new File(outFile_path);
			in = new FileInputStream(outFile_path);
	        //生成缩略图
			String imageName = FileUtil.getTimeName(thumbFile.getName());
			String compressPath = SysUtil.getValue("web_root") + "static/assess/"+DateUtil.getday()+"/cs" + imageName;
			map.put("thumbnail", "static/assess/"+DateUtil.getday()+"/cs" + imageName);
			new ImageUtil().createThumbnail(in, compressPath, 600, 600);
		} catch (BmpException e) {
			map.put("thumbnail", "");
			//File f = new File(StringUtils.error_path);
			//new ImageUtil().createThumbnail(f, compressPath, 100, 100);
			//retMap.put("FileType", fmime + "_bad");
		} finally {
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void createThumbnail(InputStream input, String dist, float width,
			float height) throws BmpException {
		BufferedImage image=null;
		try {
			ImageIO.setUseCache(false);
			image = ImageIO.read(input);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new BmpException("创建缩略图：获取原图失败!");
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 获得缩放的比例
		double ratio = 1.0;
		// 判断如果高、宽都不大于设定值，则不处理
		if (image.getHeight() > height || image.getWidth() > width) {
			if (image.getHeight() > image.getWidth()) {
				ratio = height / image.getHeight();
			} else {
				ratio = width / image.getWidth();
			}
		}
		// 计算新的图面宽度和高度
		int newWidth = (int) (image.getWidth() * ratio);
		int newHeight = (int) (image.getHeight() * ratio);

		BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		bfImage.getGraphics().drawImage(
				image.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);

		FileOutputStream os=null;
		try {
			os = new FileOutputStream(dist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BmpException("创建缩略图：缩略图存储地址无效！");
		} catch (ImageFormatException e) {
			e.printStackTrace();
			throw new BmpException("创建缩略图：缩略图格式错误！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BmpException("创建缩略图：转换缩略图失败！");
		} finally{
			try {
				if(os!=null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		//logger.info("创建缩略图成功......");
		/*} catch (IllegalArgumentException e2) {
				//e2.printStackTrace();
				logger.error("创建缩略图失败"); 
				throw new IllegalArgumentException ("图片文件错误");
			}catch (Exception e) {
				System.out.println(e);
				// TODO Auto-generated catch block
				logger.error("创建缩略图失败");
				 throw new BmpException("图片文件错误");
			}*/
	}

	public Map createThumbnail(InputStream input, String path,
			String orientation, int resolution) throws Exception{
		Map map = new HashMap();
		try {
			BufferedImage image = ImageIO.read(input);
			if (orientation != null) {
				image = rotateImage(image, orientation);
			}
			int min = resolution;
			int width = image.getWidth();
			int height = image.getHeight();
			if (width > height) {
				if (min < height) {
					width = width * min / height;
					height = min;
				}
			} else if (width < height) {
				if (min < width) {
					height = height * min / width;
					width = min;
				}
			} 
			BufferedImage bfImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
			if (width > resolution || height > resolution) {
				bfImage = new ImageUtil().imageCut(bfImage, width, height);
			}
			FileOutputStream os = new FileOutputStream(path);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();
			map.put("size", width * height);
		} catch (IllegalArgumentException e2) {
			e2.printStackTrace();
			logger.error("创建缩略图失败"); 
			throw new IllegalArgumentException ("图片文件错误");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("");
		}
		return map;
	}

	/**
	 * 旋转图片处理
	 * @param buffImage
	 * @param orientation 图片拍摄的方向，也可以理解为角度，
	 * 					  其中6表示90度，3表示180度，8表示270度
	 * @return
	 */
	public BufferedImage rotateImage(BufferedImage buffImage,String orientation) {
		AffineTransform transform = new AffineTransform();
		BufferedImage dstImage = null;
		if(orientation!=null) {
			if("6".equals(orientation)) { 
				transform.translate(buffImage.getHeight(), 0); //90°
				dstImage = new BufferedImage(buffImage.getHeight(), buffImage.getWidth(), buffImage.getType());
				transform.rotate(java.lang.Math.toRadians(90));
				AffineTransformOp op = new AffineTransformOp(transform, null); 
				buffImage = op.filter(buffImage, dstImage);
			} else 	if("3".equals(orientation)) { 
				transform.translate(buffImage.getWidth(),buffImage.getHeight()); //180°
				dstImage = new BufferedImage(buffImage.getWidth(), buffImage.getHeight(), buffImage.getType());
				transform.rotate(java.lang.Math.toRadians(180));
				AffineTransformOp op = new AffineTransformOp(transform, null); 
				buffImage = op.filter(buffImage, dstImage);
			} else if("8".equals(orientation)) { 
				transform.translate(0, buffImage.getWidth()); //270°
				dstImage = new BufferedImage(buffImage.getHeight(), buffImage.getWidth(), buffImage.getType());
				transform.rotate(java.lang.Math.toRadians(270));
				AffineTransformOp op = new AffineTransformOp(transform, null); 
				buffImage = op.filter(buffImage, dstImage);
			}
		}
		return buffImage;
	}

	public static InputStream createPreview(InputStream input, float width,
			float height) throws ImageFormatException, IOException {
		BufferedImage image;
		ByteArrayOutputStream out = null;
		image = ImageIO.read(input);
		// 获得缩放的比例
		double ratio = 1.0;
		// 判断如果高、宽都不大于设定值，则不处理
		if (image.getHeight() > height || image.getWidth() > width) {
			if (image.getHeight() > image.getWidth()) {
				ratio = height / image.getHeight();
			} else {
				ratio = width / image.getWidth();
			}
		}
		// 计算新的图面宽度和高度
		int newWidth = (int) (image.getWidth() * ratio);
		int newHeight = (int) (image.getHeight() * ratio);

		BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		bfImage.getGraphics().drawImage(
				image
				.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);

		out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bfImage);
		out.close();
		logger.info("创建预览图成功......");
		return new ByteArrayInputStream(out.toByteArray());
	}

	public InputStream createPreview(InputStream input,String orientation,float width,
			float height) {
		try{
			BufferedImage image;
			ByteArrayOutputStream out = null;
			image = ImageIO.read(input);
			if(orientation!=null){
				image = new ImageUtil().rotateImage(image,orientation);
			}
			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				if (image.getHeight() > image.getWidth()) {
					ratio = height / image.getHeight();
				} else {
					ratio = width / image.getWidth();
				}
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);

			BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image
					.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);

			out = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bfImage);
			out.close();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IllegalArgumentException e2) {
			e2.printStackTrace();
			logger.error("创建缩略图失败"); 
			throw new IllegalArgumentException ("图片文件错误");
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public static void getImgExif(Map map,String imgpath) {
		InputStream in = null;
		try {
			in = new FileInputStream(imgpath);
			Metadata metadata = JpegMetadataReader.readMetadata(in);
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			//拍摄设备
			String img_dev = exif.getString(ExifDirectory.TAG_MAKE);
			map.put("img_dev", img_dev==null?"":img_dev);
			//拍摄时间
			SimpleDateFormat sf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			String createtime = exif.getString(ExifDirectory.TAG_DATETIME_ORIGINAL);
			if(createtime !=null){
				map.put("pDatetime",sf.parse(createtime));
			}else{
				map.put("pDatetime",null);
			}
			//分辨率
			String height = exif.getString(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
			String width = exif.getString(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
			if(height!=null && width!=null){
				map.put("img_size",Integer.parseInt(height)*Integer.parseInt(width)+"");
			}else{
				map.put("img_size",null);
			}

			//获取经纬度
			//map.put("pLatitude","");
			//map.put("pLongitude","");
			/*ExifReader er = new ExifReader(new File(imgpath));
			Metadata exif1 = er.extract();
			Iterator itr = exif1.getDirectoryIterator();

			while (itr.hasNext()) {
				Directory directory = (Directory) itr.next();
				Iterator tags = directory.getTagIterator();
				while (tags.hasNext()) {
					Tag tag = (Tag) tags.next();
					System.out.println(tag);
				}
			}*/
		}catch(JpegProcessingException je) {
			je.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	public static void getImgExif_Old(Map map,InputStream in) {
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(in);
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			//拍摄设备
			map.put("img_dev", exif.getString(ExifDirectory.TAG_MAKE));
			//拍摄时间
			SimpleDateFormat sf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			String createtime = exif.getString(ExifDirectory.TAG_DATETIME_ORIGINAL);
			if(createtime !=null){
				map.put("img_create",sf.parse(createtime));
			}else{
				map.put("img_create",null);
			}
			//分辨率
			String height = exif.getString(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
			String width = exif.getString(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
			if(height!=null && width!=null){
				map.put("img_size",Integer.parseInt(height)*Integer.parseInt(width)+"");
			}else{
				map.put("img_size",null);
			}

			//计算非照片类图片分辨率
			//	        BufferedImage buffImg = ImageIO.read(in3);
			//	        retMap.put("img_size", buffImg.getWidth()*buffImg.getHeight()+"");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 对上传发生错误的图片进行处理
	 * @param input
	 * @param path
	 * @param resolution
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public Map 	correctExceptionImage(InputStream input, String path,int resolution) throws IOException, InterruptedException {
		Map map = new HashMap();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		while (true) {
			int n = input.read(buf);
			if (n == -1)
				break;
			baos.write(buf, 0, n);
		}
		baos.close();
		Image image = Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);

		BufferedImage bfImage = convertImageToBuffer(image);

		int min = resolution;
		int width = bfImage.getWidth();
		int height = bfImage.getHeight();
		if (width > height) {
			if (min < height) {
				width = width * min / height;
				height = min;
			}
		} else if (width < height) {
			if (min < width) {
				height = height * min / width;
				width = min;
			}
		} 

		bfImage.getGraphics().drawImage(
				bfImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
				0, 0, null);
		if (width > resolution || height > resolution) {
			bfImage = new ImageUtil().imageCut(bfImage, width, height);
		}
		FileOutputStream os = new FileOutputStream(path);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(bfImage);
		os.close();
		return map;
	}


	/**
	 * 上传图片发错错误重新生成预览图
	 * @param input
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public InputStream correctExceptionImage2(InputStream input,float width,
			float height) throws IOException, InterruptedException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		while (true) {
			int n = input.read(buf);
			if (n == -1)
				break;
			baos.write(buf, 0, n);
		}
		baos.close();
		Image image = Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);

		BufferedImage bfImage = new BufferedImage(image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics g = bfImage.createGraphics();  
		g.drawImage(image, 0, 0, null);  
		g.dispose(); 

		// 获得缩放的比例
		double ratio = 1.0;
		// 判断如果高、宽都不大于设定值，则不处理
		if (bfImage.getHeight() > height || bfImage.getWidth() > width) {
			if (bfImage.getHeight() > bfImage.getWidth()) {
				ratio = height / bfImage.getHeight();
			} else {
				ratio = width / bfImage.getWidth();
			}
		}
		// 计算新的图面宽度和高度
		int newWidth = (int) (bfImage.getWidth() * ratio);
		int newHeight = (int) (bfImage.getHeight() * ratio);

		bfImage.getGraphics().drawImage(
				image.getScaledInstance(newWidth, newHeight,
						bfImage.SCALE_SMOOTH), 0, 0, null);  
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
		encoder.encode(bfImage);
		baos.close();
		return new ByteArrayInputStream(baos.toByteArray());
	}

	private static BufferedImage convertImageToBuffer(Image image){  
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),  
				BufferedImage.TYPE_INT_RGB);  
		Graphics g = bufferedImage.createGraphics();  
		g.drawImage(image, 0, 0, null);  
		g.dispose();  
		return bufferedImage;  
	}

	/**
	 * 
	 * 创 建 人:  YanHao
	 * 描    述:  图片裁剪
	 * @param bfImage
	 * @param w
	 * @param h
	 * @return
	 * 创建时间:  Oct 11, 2013
	 * 修改人名:  xxx;
	 * 修改内容:  &lt;修改内容&gt;
	 * 修改时间:  Oct 11, 2013
	 */
	public BufferedImage imageCut(BufferedImage bfImage, int w, int h) {
		int x = 0;
		int y = 0;
		if (w == h) {
			return bfImage;
		} else if (w > h) {// 宽大于高，裁剪两边
			x = (w - h) / 2;
			w = h;
		} else if (h > w) {// 高大于宽，裁剪上下边
			y = (h - w) / 2;
			h = w;
		}
		bfImage = bfImage.getSubimage(x, y, w, h);
		return bfImage;
	}

	/**
	 * 
	 * 创建缩略图
	 */
	public void createThumbnailbyIOS(InputStream input, String dist, float width,
			float height) throws BmpException {
		BufferedImage image=null;

		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new BmpException("创建缩略图：获取原图失败!");
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}