package br.com.getjava.cloudws;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class MainApp {

	public static void main(String[] args) throws Exception {

		Component component = new Component();

		component.getServers().add(Protocol.HTTP, 8080);

		component.getDefaultHost().attach("/cloudws", new RotasApp());

		component.start();
	}

}
