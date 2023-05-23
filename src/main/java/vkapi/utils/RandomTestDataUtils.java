package vkapi.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static aquality.selenium.browser.AqualityServices.getLogger;

public class RandomTestDataUtils {
    public static String generateRandomText() {
        getLogger().info("Generating a random text");
        String upperCaseRandomString = RandomStringUtils.random(1, 65, 90, true, true);
        String lowerCaseRandomString = RandomStringUtils.random(1, 97, 122, true, true);
        String numbersRandomString = RandomStringUtils.randomNumeric(1);
        String randomAlphanumericString = RandomStringUtils.randomAlphanumeric(6);
        String demoPassword = upperCaseRandomString.concat(lowerCaseRandomString)
                .concat(numbersRandomString)
                .concat(randomAlphanumericString);
        return getShuffleString(demoPassword);
    }

    private static String getShuffleString(String unshuffleString) {
        List<Character> listOfChar = unshuffleString.chars()
                .mapToObj(data -> (char) data)
                .collect(Collectors.toList());
        Collections.shuffle(listOfChar);
        return listOfChar.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}