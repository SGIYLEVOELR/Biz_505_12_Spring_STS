package com.callor.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.callor.todo.service.FileServiceABS;
import com.callor.todo.config.QualifierConfig;
//import com.callor.todo.model.TodoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
//	private final List<TodoVO> todoList = new ArrayList<TodoVO>();
	
	private final FileServiceABS fileService;
	public HomeController(FileServiceABS fileService) {
		this.fileService = fileService;
	}

	// @ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
	public String home(Model model, String to_text, @RequestParam("to_image") MultipartFile to_image) {

		Map<String, String> retFileName =fileService.fileUp(to_image); 
		
//		TodoVO vo = TodoVO.builder()
//			.to_text(to_text)
//			.to_sImage(retFileName.get(QualifierConfig.FILE_SERVICE.SAVENAME))
//			.build();
//		
//		todoList.add(vo);
		
//		model.addAttribute("TODOLIST", todoList);
		model.addAttribute("IMAGES", retFileName);
		
		log.debug("TODO: {}", to_text);
		log.debug("이미지: {}", to_image.getOriginalFilename());

		return "home";
	}
	
	/**
	 * web에서 전송된 데이터를 받는 방법
	 * 1. 단독 변수를 통해서 데이터를 받기
	 * 		String, Long, Integer 등등 개별적으로 변수에 받기
	 * 		@RequestParam()을 사용하여 변수명을 지정해서 받는다
	 * 		명확하게 변수 이름을 매칭 가능
	 * 		혹시 변수값이 잘못(type) 전달되어 400 오류가 나는 것을 방지할 수 있다.
	 * 
	 * 2. Map<String, Object>를 사용하여 데이터 받기
	 * 		form에서 POST로 전송된 데이터는 Map을 사용하여 쉽게 수신할 수 있다.
	 * 		
	 * 		@RequestBody를 사용해서 변수를 설정한다
	 *		@RequestBody는 한 개의 매개변수만을 사용할 수 있다
	 *
	 *3. VO(DTO)를 사용하여 데이터 받기
	 *		form에서 POST, GET, PUT, DELETE 등으로 전송된 데이터는 모두 적절한 VO를 통해서 데이터를 수신할 수 있다.
	 *		@ModelAttribute를 부착하여 명확한 형태의 데이터를 수신할 수 있다.
	 *		@ModelAttribute를 부착하여 데이터를 수신하려면 
	 *			VO(DTO) 클래스에 반드시 Getter, Setter, 필드 생성자(@AllArgsConstructor)가 있어야 한다. 
	 */
	/*
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, String> upload(@ModelAttribute TodoVO todoVO , @RequestParam("to_image") MultipartFile to_image)  {
		
		log.debug("TEXT: {}", to_text);
		log.debug("이미지: {}", to_image.getOriginalFilename());
		
		Map<String, String> retName = fileService.fileUp(to_image);
		
		return retName;
	}
	*/

}
