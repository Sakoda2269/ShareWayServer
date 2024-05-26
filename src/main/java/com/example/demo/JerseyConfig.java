package com.example.demo;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig() {
		packages("com.example.demo.resources").register(MultiPartFeature.class);
	}
	
}
