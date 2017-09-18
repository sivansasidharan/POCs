package com.ss.nitro.analytics.dyorg.utility;

import org.springframework.beans.BeanUtils;

/**
 * <code>AttributeCopier</code> copies the attributes having the same name from
 * data transfer object to domain object or vice-versa.
 * <p>
 * 
 * This performs the following function:<br>
 * <ul>
 * <li>Copies the attribute</li>
 * </ul>
 * 
 * @published
 */
public class AttributeCopier {

	/**
	 * <code>Default Constructor.</code>
	 */
	public AttributeCopier() {

	}

	/**
	 * Copies the attribute values from one object to another object.
	 * <p>
	 * The copy properties method copies the data transfer object to domain
	 * object and vice-versa.<br>
	 * <p>
	 * <b>Release Notes:</b> <br>
	 * <table border="1" cellspacing="0" cellpadding="5" width="80%">
	 * <tr>
	 * <th align="left">Release</th>
	 * <th align="left">Notes</th>
	 * </tr>
	 * <tr>
	 * <td>@Since 1.0</td>
	 * <td>&nbsp;</td>
	 * </tr>
	 * </table>
	 * </p>
	 * <p>
	 * 
	 * @param pCopyFrom
	 *            <code>Object</code><br>
	 *            The object from which the attribute values are to be copied.<br>
	 * @param pCopyTo
	 *            <code>Object</code><br>
	 *            The object to which the attribute values are to be copied.<br>
	 *            </p>
	 */
	public void copyAttribute(Object pCopyFrom, Object pCopyTo) {

		BeanUtils.copyProperties(pCopyFrom, pCopyTo);

	}

}
