/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.ex.hex;

import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
public class HexURLConnection extends URLConnection {
    byte[] content;

    protected HexURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        if (content == null) {
            content = Hex.decode(getURL().getPath());
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        connect();
        return new ByteArrayInputStream(content);
    }

    @Override
    public Object getContent() throws IOException {
        connect();
        return content;
    }
}
