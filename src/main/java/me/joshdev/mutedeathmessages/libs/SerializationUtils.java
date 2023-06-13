package me.joshdev.mutedeathmessages.libs;

import it.unimi.dsi.fastutil.Hash;

import java.io.*;
import java.util.HashMap;

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

    public static HashMap<String, Boolean> DeserializeHashMap(String filePath){
        try{
            FileInputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Boolean> deserializedMap = (HashMap<String, Boolean>) objectInputStream.readObject(); // TODO Handle unchecked read.
            objectInputStream.close();
            inputStream.close();
            return deserializedMap;
        }catch(Exception e){
            return null;
        }
    }
}
