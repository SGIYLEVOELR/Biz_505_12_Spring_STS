package com.callor.todo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.callor.todo.config.QualifierConfig;
import com.callor.todo.service.FileServiceABS;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(QualifierConfig.SERVICE.FILE_SERVICE_V1)
public class FileServiceImplV1 extends FileServiceABS {

	/**
	 * fileUp()
	 * 1개의 파일을 서버의 특정 폴더(fileUpPath)에 업로드하기
	 * 
	 * 1. fileUpPath가 정상적으로 있는 지 검사
	 * 2. 폴더가 없으면 폴더 생성하기
	 * 3. 원본파일 이름을 겹쳐서 업로드 되지 않도록 방지 정책
	 * 		UUID + 파일이름 형식으로 파일명을 변경하여 저장
	 * 4. 저장이 완료되면 저장된 파일명을 return
	 */
	@Override
	public String fileUp(MultipartFile file) {
		// TODO Auto-generated method stub
		
		log.debug("파일업로드 path: {}", this.fileUpPath);
		
		if(file == null) {
			return null;
		}
		
		// 업로드 폴더를 검사하기
		File dir = new File(fileUpPath);
		// 업로드할 폴더가 없으면
		if(!dir.exists()) {
			dir.mkdirs(); // 폴더 생성하기
		}
		
		String strUUID = UUID.randomUUID().toString();
		// 원본 파일이름 추출
		String originalFileName = file.getOriginalFilename();
		// UUID + "-" + 원래이름
		String saveFileName = String.format("%s-%s", strUUID, originalFileName);
		
		// 저장할 폴더와 파일이름을 매개변수로 전달하여
		// 파일을 저장하기 위하여 File 객체 생성하기
		File uploadFile = new File(fileUpPath, saveFileName);
		try {
			file.transferTo(uploadFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return saveFileName;
	}

	@Override
	public List<String> filesUp(MultipartHttpServletRequest files) {
		// TODO Auto-generated method stub
		return null;
	}

}
