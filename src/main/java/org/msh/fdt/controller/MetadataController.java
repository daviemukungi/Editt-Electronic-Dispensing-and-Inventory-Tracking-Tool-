package org.msh.fdt.controller;

import org.msh.fdt.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gitahi on 28/08/14.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController {

	@Autowired
	MetaDataService metaDataService;

	@RequestMapping("/download")
	public void download(HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Content-Disposition", "attachment;filename=fdt_metadata.xml");
		metaDataService.download(response.getOutputStream());
	}

	@RequestMapping("/upload")
	public @ResponseBody
	String upload(@RequestParam("file") MultipartFile file) throws IOException {
		String message = "";
		if (file != null) {
			message = metaDataService.upload(file.getInputStream());
		}
		return message + createBackLink();
	}

	private String createBackLink() {
		String html = "<br><br><a href=\"../../settings.jsp\">Back to settings</a>";
		return html;
	}
}
