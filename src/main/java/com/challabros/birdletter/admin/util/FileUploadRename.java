package com.challabros.birdletter.admin.util;

import java.io.File;
import java.io.IOException;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileUploadRename implements FileRenamePolicy {

	public FileUploadRename() {
	}

	public File rename(File f) {
		if (createNewFile(f))
			return f;

		String name = f.getName();
		String body = null;
		String ext = null;

		int dot = name.lastIndexOf(".");
		if (dot != -1) {
			body = name.substring(0, dot);
			ext = name.substring(dot);
		} else {
			body = name;
			ext = "";
		}

		int count = 0;
		// 중복된 파일 있을때
		while (!createNewFile(f) && count < 9999) {
			count++;
			String newName = body + "_V" + count + ext;
			f = new File(f.getParent(), newName);
		}
		return f;
	}

	private boolean createNewFile(File f) {
		try {
			return f.createNewFile(); // 존재하는 파일이 아니면
		} catch (IOException e) {
			return false;
		}
	}
}
