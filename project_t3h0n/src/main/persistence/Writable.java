package persistence;

import org.json.JSONObject;


// Return object as JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
