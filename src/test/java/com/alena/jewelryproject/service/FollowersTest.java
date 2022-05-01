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
        List<String> bloggers = Arrays.asList("sofia_stuzhuk", "demivika", "milalevchuk", "sanzhlena", "xenia_sobchak",
                "sub2by", "doctor_belokon", "bulgari", "tiffanyandco", "lakinails", "alina_astrovskaya_", "olgaberek",
                "hermitagecats", "fotozont", "gshockwomen", "macherie.cafe", "alenapopova", "imho_katerina", "villagemsk",
                "varlamov", "yulia_navalnaya", "yurydud", "forbes.ideas", "forbes.russia", "alenavodonaeva", "jessicachastain",
                "nikonova.online", "meduzapro", "ludmila.petranovskaya", "navalny", "forbesliferussia", "mariyakalashnikova_",
                "tominamakeup", "nikki_makeup", "lobova_art", "maxim_gilyov", "yana_panfilovskaya", "poniroma", "spichakonline",
                "kutuzova_olga_", "forbes.woman.russia", "moscowlegal", "rav_shana", "blessedyoga", "gidstockholm",
                "theofficialpandora", "sthompsonart", "anekke.official", "riva_g_", "prostocosmos_box", "mollis.style",
                "hellomag", "ivcakes", "sidorovaanastasiya", "irenaponaroshku", "muzika_nataly", "art_design.danilova",
                "petranovskaya_family3", "pro.odnushki", "9gag", "mariannaeliseeva", "wowmosaic_moscow",
                "psycholog.alexandr.shahov", "wwfrussia", "pozneronline", "ksenia_cherezova_studio", "greenpeaceru",
                "lamodaru", "life_in_tokyo", "shergina.victoria", "lichi_brand", "solnyshko_mira", "ch3rlieflow",
                "rezultatnalitso", "madam_koko_ys", "handmade_flourish", "lilac.season", "lux.furnitura", "evgeshayoga",
                "ninavaccina","bebeautymood","_infodoc_","lera_kiryakova","katyajewelry","prattprattpratt","youngmasha",
                "alicexz","jordi.koalitic","lime_official","mugimeshi323","stars_designers","gaya_av",
                "deniceemoberg","damas_denta","pin_up.studio","miss_anastasia_u","fly.dragon.fly","zestagoldy","_markov_nlp_",
                "jurma_art","reebok_russia","milavitsaofficial_russia","melfmru","bakerybymen","nasa","tinkoffjournal","georgeshobeika");
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
