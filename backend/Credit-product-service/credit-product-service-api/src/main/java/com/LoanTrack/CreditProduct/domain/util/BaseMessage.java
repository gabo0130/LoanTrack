package com.LoanTrack.Backend.domain.util;

import java.io.Serializable;

public abstract class BaseMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected String field;

	protected String messageKey;

	protected Object[] messageKeyValues;

	protected String defaultMessage;

	protected BaseMessage(String messageKey) {
		this.messageKey = messageKey;
	}

	protected BaseMessage(String messageKey, Object[] messageKeyValues) {
		this.messageKey = messageKey;
		this.messageKeyValues = messageKeyValues;
	}

	protected BaseMessage(String field, String messageKey, Object... messageKeyValues) {
		this.field = field;
		this.messageKey = messageKey;
		this.messageKeyValues = messageKeyValues;
	}

	/**
	 * Atributo vinculado al error.
	 * 
	 * @return nombre del atributo.
	 */
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Clave del error en el archivo de recursos.
	 * 
	 * @return clave del error.
	 */
	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * Valores a reemplazar en el mensaje de la clave del error.
	 * 
	 * @return array de valores.
	 */
	public Object[] getMessageKeyValues() {
		return messageKeyValues;
	}

	public void setMessageKeyValues(Object[] messageKeyValues) {
		this.messageKeyValues = messageKeyValues;
	}

	/**
	 * Mensaje a mostrar en caso que la clave del mensaje no exista en el archivo de recursos.
	 * 
	 * @return cadena con el mensaje.
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageKey == null) ? 0 : messageKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseMessage))
			return false;
		BaseMessage other = (BaseMessage) obj;
		if (messageKey == null) {
			if (other.messageKey != null)
				return false;
		} else if (!messageKey.equals(other.messageKey))
			return false;
		return true;
	}

}
