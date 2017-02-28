package com.moregx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {"name=name in @SpringBootTest#properties level 3"})
//@TestPropertySource(properties = {"name=name in @TestPropertySource level 2"})
public class TestApplication {
	

    @Value("${name}")
    public String name;

    @Test
    public void test() {
        System.out.println("name: " + name);
    }

	

}
