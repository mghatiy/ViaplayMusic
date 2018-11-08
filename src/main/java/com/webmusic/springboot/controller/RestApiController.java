package com.webmusic.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webmusic.springboot.service.MusicDetail;
import com.webmusic.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	MusicDetail musicDetail;
	
	// -------------------Retrieve Single User------------------------------------------

		@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getUser(@PathVariable("id") String id) {
			logger.info("Fetching User with id {}", id);
			JsonNode details = musicDetail.fetchArtist(id);
			if (details == null) {
				logger.error("Artist with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Artist with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				System.out.println("finalJson = "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(details));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<String>(details.textValue(), HttpStatus.OK);
		}
}
