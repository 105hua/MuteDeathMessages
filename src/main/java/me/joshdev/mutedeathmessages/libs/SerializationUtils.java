package me.joshdev.mutedeathmessages.libs;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SerializationUtils {
    public static boolean SerializeHashMap(HashMap<String, Boolean> inputMap, String filePath){
        try{
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(inputMap);
            objectOutputStream.close();
            outputStream.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Boolean> DeserializeHashMap(String filePath){
        try{
            FileInputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object hashMapObject = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();

            if(hashMapObject instanceof HashMap<?, ?> tempHashMap){
                boolean validTypes = true;

                for(Map.Entry<?, ?> entry : tempHashMap.entrySet()){
                    if(!(entry.getKey() instanceof String) || !(entry.getValue() instanceof Boolean)){
                        validTypes = false;
                        break;
                    }
                }

                if(validTypes){
                    return (HashMap<String, Boolean>) hashMapObject;
                }
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }
}
