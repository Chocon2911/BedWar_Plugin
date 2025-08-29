package org.chocon.bedwar.shopplugin.Component;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.chocon.bedwar.shopplugin.Code.IngredientCode;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class GeneratorConfigLoader {
    private final Map<String, Map<String, List<GeneratorStat>>> configs;

    public GeneratorConfigLoader(File file) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GeneratorStat.class, new JsonDeserializer<GeneratorStat>() {
                    @Override
                    public GeneratorStat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject obj = json.getAsJsonObject();
                        long delay = obj.get("delay").getAsLong();
                        int amount = obj.get("amount").getAsInt();
                        IngredientCode ingredient = IngredientCode.valueOf(obj.get("ingredient").getAsString().toUpperCase());
                        return new GeneratorStat(delay, amount, ingredient);
                    }
                })
                .create();

        Type type = new TypeToken<Map<String, Map<String, List<GeneratorStat>>>>(){}.getType();
        try (FileReader reader = new FileReader(file)) {
            configs = gson.fromJson(reader, type);
        }
    }

    public Map<Integer, List<GeneratorStat>> getStats(String type) {
        Map<String, List<GeneratorStat>> raw = configs.get(type.toLowerCase());
        if (raw == null) return new HashMap<>();

        Map<Integer, List<GeneratorStat>> result = new HashMap<>();
        for (Map.Entry<String, List<GeneratorStat>> entry : raw.entrySet()) {
            String key = entry.getKey().replace("level", "");
            int lvl = Integer.parseInt(key);
            result.put(lvl, entry.getValue());
        }
        return result;
    }
}
