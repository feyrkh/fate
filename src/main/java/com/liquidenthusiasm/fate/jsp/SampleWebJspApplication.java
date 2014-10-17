/*
 * Copyright 2012-2013 the original author or authors.
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

import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleWebJspApplication extends SpringBootServletInitializer {
    @Value("${db.location}")
    private String dbLocation;
    @Value("${spring.view.suffix}")
    private String springViewSuffix;

    // mvn verify spring-boot:run
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleWebJspApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleWebJspApplication.class);
    }

    @Bean
//    @Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OObjectDatabaseTx orientServer() throws Exception {
        OObjectDatabaseTx db = new OObjectDatabaseTx(dbLocation);
        if (!db.exists()) {
            db.create();
            db.getMetadata().getSecurity().createUser("khobbs", "admin", ORole.ADMIN);
            db.getMetadata().getSecurity().authenticate("khobbs", "admin");
            db.setAutomaticSchemaGeneration(true);
        } else {
            db.open("khobbs", "admin");
        }
        db.getEntityManager().registerEntityClasses("com.liquidenthusiasm.fate.domain");
        return db;
    }

}
