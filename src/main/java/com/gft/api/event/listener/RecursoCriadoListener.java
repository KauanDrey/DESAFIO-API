package com.gft.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gft.api.event.RecursoCriadoEvent;


@Component
public class RecursoCriadoListener implements ApplicationListener <RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent RecursoCriadoEvent) {
	HttpServletResponse response = RecursoCriadoEvent.getResponse();
	Long id = RecursoCriadoEvent.getId();
	
	adicionarHeaderLocation(response, id);
		
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id)
				.toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
