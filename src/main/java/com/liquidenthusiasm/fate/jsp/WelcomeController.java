/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liquidenthusiasm.fate.jsp;

import com.liquidenthusiasm.fate.domain.User;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.query.OQuery;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {

    @Autowired
    OObjectDatabaseTx db;
    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @RequestMapping("/user/create")
    public String createUser(Map<String, Object> model, @RequestParam String userName, @RequestParam String password,
                             @RequestParam String displayName) {
        model.put("userCreated", false);
        ODatabaseRecordThreadLocal.INSTANCE.set(db.getUnderlying());
        OQuery<User> query = new OSQLSynchQuery<User>("select from user where loginName=?");
        List<User> duplicateUsers = db.query(query, userName);
        if (!duplicateUsers.isEmpty()) {
            model.put("flash", "User already exists");
        } else {
            User u = db.newInstance(User.class);
            u.setDisplayName(displayName);
            u.setLoginName(userName);
            u.setPassword(password);
            model.put("flash", "User created");
            model.put("userCreated", true);
            db.save(u);
        }
        return "flash";
    }

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
//        model.put("time", new Date());
//        model.put("message", this.message);

        return "welcome";
    }

}
