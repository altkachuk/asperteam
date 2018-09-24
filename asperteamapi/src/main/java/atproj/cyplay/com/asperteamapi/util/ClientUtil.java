package atproj.cyplay.com.asperteamapi.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by andre on 26-Mar-18.
 */

public class ClientUtil {

    private static String _clientUrl;

    public static String getClientUrl() {
        return _clientUrl != null ? _clientUrl : "";
    }

    public static void setClientUrl(String buildConfig, String clientUrl) {
        Map<String, String> configMap = getConfigMap(clientUrl);
        _clientUrl = configMap.get(buildConfig);
    }

    private static Map<String, String> getConfigMap(String confString) {
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        return new Gson().fromJson(confString, mapType);
    }


}
