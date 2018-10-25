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

import by.epam.auction.constant.ParsingValues;
import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.service.exception.ServiceException;

/**
 * The Class ImageSaver.
 */
public class ImageSaver {

	/** The Constant LOG. */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Write img.
	 *
	 * @param file the file
	 * @param path the path
	 * @return the string
	 * @throws ServiceException the service exception
	 */
	public String writeImg(Part file, String path) throws ServiceException {
		String fileName = RegExpAndPatternHolder.EMPTY_STRING;
		try {
			String header = file.getHeader(ParsingValues.CONTENT_DISPOSITION);
			String targetPath = path + ParsingValues.IMAGES_FOLDER_NAME;
			File directory = new File(targetPath);
			if (!directory.exists()) {
				directory.mkdir();
			}
			fileName = takeName(header);
			if (!fileName.isEmpty()) {
				file.write(targetPath + File.separator + fileName);
				LOG.log(Level.INFO, "File with header " + header + " was saved as\n" + targetPath + File.separator + fileName);
			} else {
				throw new ServiceException("Empty file name");
			}
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		return fileName;
	}

	/**
	 * Take name.
	 *
	 * @param header the header
	 * @return the string
	 */
	private String takeName(String header) {
		Pattern pattern = Pattern.compile(RegExpAndPatternHolder.IMG_REG_EX);
		Matcher matcher = pattern.matcher(header);
		String fileName = RegExpAndPatternHolder.EMPTY_STRING;
		if (matcher.find()) {
			fileName = matcher.group();
			fileName = FilenameUtils.getExtension(fileName);
			fileName = UUID.randomUUID() + ParsingValues.DOT + fileName;
		}
		return fileName;
	}
}
