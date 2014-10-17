package com.liquidenthusiasm.fate.jsp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebJspApplication.class)
@WebAppConfiguration
public class WelcomeControllerTest {
    @Autowired
    WelcomeController ctrl;

    @Value("memory:inmem_for_test")
    String dbLocation;

    @Test
    public void userMustBeUnique() throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        ctrl.createUser(model, "khobbs", "password", "display");
        assertEquals(true, model.get("userCreated"));
        ctrl.createUser(model, "khobbs", "password", "display");
        assertEquals(false, model.get("userCreated"));
    }
}