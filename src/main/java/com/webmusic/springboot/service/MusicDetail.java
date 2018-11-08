package com.webmusic.springboot.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("MusicDetail")
public class MusicDetail {
	public JsonNode fetchArtist(String mbid)
	{
		ProcessUrl a = new ProcessUrl(); 
		
		String musicUrl = a.getURL("f27ec8db-af05-4f36-916e-3d57f91ecf5e");
		JsonNode mainPayload = a.fetchJson(musicUrl);
		JsonNode releaseGroup = a.fetchReleaseGroups(mainPayload);

		BuildNewJson js = new BuildNewJson();
		JsonNode relations = mainPayload.get("relations");
		String imageUrl = js.fetchProfile(relations);
		
		JsonNode discog = a.fetchJson(imageUrl);
		
		String profile = discog.get("profile").textValue();
		JsonNode finalJson = js.createJson(releaseGroup, profile, musicUrl);
		
		return finalJson;
		
	}
	public static void main(String[] args) {
		MusicDetail musicDetail = new MusicDetail();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode details = musicDetail.fetchArtist("f27ec8db-af05-4f36-916e-3d57f91ecf5e");
		try {
			System.out.println("finalJson = "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(details));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
