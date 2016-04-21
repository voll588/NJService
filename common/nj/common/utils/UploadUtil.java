package nj.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;



public class UploadUtil {

	/**
	 * 上传文件，写入磁盘
	 * @param request
	 * @return
	 * @throws FileUploadException
	 */
	public Map<String,Object> fileUpload(HttpServletRequest request,String outFile_path) throws Exception {
		Map<String,Object> retMap = new HashMap<String,Object>();
		int sizeThreshold = Integer.parseInt(SysUtil.getValue("SizeThreshold"));
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(sizeThreshold);
		String tempPath = SysUtil.getValue("temp_path");
		File tempfile = new File(tempPath);
		diskFileItemFactory.setRepository(tempfile);
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		List fileList = fileUpload.parseRequest(request);
		Iterator fileItr = fileList.iterator();
		try{
			while (fileItr.hasNext()) {
				FileItem fileItem = (FileItem) fileItr.next();
				InputStream in=null;
				if (!fileItem.isFormField()) {
					//if(fileItem.getSize()<=2097152){ //上传文件大小的限制
						try {
							in = fileItem.getInputStream();
							String fileName = FileUtil.getTimeName(fileItem.getName());
							File file = new File(SysUtil.getValue("web_root")+outFile_path+fileName);
							FileUtils.copyInputStreamToFile(in, file);
							retMap.put("outFile_path", file.getAbsolutePath());
							retMap.put("url", outFile_path+fileName);
						} catch (Exception e) {
							throw e;
						} finally {
							if (null != in)
								in.close();
							fileItem.delete();
						}
					//}
				} else {
					String fieldName = fileItem.getFieldName();
					String value ="";
					if(fieldName.equals("filenames")){
						value = fileItem.getString("gb2312");
					}else{
						value = fileItem.getString("UTF-8");
					}
					System.out.println(fieldName+"============="+value);
					retMap.put(fieldName, value);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return retMap;
	}

	public void uploadLocal_new(HttpServletRequest request, String fileStorePath) throws IOException {
		ReadableByteChannel rin=null;
		FileChannel fch=null;
		RandomAccessFile ra=null;
		java.io.InputStream in = null;
		try {
			ByteBuffer bb = ByteBuffer.allocate(8192);
			in =request.getInputStream();
			rin = Channels.newChannel(in); 
			fch =new RandomAccessFile(fileStorePath, "rw").getChannel();
			while (rin.read(bb)>0){
				bb.flip();
				fch.write(bb);
				bb.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			fch.force(true);
			if(fch!=null){
				fch.close();
			}
			if(rin!=null){
				rin.close();
			}
			if(in != null){
				in.close();
			}
		}
	}
	

	/**
	 * 获取文件输入流
	 * @param fItem
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(FileItem fItem) throws IOException {
		return fItem.getInputStream();
	}
	
}
