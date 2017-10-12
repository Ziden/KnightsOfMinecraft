package nativelevel.Language;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nativelevel.KoM;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Nosliw
 */
public class TRL {

    public static String translate(LNG language, MSG message) {
        return message.get(language);
    }

    public static String translate(LNG language, String message) {
        for (MSG msg : MSG.values()) {
            for (LNG lng : LNG.values()) {
                if (msg.get(lng).equalsIgnoreCase(message))
                    return msg.get(language);
            }
        }
        return message;
    }

    public static String translateOrNull(LNG language, String message) {
        for (MSG msg : MSG.values()) {
            for (LNG lng : LNG.values()) {
                if (msg.get(lng).equalsIgnoreCase(message))
                    return msg.get(language);
            }
        }
        return null;
    }

    public static String calcTranslation(LNG langFrom, LNG langTo, String text) {
        HashMap hm = new HashMap();
        Pattern p = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");

        Matcher m = p.matcher(text);
        StringBuffer sb = new StringBuffer();
        String urlTmp = "";

        while (m.find()) {
            urlTmp = m.group(1);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            hm.put(uuid, urlTmp);
            text.replace(urlTmp, uuid);
            m.appendReplacement(sb, "");
            sb.append(urlTmp);
        }
        m.appendTail(sb);
        text = sb.toString();

        text = URLEncoder.encode(text);
        String response = readURL("http://translate.google.com/translate_a/t?q=" + text + "&client=p&text=&sl=" + langFrom.name() + "&tl=" + langTo.name() + "&ie=UTF-8&oe=UTF-8");
        response = parse(response);

        Set set = hm.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            response.replace(me.getKey().toString(), me.getValue().toString());
        }

        response = postProcess(response);
        return response;
    }

    private static String postProcess(String response) {
        response = response.replace(" :", ":");
        response = response.replace(" ,", ",");
        response = response.replace(". / ", "./");

        if (response.startsWith("�") && !response.contains("?"))
            response = response + "?";
        if (response.startsWith("�") && !response.contains("!"))
            response = response + "!";
        return response;
    }

    private static String parse(String response) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray sentences = (JSONArray) obj.get("sentences");
        String finalresponse = "";
        for (int i = 0; i < sentences.size(); i++) {
            String line = sentences.get(i).toString();
            String trans = getTrans(line);
            finalresponse = finalresponse + trans;
        }
        return finalresponse;
    }

    private static String getTrans(String sentence) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) parser.parse(sentence);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sentence = (String) obj.get("trans");
        return sentence;
    }

    private static String readURL(String url) {
        String response = "";
        try {
            URL toread = new URL(url);
            URLConnection yc = toread.openConnection();

            yc.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response = response + inputLine;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
