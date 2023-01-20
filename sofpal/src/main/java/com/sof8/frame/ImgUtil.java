package com.sof8.frame;

import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class ImgUtil {
	public static void saveFile(MultipartFile mf, String imgdir) throws Exception{
		byte [] data;
		String imgname = mf.getOriginalFilename();
		try {
			data = mf.getBytes();
			
			FileOutputStream fo2 = new FileOutputStream(imgdir+imgname);
			fo2.write(data);
			fo2.close();
		}catch(Exception e) {
			throw e;
		}

	}
	
}




