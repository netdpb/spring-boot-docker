/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.guestbook;

import static com.google.cloud.translate.Translate.TranslateOption.sourceLanguage;
import static com.google.cloud.translate.Translate.TranslateOption.targetLanguage;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}
}

@RestController
class HelloworldController {
  private final String version = "1.0";

  @GetMapping("/hello/{name}")
  public Map<String, String> hello(@Value("${greeting}") String greetingTemplate, @PathVariable String name) throws UnknownHostException {
    Map<String, String> response = new HashMap<>();

    String hostname = InetAddress.getLocalHost().getHostName();
    String greeting = greetingTemplate
        .replaceAll("\\$name", name)
        .replaceAll("\\$hostname", hostname)
        .replaceAll("\\$version", version);
    response.put("greeting", greeting);
    response.put("version", version);
    response.put("hostname", hostname);
    Translate translate = TranslateOptions.getDefaultInstance().getService();
    response.put("greeting_fr",
        translate.translate(greeting, sourceLanguage("en"), targetLanguage("fr"))
            .getTranslatedText());

    return response;
  }
}
