package com.zstu.mijazz.wms.Utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswdUtil {

    public boolean isPasswdEqual(String passwd1, String passwd2) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwd1.toCharArray(), passwd2.toCharArray());
        return result.verified;
    }

    public String resetPasswdAsName(String emId) {
        return BCrypt.withDefaults().hashToString(12, emId.toCharArray());
    }

    public String hashPasswd(String passwd) {
        return BCrypt.withDefaults().hashToString(12, passwd.toCharArray());
    }
}
