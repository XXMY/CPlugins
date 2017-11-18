import com.cfw.plugins.parents.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.corba.se.spi.legacy.interceptor.RequestInfoExt;
import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class Test {

    public static void main(String [] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Request request = new Request();
        request.setService("service");
        request.setMethod("method");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add("Fangwei");
        parameterList.add(23);
        parameterList.add("wasu".getBytes());
        parameterList.add(2.2);
        HashMap<String,Cat> hashMap = new HashMap<>();
        hashMap.put("cat1",new Cat("cat1",1));
        hashMap.put("cat2",new Cat("cat2",2));
        parameterList.add(hashMap);
        request.setParameter(parameterList);
        List<String> classes = new ArrayList<>();
        for(int i=0; i<parameterList.size();i++){
            classes.add(parameterList.get(i).getClass().getName());
        }

        request.setClasses(classes);

        System.out.println();


        /*String requestString = "{\"service\":\"service\",\"method\":\"method\",\"parameter\":[\"Fangwei\",23,[119,97,115,117],2.2,{\"cat2\":{\"name\":\"cat2\",\"age\":2},\"cat1\":{\"name\":\"cat1\",\"age\":1}}]}";
        Request request = new Request();
        List<Object> parameterList = request.getParameter();
        Class<?> classes [] = new Class[parameterList.size()];
        for(int i=0; i<parameterList.size();i++){
            classes[i] = parameterList.get(i).getClass();
        }
        Cat cat = new Cat("name",20);
        Method method = cat.getClass().getDeclaredMethod(request.getMethod(),classes);

        method.invoke(cat,parameterList.toArray());*/

        /*Cat cat = new Cat("name",20);
        String methodName = "cat";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add("Fangwei");
        parameterList.add(23);
        parameterList.add("wasu".getBytes());
        parameterList.add(2.2);
        HashMap<String,Cat> hashMap = new HashMap<>();
        hashMap.put("cat1",new Cat("cat1",1));
        hashMap.put("cat2",new Cat("cat2",2));
        parameterList.add(hashMap);

        Class<?> classes [] = new Class[parameterList.size()];
        for(int i=0; i<parameterList.size();i++){
            classes[i] = parameterList.get(i).getClass();
        }

        Method method = cat.getClass().getDeclaredMethod(methodName,classes);

        method.invoke(cat,parameterList.toArray());*/
    }


}

class Request implements Serializable{

    private static final long serialVersionUID = -1869691375820681707L;
    private String service;
    private String method;
    private List<Object> parameter;
    private List<String> classes;

    public String getService() {
        return service;
    }

    void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getParameter() {
        return parameter;
    }

    void setParameter(List<Object> parameter) {
        this.parameter = parameter;
    }

    public List<String> getClasses() {
        return classes;
    }

    void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Request{" +
                "service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", parameter=" + parameter +
                ", classes=" + classes +
                '}';
    }
}

class Cat implements Serializable{

    private String name;
    private int age;

    Cat(String name,int age){
        this.name = name;
        this.age = age;
    }

    public void cat(String name,Integer age,byte [] bytes,Double price,HashMap<String,Cat> cats){
        System.out.println(name);
        System.out.println(age);
        System.out.println(new String(bytes));
        System.out.println(price);
        Set<String> keySet = cats.keySet();
        for(String key : keySet){
            System.out.println(cats.get(key));
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
