/*******************************************************************************
 * Copyright (c) 2019-10-08 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.ex.hex;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * HexURL
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-08
 * auto generate by qdp.
 */
public class HexURL {

    static {
        String was = System.getProperty("java.protocol.handler.pkgs", "");
        String pkg = Handler.class.getPackage().getName();
        int ind = pkg.lastIndexOf('.');
        assert ind != -1 : "You can't add url handlers in the base package";
        assert "Handler".equals(Handler.class.getSimpleName()) : "A URLStreamHandler must be in a class named Handler; not " + Handler.class.getSimpleName();
        System.setProperty("java.protocol.handler.pkgs", pkg.substring(0, ind) + (was.isEmpty() ? "" : "|" + was));
    }

    public static void register() {
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("hex:" + StringUtils.repeat("1", 10000));
            url.openStream();
            url.getContent();
            FileInputStream fileInputStream = new FileInputStream(new File("/Users/zhaochen/dev/workspace/idea/zcoin_project/ztuo_wallet_rpc/sign-service/target/classes/server.jks"));
            byte[] bs = new byte[102400];
            int read = fileInputStream.read(bs);
            byte[] bytes = new byte[read];
            System.arraycopy(bs, 0, bytes, 0, read);
            System.out.println(Hex.toHexString(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
