package hu.ponte.hr.services;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {
    private static PrivateKey pk;
    static {
        try {
            InputStream keyIStream = new ClassPathResource("config/keys/key.private").getInputStream();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(IOUtils.toByteArray(keyIStream));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pk = keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sign(byte[] bytes) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(pk);
            signature.update(bytes);
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
