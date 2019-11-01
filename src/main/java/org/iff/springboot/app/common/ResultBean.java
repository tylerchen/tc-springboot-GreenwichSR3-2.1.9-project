/*******************************************************************************
 * Copyright (c) 2019-11-01 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.app.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ResultBean
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-11-01
 * auto generate by qdp.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean implements Serializable {
    private int status;
    private String message;
    private Object data;
}
