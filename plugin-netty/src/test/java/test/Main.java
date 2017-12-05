package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cfw.plugins.netty.http.convert.DataConverter;

import java.net.URLDecoder;

public class Main {

    public static void main(String [] args) throws Exception {
        String source = "{\"age\":20,\"gender\":true,\"name\":\"Kitty\"}";
        //source = URLDecoder.decode(source,"UTF-8");
        System.out.println(source);
        JSONArray.parseArray(source);
    }

    static class Cat {
        private String name;
        private int age;
        private boolean gender;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Cat{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age=").append(age);
            sb.append(", gender=").append(gender);
            sb.append('}');
            return sb.toString();
        }
    }
}
