package atproj.cyplay.com.asperteamapi.util;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by andre on 12-May-18.
 */

public class Crossknowledge {

    private Context _context;
    private String _crossknowledgeApi;
    private String _crossknowledgeUrl;
    private String _crossknowledgeContent;
    private String _username;

    public Crossknowledge(Context context, int crossknowledgeApiRes, int crossknowledgeUrlRes, int crossknowledgeContentRes, String username) {
        _context = context;
        _crossknowledgeApi = _context.getString(crossknowledgeApiRes);
        _crossknowledgeUrl = _context.getString(crossknowledgeUrlRes);
        _crossknowledgeContent = _context.getString(crossknowledgeContentRes);
        _username = username;
    }

    public String buildUrl(String trainingContentId) {
        String content = _crossknowledgeContent.replace("_training_content_id_", trainingContentId).replace("_login_", _username);
        String hash = md5(_crossknowledgeApi + content);
        String url  = _crossknowledgeUrl + content + "hash/" + hash + "/";
        return url;
    }

    private String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
