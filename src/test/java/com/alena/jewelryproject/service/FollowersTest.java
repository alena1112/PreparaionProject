package com.alena.jewelryproject.service;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Ignore
public class FollowersTest {

    //нахождение ненужных подписок
    @Test
    public void getUselessFollowings() {
        List<String> bloggers = Arrays.asList("sofia_stuzhuk", "demivika", "milalevchuk");
        Path followersPath = Paths.get("src/test/resources/followers.txt");
        Path followingPath = Paths.get("src/test/resources/following.txt");
        List<String> followers = readFile(followersPath);
        List<String> followings = readFile(followingPath);
        followings.stream()
                .filter(following -> !followers.contains(following) && !following.contains("Подтвержденный") &&
                        !bloggers.contains(following))
                .forEach(System.out::println);
    }

    //кто отписался от меня
    @Test
    public void compareFollowers() {
        final String oldDate = "05_09";
        final String newDate = "15_09";
        Path followersPath = Paths.get("src/test/resources/followers_" + oldDate + ".txt");
        Path followingPath = Paths.get("src/test/resources/followers_" + newDate + ".txt");
        List<String> followersOld = readFile(followersPath);
        List<String> followersNew = readFile(followingPath);
        followersOld.stream()
                .filter(following -> !followersNew.contains(following))
                .forEach(System.out::println);
    }

    private List<String> readFile(Path path) {
        try (Stream<String> lineStream = Files.lines(path)) {
            return lineStream.collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
