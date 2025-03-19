package com.LoanTrack.Backend.domain.dto;

import java.util.Arrays;

public enum PathRoutesControllersEnum {

	TEST("/", "");

	private String ruta;
	private String requestObjectName;

	private PathRoutesControllersEnum(String ruta, String nombreObjeto) {
		this.ruta = ruta;
		this.requestObjectName = nombreObjeto;
	}

	public String getRuta() {
		return this.ruta;
	}

	public String getRequestObjectName() {
		return requestObjectName;
	}

	public static PathRoutesControllersEnum getByObjectName(String objectName) {
		return Arrays.asList(values()).stream()
				.filter(enumeracion -> enumeracion.getRequestObjectName().equals(objectName)).findAny().orElse(null);

	}

}
