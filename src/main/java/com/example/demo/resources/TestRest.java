package com.example.demo.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.example.demo.repository.ImageTestRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Component
@Path("/test")
@Slf4j
public class TestRest {
	
	private final ImageTestRepository ir;
	
	TestRest(ImageTestRepository ir){
		this.ir = ir;
	}
	
	@GET
	public String test() {
		return "HELLO";
	}
	
	@POST
	@Consumes({ "image/jpg", "image/png" })
	public void imageTest(byte[] imageBytes) {
		for(byte b : imageBytes) {
			System.out.print(b);
		}
		System.out.println("end");
		log.info("aaaa");
		ir.uploadFile(imageBytes);
	}
	
	@POST
	@Path("/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void upload(@FormDataParam("file") InputStream fileStream, @FormDataParam(value = "file") FormDataContentDisposition fileDisposition,
			@FormParam("test") String test) {
		byte[] a;
		try {
			a = fileStream.readAllBytes();
			ir.uploadFile(a);
			log.info(test);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック, Data data
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/image2")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void upload2(@FormDataParam("file") List<FormDataBodyPart> bodyParts, @FormDataParam("data") List<FormDataBodyPart> dataBody) {
		for(FormDataBodyPart part : bodyParts) {
			try {
				byte[] image = part.getContent().readAllBytes();
				log.info(part.getFileName().get());
				ir.uploadFile(image);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		for(FormDataBodyPart part : dataBody) {
			log.info(part.getFileName().get());
			log.info(part.getContent(MyData.class).toString());
		}
	}
	
	@POST
	@Path("/image2")
	@Consumes(MediaType.APPLICATION_JSON)
	public void upload2(MyData data) {
		log.info(data.toString());
	}
	
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void list(@FormParam("list") List<String> list) {
		for(String s : list) {
			log.info(s.toString());
		}
	}
	
	@POST
	@Path("/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public void jsonTest(MyData data) {
		log.info(data.toString());
	}
	
	@GET
	@Path("/{id}")
	public byte[] imageGet(@PathParam("id") Integer id) {
		Map<String, Object>imageMap = ir.getImageById(id);
		byte[] result = (byte[]) imageMap.get("IMAGE");
		return result;
	}
	
	record MyData(String one, String two, Integer three) {};
	
}





