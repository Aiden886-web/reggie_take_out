package com.atdahai.reiji;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggieTakeOutApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test01(){
        String path = "asf.jsp";
        System.out.println(path.substring(path.lastIndexOf(".")));
    }

}
