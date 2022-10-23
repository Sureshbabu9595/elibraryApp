package com.library.elibrary.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

@Configuration
public class ElibraryConfig {

	private Logger LOGGER = LoggerFactory.getLogger(ElibraryConfig.class);

	@Bean
	public DeserializationProblemHandler getHandler() {
		DeserializationProblemHandler handler = new DeserializationProblemHandler() {
			@Override
			public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p,
					JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
				LOGGER.info(String.format("The property %s is Unknown for %s", propertyName, beanOrClass.getClass()));
				return true;
			}

		};

		return handler;
	}
}
