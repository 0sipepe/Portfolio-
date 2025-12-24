import org.json.JSONObject;

public interface ISerializableJson {
    JSONObject SerializeJson();
    Boolean DeserializeJson(JSONObject o);
}
