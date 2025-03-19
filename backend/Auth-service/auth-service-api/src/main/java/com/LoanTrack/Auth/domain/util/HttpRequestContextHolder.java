package com.LoanTrack.Backend.domain.util;

public final class HttpRequestContextHolder {

    private static final ThreadLocal<String> SYSTEM_HOLDER = new ThreadLocal<>();
	private static final ThreadLocal<String> TOKEN_HOLDER = new ThreadLocal<>();
	private static final ThreadLocal<String> AUTHORIZATION_HOLDER = new ThreadLocal<>();
	private static final ThreadLocal<String> APIUSERNAME_HOLDER = new ThreadLocal<>();
	private static final ThreadLocal<String> API_TIENDAS_NEGOCIOS_USER_HOLDER = new ThreadLocal<>();

 	public static void setIdSystem(String idSystem) {
		SYSTEM_HOLDER.set(idSystem);
	}

	public static Integer getIdSystem() {
		return SYSTEM_HOLDER.get() != null ? Integer.parseInt(SYSTEM_HOLDER.get()) : null;
	}

	public static void clearSystem() {
		SYSTEM_HOLDER.remove();
	}

	public static void setToken(String token) {
		TOKEN_HOLDER.set(token);
	}

	public static String getToken() {
		return TOKEN_HOLDER.get() != null ? TOKEN_HOLDER.get() : null;
	}

	public static void clearToken() {
		TOKEN_HOLDER.remove();
	}

	public static void setAuthorization(String type) {
		AUTHORIZATION_HOLDER.set(type);
	}

	public static String getAuthorization() {
		return AUTHORIZATION_HOLDER.get() != null ? AUTHORIZATION_HOLDER.get() : null;
	}

	public static void clearAuthorization() {
		AUTHORIZATION_HOLDER.remove();
	}

	public static void setApiUsername(String username) {
		APIUSERNAME_HOLDER.set(username);
	}

	public static String getApiUsername() {
		return APIUSERNAME_HOLDER.get() != null ? APIUSERNAME_HOLDER.get() : null;
	}

	public static void clearApiUsername() {
		APIUSERNAME_HOLDER.remove();
	}

}

