package nj.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class FileUtil {

	public static final String DEFAULT_FILE_TYPE = "txt";


	/**
	 * 创建文件目录
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean mkDir(String path) throws Exception {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			file = null;
		}
		return false;
	}


	/**
	 * 处理上传文件路径
	 * @param path
	 * @return
	 */
	public static String getUploadPath(String path) {
		path = path.replace("\\", "/");
		String lastChar = path.substring(path.length() - 1);
		if (!"/".equals(lastChar)) {
			path += "/";
		}
		return path;
	}

	/**
	 * 传入一个文件名，得到这个文件名称的后缀
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			String suffix = fileName.substring(index);// 后缀
			return suffix;
		} else {
			return null;
		}
	}

	/**
	 *  传递一个文件名称和一个新名称，组合成一个新的带后缀文件名
	 * @param fileName
	 * @param newName
	 * @param nullSuffix
	 * @return
	 */
	public static String getNewFileName(String fileName, String newName,
			String nullSuffix) {
		String suffix = getSuffix(fileName);
		if (suffix != null) {
			newName += suffix;
		} else {
			newName = newName.concat(".").concat(nullSuffix);
		}
		return newName;
	}

	/**
	 * 利用uuid产生一个随机的name
	 * @param fileName
	 * @return
	 */
	public static String getRandomName(String fileName) {
		String randomName = UUID.randomUUID().toString();
		return getNewFileName(fileName, randomName, FileUtil.DEFAULT_FILE_TYPE);
	}

	/**
	 * <b>function:</b> 用当前日期、时间和1000以内的随机数组合成的文件名称
	 * 
	 * @package commond
	 * @createDate 2012-5-13
	 * @author yangjd
	 * @return 新文件名称
	 */
	public static String getNumberName(String fileName) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
		int rand = new Random().nextInt(1000);
		String numberName = format.format(new Date()) + rand;
		return getNewFileName(fileName, numberName, FileUtil.DEFAULT_FILE_TYPE);
	}

	/**
	 * 根据系统时间生成上传后保存的文件名
	 * @param fileName
	 * @return
	 */
	public static String getTimeName(String fileName) {
		String numberName = UUID.randomUUID().toString();//System.currentTimeMillis()+"";
		return getNewFileName(fileName, numberName, FileUtil.DEFAULT_FILE_TYPE);
	}


	/**
	 * 缩略图文件处理
	 * @param ins
	 * @param file
	 */
	public static void inputstreamtofile(InputStream ins,File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
