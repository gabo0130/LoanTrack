package com.LoanTrack.Backend.domain.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Metodos para poder formatear valores decimales con separador de miles "." y
 * separador de decimales ",".
 * 
 * @author pbartolo
 */
public class FormatUtils {

	public static DecimalFormat getNumberInstance() {
		return (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMANY);
	}

	public static DecimalFormat initValidate(Integer enteros, Integer decimales) {
		DecimalFormat df = getNumberInstance();
		df.setDecimalSeparatorAlwaysShown(true);
		df.setMinimumFractionDigits(decimales);
		df.setMaximumFractionDigits(decimales);
		df.setMaximumIntegerDigits(enteros);
		df.setParseBigDecimal(true);
		return df;
	}

	/**
	 * La función que convierte un valor entero a una cadena de texto plano.
	 * 
	 * <pre>
	 * 123.456.789 => 123456789
	 * </pre>
	 * 
	 * @param value
	 *            El valor entero que vamos a convertir.
	 * @return La cadena con el valor recibido como texto plano.
	 */
	public static String formatIntegerToString(Integer value) {
		DecimalFormat df = getNumberInstance();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		return value != null ? df.format(value) : "";
	}

	/**
	 * La función que convierte un valor entero largo a una cadena de texto
	 * plano.
	 * 
	 * <pre>
	 * 123.456.789 => 123456789
	 * </pre>
	 * 
	 * @param value
	 *            El valor entero largo que vamos a convertir.
	 * @return La cadena con el valor recibido como texto plano.
	 */
	public static String formatLongToString(Long value) {
		DecimalFormat df = getNumberInstance();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		return value != null ? df.format(value) : "";
	}

	/**
	 * La función que convierte un valor de fecha en una cadena de texto plano
	 * de acuerdo al patrón de fecha común al proyecto.
	 * 
	 * <pre>
	 * dd / MM / yyyy
	 * </pre>
	 * 
	 * @param value
	 *            La fecha que vamos a convertir a texto plano de acuerdo al
	 *            patrón del proyecto.
	 * @return La cadena de texto plano que corresponde con la fecha convertida.
	 */
	public static String formatDateToString(Date value) {
		return formatDateToString(value, Constants.FORMAT_DATE);
	}

	public static String formatDateToString(Date value, String pattern) {
		return value != null ? new SimpleDateFormat(pattern).format(value) : "";
	}

	/**
	 * La función que convierte un valor Doble en una cadena de texto plano de
	 * acuerdo al patrón de número doble común al proyecto.
	 * 
	 * <pre>
	 * #,00
	 * </pre>
	 * 
	 * @param value
	 *            El valor doble que vamos a convertir a texto plano de acuerdo
	 *            al patrón del proyecto.
	 * @return La cadena de texto plano que corresponde con el valor doble
	 *         convertido.
	 */
	public static String formatDoubleToString(Double value) {
		DecimalFormat formatter = initValidate(5, 2);
		return value != null ? formatter.format(value) : "";
	}

	/**
	 * La función que convierte una cadena de texto a un valor Doble de acuerdo
	 * al patrón de número doble común al proyecto.
	 * 
	 * <pre>
	 * #,00
	 * </pre>
	 * 
	 * @param value
	 *            La cadena que texto que vamos a parsear a un valor doble.
	 * @return El valor doble convertido a partir de la cadena de texto.
	 * @throws ParseException
	 *             En caso de falla en el parseo de la cadena.
	 */
	public static Double parseStringToDouble(String value) throws ParseException {
		DecimalFormat formatter = initValidate(5, 2);
		return value != null ? formatter.parse(value).doubleValue() : null;
	}

	/**
	 * Aplica el formato requerido a un BigDecimal: 3.15 => 3,15
	 * 
	 * @param value
	 * @param enteros
	 * @param decimales
	 * @return
	 */
	public static String formatearValorBigDecimal(BigDecimal value, Integer enteros, Integer decimales) {
		DecimalFormat df = initValidate(enteros, decimales);
		return value != null ? df.format(value) : "";
	}

	/**
	 * Aplica formato requerido a un String: 123456,4 => 123.456,4
	 * 
	 * @param value
	 * @param enteros
	 * @param decimales
	 * @return
	 */
	public static String formatearValorString(String value, Integer enteros, Integer decimales) throws ParseException {
		DecimalFormat df = initValidate(enteros, decimales);
		BigDecimal decimal = null;
		if (StringUtils.isNotBlank(value)) {
			decimal = (BigDecimal) df.parse(value);
		}
		return decimal != null ? df.format(decimal) : "";
	}

	/**
	 * Obtiene el BigDecimal a partir de un String: 1.245,4 => 1245.4
	 * 
	 * @param value
	 * @param enteros
	 * @param decimales
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal formatearStringABigDecimal(String value, Integer enteros, Integer decimales)
			throws ParseException {
		DecimalFormat df = initValidate(enteros, decimales);
		return !StringUtils.isBlank(value) ? (BigDecimal) df.parse(value) : null;
	}

	/**
	 * Obtiene un codigo de tipo String con espacios en blanco (relleno) por
	 * delante, agrega la cantidad que falte para completar el tamaño de campo
	 * definido en la DB
	 * 
	 * @param codigo
	 * @param longitudCampo
	 * @return
	 */
	public static String formatearCodigoConEspaciosDelante(String codigo, int longitudCampo) {
		if (codigo != null) {
			return String.format("%1$" + longitudCampo + "s", codigo.trim());
		} else {
			return null;
		}
	}

	/**
	 * Obtiene un codigo de tipo String con espacios en blanco (relleno) por
	 * detrás, agrega la cantidad que falte para completar el tamaño de campo
	 * definido en la DB
	 * 
	 * @param codigo
	 * @param longitudCampo
	 * @return
	 */
	public static String formatearCodigoConEspaciosAtras(String codigo, int longitudCampo) {
		if (codigo != null) {
			return String.format("%1$-" + longitudCampo + "s", codigo);
		} else {
			return null;
		}
	}

	public static Date TodayWithoutTime() {
		return DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH);
	}

	public static Object formatDoubleToType(Double number, Class<?> type) {
		if (type.equals(Long.class)) {
			return number.longValue();
		} else if (type.equals(Integer.class)) {
			return number.intValue();
		}
		if (type.equals(Short.class)) {
			return number.shortValue();
		}
		if (type.equals(Byte.class)) {
			return number.byteValue();
		} else {
			try {
				return type.getConstructor(String.class)
						.newInstance(number % 1 > 0 ? number.toString() : String.valueOf(number.longValue()));
			} catch (Exception e) {
				throw new NumberFormatException();
			}
		}
	}

	public static BigDecimal removeDecimals(BigDecimal numero) {
		return numero != null ? new BigDecimal(numero.toBigInteger()) : null;
	}

}