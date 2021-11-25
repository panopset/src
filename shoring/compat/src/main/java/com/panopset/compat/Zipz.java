package com.panopset.compat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public interface Zipz {

	default void zipFile(String filePath) {
		File file = new File(filePath);
		String zipFileName = createZipFileName(filePath);
		try (FileOutputStream fos = new FileOutputStream(zipFileName);
				ZipOutputStream zos = new ZipOutputStream(fos)) {
			zos.putNextEntry(new ZipEntry(file.getName()));

			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			zos.write(bytes, 0, bytes.length);
			zos.closeEntry();
		} catch (IOException ex) {
			Logop.handle(ex);
		}
	}

	default String createZipFileName(File file) {
		return createZipFileName(Fileop.getCanonicalPath(file));
	}

	default String createZipFileName(String filePath) {
		return filePath.concat(".zip");
	}

	default void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		byte[] buffer = new byte[1024];
		try (FileInputStream fis = new FileInputStream(zipFilePath); ZipInputStream zis = new ZipInputStream(fis)) {
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				try (FileOutputStream fos = new FileOutputStream(newFile)) {
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				}
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		} catch (IOException ex) {
			Logop.handle(ex);
		}

	}
}
