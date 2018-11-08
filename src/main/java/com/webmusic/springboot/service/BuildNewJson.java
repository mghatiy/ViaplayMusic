package com.webmusic.springboot.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BuildNewJson {

	public String fetchImage(String id)
	{
		String image = "";
		String coverUrl = "http://coverartarchive.org/release-group/"+id;
		ProcessUrl a = new ProcessUrl();
		JsonNode coverArt = a.fetchJson(coverUrl);
		if (coverArt != null) {
			image = coverArt.get("images").get(0).get("image").textValue();
		}
		return image;	
	}


	public JsonNode createJson( JsonNode mainPayload, String profile, String mbid) {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();

		ArrayNode albumsArray = mapper.createArrayNode();
		for (int i = 0; i < mainPayload.size(); i++) 
		{
			ObjectNode album = mapper.createObjectNode();
			album.put("title", mainPayload.get(i).get("title").textValue());
			String id = mainPayload.get(i).get("id").textValue();
			System.out.println("id= "+id);
			album.put("id", id);
			//call method to hit URL using above ID and return 'image' and then add that to album
			
			String coverimage = fetchImage(id);
			album.put("Image", coverimage);

			albumsArray.add(album);
		}

		ObjectNode objectNode4 = mapper.createObjectNode();
//		JsonNode testNode = mapper.createObjectNode();
		objectNode4.putPOJO("mbid", mbid);
		objectNode4.putPOJO("description", profile);
		objectNode4.putPOJO("albums", albumsArray);

		arrayNode.add(objectNode4);

		((ObjectNode) rootNode).set("root", objectNode4);
		//System.out.println("rootNode="+rootNode.textValue());
		return rootNode;
	}


	public String fetchProfile(JsonNode relations)
	{
		String url = "";
		String resource  = "";
		for (int i = 0; i < relations.size(); i++) 
		{
			if (relations.get(i).get("type").textValue().equals("discogs")) {
				resource = relations.get(i).get("url").get("resource").textValue();
				url = resource.replace("www", "api").replace("artist", "artists");
				break;
			}
		}
		return url;
	}
	public JsonNode createFinalJson( JsonNode mainPayload, String profile) {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();

		ArrayNode albumsArray = mapper.createArrayNode();
		for (int i = 0; i < mainPayload.size(); i++) 
		{
			ObjectNode album = mapper.createObjectNode();
			album.put("title", mainPayload.get(i).get("title").textValue());
			album.put("id", mainPayload.get(i).get("id").textValue());
			//call method to hit URL using above ID and return 'image' and then add that to album
			String coverimage = fetchImage("f32fab67-77dd-3937-addc-9062e28e4c37");
			album.put("Image", coverimage);

			albumsArray.add(album);
		}

		ObjectNode objectNode4 = mapper.createObjectNode();
		objectNode4.putPOJO("albums", albumsArray);

		arrayNode.add(objectNode4);

		((ObjectNode) rootNode).set("root", objectNode4);
		return rootNode;
	}
	

	//write code in driver class which has a method to create/merge final payload.
	// Call a method which returns profile as description by hitting URL https://api.discogs.com/artists/15885

}
