package by.epam.auction.service.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import by.epam.auction.service.exception.ServiceException;

public class ImageSaver {
	
	private static final Logger LOG = LogManager.getLogger();

	public String writeImg(Part file, String path) throws ServiceException {
		String fileName = "";
		try {
			String header = file.getHeader("content-disposition");
			String targetPath = path + "data";
			File directory = new File(targetPath);
			if (!directory.exists()) {
				directory.mkdir();
			}
			fileName = takeName(header);
			if (!fileName.isEmpty()) {
				file.write(targetPath + File.separator + fileName);
				LOG.log(Level.INFO,
						"File with header " + header + " was saved as\n" + targetPath + File.separator + fileName);
			} else {
				throw new ServiceException("Empty file name");
			}
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		return fileName;
	}

	private String takeName(String header) {
		Pattern pattern = Pattern.compile("[A-Z-a-z-1-9\\w_\\-]+\\.((jpg)|(png))");
		Matcher matcher = pattern.matcher(header);
		String fileName = "";
		if (matcher.find()) {
			fileName = matcher.group();
			fileName = FilenameUtils.getExtension(fileName);
			fileName = UUID.randomUUID() + fileName;
		}
		return fileName;
	}
}

