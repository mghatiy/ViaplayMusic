package com.webmusic.springboot.service;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessUrl 
{
	private URI getBaseURI(String url) {
		return UriBuilder.fromUri(url).build();
	}
	public String getURL(String mbid) {
		String url = "http://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
		return url;
	}

	public JsonNode fetchJson(String url) {
		JsonNode mainPayload = null;
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(getBaseURI(url));
		Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
		int errCode = response.getStatus();

		String plainAnswer = response.readEntity(String.class);

		//IF THE ERROR CODE IS NOT 200 then there is an error
		if(errCode != 200)
		{ 
			System.out.println("Response failure on hitting this Url");
		}

		else {
			ObjectMapper mapper = new ObjectMapper();
		//	JsonNode mainPayload = null;

			try {
				mainPayload = mapper.readTree(plainAnswer);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return mainPayload;
	}
	
	public JsonNode fetchReleaseGroups(JsonNode mainPayload) {
	JsonNode releaseGroup = mainPayload.get("release-groups");
	return releaseGroup;
	}
	
}
