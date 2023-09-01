package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ray3k.template.*;

import java.security.MessageDigest;
import java.util.Locale;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static text.formic.Stringf.format;

public class GameScreen extends JamScreen {
    private static Music music;
    private static Music oldMusic;
    public static GameScreen gameScreen;
    public static final Color BG_COLOR = new Color();
    public Stage stage;
    public Table root;
    public static long cash;
    public static long debt;
    public final static int LOAN_MINIMUM = 250;
    public static int debtRate;
    public static int loanOffer;
    public static int loanRate;
    public final static int LOAN_OFFER_MINIMUM = 700;
    public final static int LOAN_OFF_MAXIMUM = 1500;
    public final static int LOAN_RATE_MINIMUM = 12;
    public final static int LOAN_RATE_MAXIMUM = 25;
    public static long bank;
    public static int bankRate;
    public static String weapon;
    public static int health;
    public static int healthMax;
    public final static int HEAL_PRICE = 500;
    public final static int FORTUNE_PRICE = 100;
    public static int damage;
    public static int accuracy;
    public final static int GOOD_BATTLE_OMEN_ACCURACY_BONUS = 10;
    public static String location;
    public static String building;
    public static boolean goodOmen;
    public static boolean badOmen;
    public static boolean goodBattleOmen;
    public static boolean badBattleOmen;
    public static boolean checkedFortune;
    public static String eventsText;
    public static int day;
    public static int dayMax;
    public static int inventoryCount;
    public static int inventoryMax;
    public static int beastiaryUnlock;
    public static int door;
    public static int runAwayIndex;
    public final static int DOOR_MAX = 2;
    public static boolean inCastle;
    public final static int POCKETS_OFFER_MINIMUM = 6;
    public final static int POCKETS_OFFER_MAXIMUM = 15;
    public final static int POCKETS_PRICE_MINIMUM = 200;
    public final static int POCKETS_PRICE_MAXIMUM = 500;
    public final static int CHEST_REWARD_MINIMUM = 20;
    public final static int CHEST_REWARD_MAXIMUM = 200;
    public final static int CHEST_DAMAGE_MINIMUM = 5;
    public final static int CHEST_DAMAGE_MAXIMUM = 15;
    public final static float CHEST_REWARD_CHANCE = .6f;
    public final static int SHARPENING_DAMAGE_MINIMUM = 3;
    public final static int SHARPENING_DAMAGE_MAXIMUM = 5;
    public final static int SHARPENING_PRICE_MINIMUM = 500;
    public final static int SHARPENING_PRICE_MAXIMUM = 1000;
    public final static int FORMULA_HEALTH_MINIMUM = 5;
    public final static int FORMULA_HEALTH_MAXIMUM = 15;
    public final static int FORMULA_PRICE_MINIMUM = 200;
    public final static int FORMULA_PRICE_MAXIMUM = 600;
    public final static float TRAVEL_EVENT_CHANCE = .2f;
    public final static IntArray battleIntervals = new IntArray();
    public final static ObjectMap<String, Integer> inventory = new ObjectMap<>();
    public final static ObjectMap<String, Integer> averagePaidPrice = new ObjectMap<>();
    public final static Array<String> vampires = new Array<>(new String[] {"Beast Vampire", "Bride Vampire", "Noble Vampire", "Pharaoh Vampire"});
    public final static Array<String> vampireImages = new Array<>(new String[] {"beast", "bride", "noble", "pharaoh"});
    public final static IntArray vampireDamages = new IntArray(new int[] {5, 10, 15, 20});
    public final static IntArray vampireAccuracies = new IntArray(new int[] {50, 60, 65, 70});
    public final static IntArray rewards = new IntArray(new int[] {2000, 3000, 10000, 100000});
    public static int vampireHealth;
    public final static int VAMPIRE_HEALTH_MAX = 100;
    public final static int BAD_OMEN_VAMPIRE_ACCURACY_BONUS = 10;
    public final static Array<String> locations = new Array<>(new String[] {"Transylvania", "Wallachia", "Moldavia", "Dobruja", "Bukovina", "Crisana"});
    public final static Array<String> buildings = new Array<>(new String[] {"Castle", "Bank", "Weapons Dealer", "Soothsayer", "Loan Shark", "Healer"});
    public final static Music[] locationMusics = new Music[] {bgm_Transylvania, bgm_WallachiaAndBukovina, bgm_Moldovia, bgm_DobrujaAndCrisana, bgm_WallachiaAndBukovina, bgm_DobrujaAndCrisana};
    public final static Array<String> surplusEventTexts = new Array<>();
    public final static Array<String> bustEventTexts = new Array<>();
    public final static Array<String> surplusItems = new Array<>();
    public final static Array<String> bustItems = new Array<>();
    public final static Array<String> items = new Array<>(new String[] {"Swords", "Stakes", "Garlic", "Holy Water",
            "Silver Bullets", "Crucifixes", "Mirrors", "Sacraments", "Bobbles", "Holy Books", "Torches", "Charms"});
    public final static IntArray prices = new IntArray();
    public final static ObjectMap<String, FloatArray> locationPreferences = new ObjectMap<>();
    public final static float dailyPreferenceMin = -.2f;
    public final static float dailyPreferenceMax = .2f;
    public final static float bustPreferenceMin = 2f;
    public final static float bustPreferenceMax = 5f;
    public final static float surplusPreferenceMin = -.5f;
    public final static float surplusPreferenceMax = -.3f;
    public final static IntArray basePrices = new IntArray(new int[]{10000, 100, 250, 800, 50000, 1000, 5000, 20, 10, 2500, 500, 60});
    public final static Array<String> weapons = new Array<>(new String[] {"Holy Sabre", "Anointed Whip", "Hand Cannon", "Devil's Crossbow", "Sol Grenada", "Lance of Longinus"});
    public final static Sound[] weaponSounds = new Sound[] {sfx_slash, sfx_whip, sfx_gun, sfx_slash, sfx_grenade, sfx_slash};
    public static Sound weaponSound;
    public final static IntArray weaponDamages = new IntArray(new int[] {10, 15, 25, 20, 50, 25});
    public final static IntArray weaponAccuracies = new IntArray(new int[] {60, 65, 50, 70, 40, 75});
    public final static IntArray weaponPrices = new IntArray(new int[] {1800, 2500, 5600, 7100, 20300, 100700});
    public final static FloatArray runawayChances = new FloatArray(new float[] {.25f, .15f, .1f, .05f});
    public final static float SLIDER_MIN_WIDTH = 300;
    
    private void newGame() {
        surplusEventTexts.clear();
        surplusEventTexts.addAll(Gdx.files.internal("surplus.txt").readString("UTF-8").split("\\n"));
        
        bustEventTexts.clear();
        bustEventTexts.addAll(Gdx.files.internal("bust.txt").readString("UTF-8").split("\\n"));
        
        cash = 50;
        debt = 0;
        debtRate = 0;
        bank = 0;
        bankRate = 10;
        inventoryCount = 0;
        inventoryMax = 100;
        weapon = null;
        weaponSound = sfx_slash;
        healthMax = 100;
        health = healthMax;
        damage = 0;
        accuracy = 0;
        runAwayIndex = 0;
        day = -1;
        dayMax = 30;
        inventory.clear();
        averagePaidPrice.clear();
        beastiaryUnlock = 0;
        battleIntervals.clear();
        
        bgm_DobrujaAndCrisana.setLooping(true);
        bgm_Moldovia.setLooping(true);
        bgm_Transylvania.setLooping(true);
        bgm_WallachiaAndBukovina.setLooping(true);
        bgm_below50Hp.setLooping(true);
        bgm_hasDebtOrMoneyInBank.setLooping(true);
        bgm_noMoneyOrItemsOnHand.setLooping(true);
        bgm_underAttack.setLooping(true);
        bgm_main.setLooping(true);
        bgm_ResultsLOOP.setLooping(true);
        
        bgm_DobrujaAndCrisana.setVolume(0);
        bgm_Moldovia.setVolume(0);
        bgm_Transylvania.setVolume(0);
        bgm_WallachiaAndBukovina.setVolume(0);
        bgm_below50Hp.setVolume(0);
        bgm_hasDebtOrMoneyInBank.setVolume(0);
        bgm_noMoneyOrItemsOnHand.setVolume(0);
        bgm_underAttack.setVolume(0);
        bgm_main.setVolume(0);
        bgm_ResultsINTRO.setVolume(bgm);
        bgm_ResultsLOOP.setVolume(0);
        
        bgm_DobrujaAndCrisana.play();
        bgm_Moldovia.play();
        bgm_Transylvania.play();
        bgm_WallachiaAndBukovina.play();
        bgm_below50Hp.play();
        bgm_hasDebtOrMoneyInBank.play();
        bgm_noMoneyOrItemsOnHand.play();
        bgm_underAttack.play();
        bgm_main.play();
        
        fadeInMusic(bgm_main);
        fadeInMusic(bgm_WallachiaAndBukovina);
        music = bgm_WallachiaAndBukovina;
        
        locationPreferences.clear();
        for (var location : locations) {
            var preferences = new FloatArray();
            locationPreferences.put(location, preferences);
            
            for (var i = 0; i < basePrices.size; i++) {
                preferences.add(MathUtils.random(-.3f, .3f));
            }
        }
        
        var totalBattles = MathUtils.random(4, 6);
        for (int i = 0; i < totalBattles; i++) {
            var indexMin = Math.max(6, dayMax / totalBattles * i + 1);
            var indexMax = Math.max(indexMin, i == totalBattles - 1 ? dayMax : Math.max(6, dayMax / totalBattles * (i + 1)));
            battleIntervals.add(MathUtils.random(indexMin, indexMax));
        }
        
        newDay(4);
    }
    
    private void newDay(int locationIndex) {
        day++;
        location = locations.get(locationIndex);
        building = buildings.get(locationIndex);
        var music = locationMusics[locationIndex];
        prices.clear();
        surplusItems.clear();
        bustItems.clear();
        eventsText = null;
        loanOffer = MathUtils.random(LOAN_OFFER_MINIMUM, LOAN_OFF_MAXIMUM);
        loanRate =  MathUtils.random(LOAN_RATE_MINIMUM, LOAN_RATE_MAXIMUM);
        if (debt > 0) debt += debtRate * debt / 100;
        if (bank > 0) bank += bankRate * bank / 100;
        checkedFortune = false;
        door = 0;
        inCastle = false;
        
        var preferences = locationPreferences.get(location);
        
        var showSpecialEvent = day != 0 && MathUtils.randomBoolean(TRAVEL_EVENT_CHANCE);
        
        var surplusEventIndexes = new IntArray();
        for (int i = 0; i < basePrices.size; i++) {
            surplusEventIndexes.add(i);
        }
        surplusEventIndexes.shuffle();
        if (day == 0 || MathUtils.randomBoolean(.7f) && !goodOmen) surplusEventIndexes.clear();
        else if (MathUtils.randomBoolean(.8f)) surplusEventIndexes.removeRange(1, surplusEventIndexes.size - 1);
        else surplusEventIndexes.removeRange(2, surplusEventIndexes.size - 1);
        for (int i = 0; i < surplusEventIndexes.size; i++) {
            var index = surplusEventIndexes.get(i);
            var item = items.get(index);
            surplusItems.add(item);
            
            if (eventsText == null) eventsText = "";
            eventsText += "\n" + surplusEventTexts.random().replaceAll("@", item.toUpperCase(Locale.ROOT));
        }
        
        var bustEventIndexes = new IntArray();
        for (int i = 0; i < basePrices.size; i++) {
            if (!surplusEventIndexes.contains(i)) bustEventIndexes.add(i);
        }
        bustEventIndexes.shuffle();
        if (day == 0 || MathUtils.randomBoolean(.7f) && !badOmen) bustEventIndexes.clear();
        else if (MathUtils.randomBoolean(.8f)) bustEventIndexes.removeRange(1, bustEventIndexes.size - 1);
        else bustEventIndexes.removeRange(2, bustEventIndexes.size - 1);
        for (int i = 0; i < bustEventIndexes.size; i++) {
            var index = bustEventIndexes.get(i);
            var item = items.get(index);
            bustItems.add(item);
            
            if (eventsText == null) eventsText = "";
            eventsText += "\n" + bustEventTexts.random().replaceAll("@", item.toUpperCase(Locale.ROOT));
        }
        
        for (var i = 0; i < basePrices.size; i++) {
            var basePrice = basePrices.get(i);
            var price = basePrice + preferences.get(i) * basePrice + MathUtils.random(dailyPreferenceMin, dailyPreferenceMax) * basePrice;
            if (bustEventIndexes.contains(i)) price += basePrice * MathUtils.random(bustPreferenceMin, bustPreferenceMax);
            if (surplusEventIndexes.contains(i)) price += basePrice * MathUtils.random(surplusPreferenceMin, surplusPreferenceMax);
            prices.add(Math.max(MathUtils.round(price), 5));
        }
        
        if (day == 0) {
            showWelcomeMessage();
            transitionMusic(music);
        } else if (battleIntervals.size > 0 && day == battleIntervals.first() && beastiaryUnlock < vampires.size) {
            battleIntervals.removeIndex(0);
            transitionMusic(bgm_underAttack);
            showBattle();
        } else if (showSpecialEvent) {
            transitionMusic(music);
            switch (MathUtils.random(7)) {
                case 0:
                    showPocketEvent();
                    break;
                case 1:
                    showChestEvent();
                    break;
                case 2:
                    showSharpenEvent();
                    break;
                case 3:
                    showWeaponEvent();
                    break;
                case 4:
                    showBrigandEvent();
                    break;
                case 5:
                    showMobEvent();
                    break;
                case 6:
                    showSalesmanEvent();
                    break;
                case 7:
                    showHealEvent();
                    break;
            }
        } else if (eventsText != null) {
            showEventsDialog();
            transitionMusic(music);
        } else {
            transitionMusic(music);
        }
        
        goodOmen = false;
        badOmen = false;
    }
    
    @Override
    public void show() {
        super.show();
        
        try {
            GameJoltApi.crypt = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        gameScreen = this;
        BG_COLOR.set(Color.BLACK);
    
        stage = new Stage(new FitViewport(1024, 576), batch);
        
        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Gdx.input.setInputProcessor(stage);
        
        newGame();
        
        populate();
    }
    
    private void populate() {
        root.clear();
        
        root.defaults().space(60).growY().width(900/3);
        root.pad(20);
        
        //left column
        var table = new Table();
        table.top().right();
        root.add(table);
        
        table.columnDefaults(0).right();
        table.defaults().spaceLeft(20);
        var label = new Label("CASH", skin, "big");
        table.add(label);
        
        table.columnDefaults(1).left();
        
        label = new Label("$" + formatMoney(cash), skin, "cash");
        table.add(label);
        
        table.row();
        label = new Label("DEBT", skin, "cash-white");
        table.add(label);
        
        var subTable = new Table();
        table.add(subTable);
        
        label = new Label("$" + formatMoney(debt), skin, "debt");
        subTable.add(label);
        
        if (debt != 0) {
            label = new Label(" at " + debtRate + "%", skin, "debt-subtitle");
            subTable.add(label).space(5).bottom().padBottom(10);
        }
        
        table.row();
        label = new Label("BANK", skin, "cash-white");
        table.add(label);
        
        subTable = new Table();
        table.add(subTable);
        
        label = new Label("$" + formatMoney(bank), skin, "cash");
        subTable.add(label);
        
        label = new Label(" at " + bankRate + "%", skin, "cash-subtitle");
        subTable.add(label).space(5).bottom().padBottom(10);
        
        table.row().padTop(120);
        label = new Label("INVENTORY", skin, "inventory");
        table.add(label);
        
        label = new Label(inventoryCount + "/" + inventoryMax, skin, "inventory");
        table.add(label);
        
        table.row();
        var textButton = new TextButton("BEASTIARY", skin);
        table.add(textButton).expandY().bottom().right().colspan(2);
        onChange(textButton, () -> {
            showBeastiary(0);
        });
        
        table.row();
        textButton = new TextButton("QUIT GAME", skin);
        table.add(textButton).right().padBottom(20).colspan(2);
        onChange(textButton, this::showQuitGame);
        
        //middle column
        table = new Table();
        root.add(table);
        
        subTable = new Table();
        table.add(subTable).growX();
        
        subTable.defaults().growX();
        
        
        for (var item : items) {
            var price = prices.get(items.indexOf(item, false));
            var itemCount = inventory.get(item, 0);
            
            if (!item.equals(items.first())) subTable.row();
            
            var button = new Button(skin);
            subTable.add(button);
            
            var color = surplusItems.contains(item, false) ? Color.GREEN : bustItems.contains(item, false) ? Color.RED :
                    (price > cash || inventoryCount >= inventoryMax) && itemCount == 0 ? Color.GRAY : Color.WHITE;
            
            var inventoryString = inventory.containsKey(item) ? Integer.toString(inventory.get(item)) : "0";
            label = new Label(inventoryString, skin, "small");
            label.setColor(color);
            button.add(label).spaceRight(30);
            
            label = new Label(item, skin, "small");
            label.setColor(color);
            button.add(label).growX().spaceRight(30);
            
            if ((price > cash || inventoryCount >= inventoryMax) && itemCount == 0) button.setDisabled(true);
            
            label = new Label("$" + formatMoney(price), skin, "small");
            label.setColor(color);
            button.add(label).spaceRight(30);
            
            if (!item.equals(items.peek())) {
                subTable.row();
                var image = new Image(skin, "divider");
                subTable.add(image);
            }
            
            onChange(button, () -> {
                showSelectedItem(item);
            });
        }
        
        table.row();
        textButton = new TextButton("VISIT " + building.toUpperCase(Locale.ROOT), skin);
        table.add(textButton).padTop(20);
        onChange(textButton, () -> {
            if (building.equals("Loan Shark")) {
                showLoan();
            } else if (building.equals("Bank")) {
                showBank();
            } else if (building.equals("Weapons Dealer")) {
                showWeaponsDealer();
            } else if (building.equals("Soothsayer")) {
                showSoothsayer();
            } else if (building.equals("Castle")) {
                showCastle();
            } else if (building.equals("Healer")) {
                showHealer();
            }
        });
        
        table.row();
        textButton = new TextButton(day >= dayMax ? "BOARD SHIP" : "TRAVEL", skin, "big");
        table.add(textButton);
        onChange(textButton, () -> {
            if (day >= dayMax) showFinish();
            else showTravel();
        });
        
        //right column
        table = new Table();
        root.add(table);
        
        table.defaults().expandX().left();
        label = new Label(location.toUpperCase(Locale.ROOT), skin, "region");
        table.add(label);
        
        table.row();
        label = new Label("DAY " + day, skin, "big");
        table.add(label).padTop(40);
        
        table.row();
        label = new Label("OF " + dayMax, skin, "gray");
        table.add(label);
        
        table.row();
        var image = new Image(skin, health >= 75 ? "player-0" : health >= 50 ? "player-1" : health >= 25 ? "player-2" : "player-3");
        table.add(image).padTop(40);
        
        table.defaults().space(10);
        table.row();
        label = new Label("HEALTH " + health, skin, "inventory");
        table.add(label);
        
        if (weapon != null) {
            table.row();
            label = new Label(weapon.toUpperCase(Locale.ROOT), skin, "inventory");
            table.add(label);
        }
    }
    
    @Override
    public void act(float delta) {
        var playingResults = music == bgm_ResultsINTRO;
        if (playingResults && !music.isPlaying()) {
            music = bgm_ResultsLOOP;
            music.setVolume(bgm);
            music.play();
        }
        
        if (health < 50 && !playingResults) {
            bgm_below50Hp.setVolume(Utils.approach(bgm_below50Hp.getVolume(), bgm, .5f * delta));
        } else {
            bgm_below50Hp.setVolume(Utils.approach(bgm_below50Hp.getVolume(), 0, .5f * delta));
        }
        
        if ((debt > 0 || bank > 0) && !playingResults) {
            bgm_hasDebtOrMoneyInBank.setVolume(Utils.approach(bgm_hasDebtOrMoneyInBank.getVolume(), bgm, .5f * delta));
        } else {
            bgm_hasDebtOrMoneyInBank.setVolume(Utils.approach(bgm_hasDebtOrMoneyInBank.getVolume(), 0, .5f * delta));
        }
        
        if (cash == 0 && inventoryCount == 0 && !playingResults) {
            bgm_noMoneyOrItemsOnHand.setVolume(Utils.approach(bgm_noMoneyOrItemsOnHand.getVolume(), bgm, .5f * delta));
        } else {
            bgm_noMoneyOrItemsOnHand.setVolume(Utils.approach(bgm_noMoneyOrItemsOnHand.getVolume(), 0, .5f * delta));
        }
        
        stage.act(delta);
    }
    
    public static void fadeInMusic(Music music) {
        gameScreen.stage.addAction(new TemporalAction(2f) {
            @Override
            protected void update(float percent) {
                music.setVolume(percent * bgm);
            }
        });
    }
    
    public static void fadeOutMusic(Music music) {
        gameScreen.stage.addAction(new TemporalAction(2f) {
            @Override
            protected void update(float percent) {
                music.setVolume((1 - percent) * bgm);
            }
        });
    }
    
    public static void transitionMusic(Music newMusic) {
        if (music != newMusic) {
            oldMusic = music;
            music = newMusic;
            music.setVolume(0);
            gameScreen.stage.addAction(new TemporalAction(2f) {
                @Override
                protected void end() {
                    music.setVolume(bgm);
                }

                @Override
                protected void update(float percent) {
                    oldMusic.setVolume((1 - percent) * bgm);
                    music.setVolume(percent * bgm);
                }
            });
        }
    }
    
    public static void stopMusic() {
        bgm_DobrujaAndCrisana.stop();
        bgm_Moldovia.stop();
        bgm_Transylvania.stop();
        bgm_WallachiaAndBukovina.stop();
        bgm_below50Hp.stop();
        bgm_hasDebtOrMoneyInBank.stop();
        bgm_noMoneyOrItemsOnHand.stop();
        bgm_underAttack.stop();
        bgm_main.stop();
        bgm_ResultsINTRO.stop();
        bgm_ResultsLOOP.stop();
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().apply();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        if (width + height != 0) {
            stage.getViewport().update(width, height, true);
        }
    }
    
    @Override
    public void dispose() {
    }
    
    @Override
    public void hide() {
        super.hide();
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    private void showEventsDialog() {
        var dialog = new Dialog("EVENTS", skin);
        
        var table = new Table();
        var scrollPane = new ScrollPane(table, skin);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        dialog.getContentTable().add(scrollPane).grow();
        
        table.pad(20);
        var label = new Label(eventsText, skin, "small");
        table.add(label);
        
        var textButton = new TextButton("OK", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
        stage.setScrollFocus(scrollPane);
    }
    
    private void showQuitGame() {
        var dialog = new Dialog("QUIT GAME?", skin);
        
        var label = new Label("Are you sure you want to quit? All progress will be lost and your score won't be recorded.", skin, "small");
        label.setWrap(true);
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label).growX();
        
        var textButton = new TextButton("QUIT GAME", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            Gdx.input.setInputProcessor(null);
            core.transition(new MenuScreen());
            stopMusic();
        });
        
        textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(800, 250);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showTravel() {
        var dialog = new Dialog("TRAVEL", skin);
        
        dialog.getContentTable().defaults().space(20).left();
        dialog.getContentTable().padTop(20).padLeft(50).padRight(50);
        for (int i = 0; i < locations.size; i++) {
            var location = locations.get(i);
            var building = buildings.get(i);
            
            if (i % 2 == 0) dialog.getContentTable().row();
            var button = new Button(skin);
            dialog.getContentTable().add(button);
            
            button.defaults().left();
            var label = new Label(location.toUpperCase(Locale.ROOT), skin);
            if (location.equals(GameScreen.location)) {
                button.setDisabled(true);
                label.setStyle(skin.get("gray", LabelStyle.class));
            }
            button.add(label);
            
            button.row();
            label = new Label(building.toUpperCase(Locale.ROOT), skin, "travel-subtitle");
            button.add(label);
            
            button.setUserObject(i);
            
            onChange(button, () -> {
                var index = (int) button.getUserObject();
                newDay(index);
                populate();
                dialog.hide();
            });
        }
        
        var textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
    }
    
    private void showFinish() {
        fadeOutMusic(bgm_main);
        bgm_ResultsINTRO.play();
        transitionMusic(bgm_ResultsINTRO);
        var dialog = new Dialog("THE SHIP DEPARTS", skin);
        
        var table = dialog.getContentTable();
        
        table.defaults().space(20);
        table.pad(10);
        var label = new Label(Gdx.files.internal("conclusion1.txt").readString(), skin, "small");
        label.setWrap(true);
        table.add(label).growX();
        
        dialog.getContentTable().row();
        var image = new Image(skin, "title-small");
        table.add(image);
        
        dialog.getContentTable().row();
        label = new Label(Gdx.files.internal("conclusion2.txt").readString(), skin, "small");
        label.setWrap(true);
        table.add(label).growX();
        
        table.row();
        var subTable = new Table();
        table.add(subTable);
        
        subTable.defaults().space(5);
        label = new Label("Name:", skin, "small");
        subTable.add(label);
        
        var textField = new TextField(MenuScreen.lastScoreName, skin);
        subTable.add(textField);
        
        MenuScreen.lastScore = cash + bank - debt;
        label = new Label("$" + formatMoney(MenuScreen.lastScore), skin, "small");
        subTable.add(label);
        
        var textButton = new TextButton("FINISH", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            MenuScreen.lastScoreName = textField.getText();
            MenuScreen.lastScore = Math.max(cash + bank - debt, 0);
            if (MenuScreen.lastScore > preferences.getLong("highscore", -Long.MAX_VALUE)) {
                preferences.putString("highscore-name", textField.getText());
                preferences.putLong("highscore", MenuScreen.lastScore);
                preferences.flush();
            }
            
            GameJoltApi.addHighScore(MenuScreen.lastScore, textField.getText());
            
            Gdx.input.setInputProcessor(null);
            core.transition(new MenuScreen());
            stopMusic();
        });
        
        textButton.setDisabled(textField.getText().equals(""));
        onChange(textField, () -> textButton.setDisabled(textField.getText().equals("")));
        
        dialog.show(stage);
        stage.setKeyboardFocus(textField);
        dialog.setSize(600, 450);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showLoan() {
        var dialog = new Dialog("LOAN SHARK", skin);
        
        var table = dialog.getContentTable();
        
        if (debt == 0) {
            var label = new Label("The local mafia is offering up to $" + formatMoney(loanOffer) + " at " + loanRate + "% daily interest.\nThey assure you it's a great deal!", skin, "small");
            table.add(label).colspan(2);
            
            table.row();
            var slider = new Slider(LOAN_MINIMUM, loanOffer, 1, false, skin);
            table.add(slider).minWidth(SLIDER_MIN_WIDTH).growX();
            
            table.row();
            var loanLabel = new Label("$" + formatMoney(MathUtils.round(slider.getValue())), skin, "debt");
            table.add(loanLabel);
            onChange(slider, () -> loanLabel.setText("$" + formatMoney(MathUtils.round(slider.getValue()))));
            
            var textButton = new TextButton("ACCEPT", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_money.play(sfx);
                debt = MathUtils.round(slider.getValue());
                debtRate = loanRate;
                cash += debt;
                populate();
            });
            
            textButton = new TextButton("CANCEL", skin);
            dialog.button(textButton);
        } else if (cash > 0) {
            var label = new Label("You owe the mafia $" + formatMoney(debt) + " at " + debtRate + "% daily interest.\n\"It's the dough, or we gotta break something\"", skin, "small");
            table.add(label).colspan(2);
            
            table.row();
            var slider = new Slider(1, Math.min(debt, cash), 1, false, skin);
            slider.setValue(slider.getMaxValue());
            table.add(slider).minWidth(SLIDER_MIN_WIDTH);
            
            table.row();
            var loanLabel = new Label("$" + formatMoney(MathUtils.round(slider.getValue())), skin, "debt");
            table.add(loanLabel);
            onChange(slider, () -> loanLabel.setText("$" + formatMoney(MathUtils.round(slider.getValue()))));
            
            var textButton = new TextButton("PAY", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_money.play(sfx);
                var value = Math.max(MathUtils.round(slider.getValue()), 0);
                debt -= value;
                if (debt == 0) debtRate = 0;
                cash -= value;
                populate();
            });
            
            textButton = new TextButton("ACCEPT BEATING", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_hurt.play(sfx);
                health--;
                if (health <= 0) {
                    health = 0;
                    showGameOver();
                }
                populate();
            });
        } else {
            var label = new Label("You owe the mafia $" + debt + " at " + debtRate +
                    "% daily interest.\n\"It's the dough, or we gotta break something\nYou know, this is for your own good...\"", skin, "small");
            table.add(label);
            
            var textButton = new TextButton("ACCEPT BEATING", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_hurt.play(sfx);
                health--;
                if (health <= 0) {
                    health = 0;
                    showGameOver();
                }
                populate();
            });
        }
        
        dialog.show(stage);
    }
    
    private void showBank() {
        //withdraw dialog
        var dialogWithdraw = new Dialog("BANK", skin);
        
        var table = dialogWithdraw.getContentTable();
        
        var label = new Label("You have $" + formatMoney(bank) + " in the bank.\nInterest is compounded daily at " + bankRate + "%.", skin, "small");
        table.add(label).colspan(2);
        
        table.row();
        var withdrawSlider = new Slider(1, bank == 0 ? Integer.MAX_VALUE : bank, 1, false, skin);
        withdrawSlider.setValue(withdrawSlider.getMaxValue());
        table.add(withdrawSlider).minWidth(SLIDER_MIN_WIDTH);
        
        table.row();
        var withdrawLabel = new Label("$" + formatMoney(MathUtils.round(withdrawSlider.getValue())), skin, "debt");
        table.add(withdrawLabel);
        onChange(withdrawSlider, () -> withdrawLabel.setText("$" + formatMoney(MathUtils.round(withdrawSlider.getValue()))));
        
        var textButton = new TextButton("WITHDRAW", skin);
        dialogWithdraw.button(textButton);
        onChange(textButton, () -> {
            sfx_money.play(sfx);
            var value = MathUtils.round(withdrawSlider.getValue());
            cash += value;
            bank -= value;
            populate();
        });
        
        textButton = new TextButton("CANCEL", skin);
        dialogWithdraw.button(textButton);
        
        //deposit dialog
        var dialogDeposit = new Dialog("BANK", skin);
        
        table = dialogDeposit.getContentTable();
        
        label = new Label("You have $" + formatMoney(bank) + " in the bank.\nInterest is compounded daily at " + bankRate + "%.", skin, "small");
        table.add(label).colspan(2);
        
        table.row();
        var depositSlider = new Slider(1, cash == 0 ? Integer.MAX_VALUE : cash, 1, false, skin);
        depositSlider.setValue(depositSlider.getMaxValue());
        table.add(depositSlider).minWidth(SLIDER_MIN_WIDTH);
        
        table.row();
        var depositLabel = new Label("$" + formatMoney(MathUtils.round(depositSlider.getValue())), skin, "debt");
        table.add(depositLabel);
        onChange(depositSlider, () -> depositLabel.setText("$" + formatMoney(MathUtils.round(depositSlider.getValue()))));
        
        textButton = new TextButton("DEPOSIT", skin);
        dialogDeposit.button(textButton);
        onChange(textButton, () -> {
            sfx_money.play(sfx);
            var value = MathUtils.round(depositSlider.getValue());
            cash -= value;
            bank += value;
            populate();
        });
        
        textButton = new TextButton("CANCEL", skin);
        dialogDeposit.button(textButton);
        
        //main dialog
        var dialogMain = new Dialog("BANK", skin);
        
        table = dialogMain.getContentTable();
        
        if (bank > 0) {
            textButton = new TextButton("WITHDRAW", skin);
            table.add(textButton);
            onChange(textButton, () -> {
                dialogWithdraw.show(stage);
                dialogMain.hide();
            });
        }
        
        if (cash > 0) {
            textButton = new TextButton("DEPOSIT", skin);
            table.add(textButton);
            onChange(textButton, () -> {
                dialogDeposit.show(stage);
                dialogMain.hide();
            });
        }
        
        if (bank == 0 && cash == 0) {
            label = new Label("You have no money.\n\"We don't do business with people of your stature\"", skin);
            table.add(label);
        }
        
        textButton = new TextButton("CANCEL", skin);
        dialogMain.button(textButton);
        
        dialogMain.show(stage);
    }
    
    private void showGameOver() {
        sfx_die.play(sfx);
        var dialog = new Dialog("THE DEARLY DEPARTED", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        var label = new Label(Gdx.files.internal("gameover1.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        var image = new Image(skin, "title-small");
        dialog.getContentTable().add(image);
        
        dialog.getContentTable().row();
        label = new Label(Gdx.files.internal("gameover2.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        var textButton = new TextButton("FINISH", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            Gdx.input.setInputProcessor(null);
            core.transition(new MenuScreen());
            stopMusic();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 550);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showHealer() {
        var dialog = new Dialog("HEALING", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        var label = new Label(Gdx.files.internal("healing.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label("You have " + health + " of " + healthMax + " health.", skin, "small");
        dialog.getContentTable().add(label);
        
        dialog.getContentTable().row();
        label = new Label(cash >= HEAL_PRICE ? "Heal for $" + HEAL_PRICE + "." : "You cannot afford the \"donation\" of $" + HEAL_PRICE + ".", skin, "small");
        dialog.getContentTable().add(label);
        
        if (cash >= HEAL_PRICE) {
            var textButton = new TextButton("PURCHASE HEALING", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_heal.play(sfx);
                cash -= HEAL_PRICE;
                health = healthMax;
                populate();
            });
        }
        
        var textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(600, 400);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showWeaponsDealer() {
        var foundWeapon = weapon == null;
        var dialog = new Dialog("WEAPONS DEALER", skin);
        
        var label = new Label("\"You buy. Tell friend 'Vamp Store Number 1!' Okay? Yes!\"", skin, "small");
        dialog.getContentTable().add(label).colspan(3);
        
        dialog.getContentTable().defaults().space(20).left();
        dialog.getContentTable().padTop(20).padLeft(50).padRight(50);
        for (int i = 0; i < weapons.size; i++) {
            var weapon = weapons.get(i);
            var weaponSound = weaponSounds[i];
            var accuracy = weaponAccuracies.get(i);
            var damage = weaponDamages.get(i);
            var price = weaponPrices.get(i);
            var owned = weapon.equals(GameScreen.weapon);
            if (owned) foundWeapon = true;
            
            if (i % 3 == 0) dialog.getContentTable().row();
            var button = new Button(skin);
            dialog.getContentTable().add(button);
            
            button.defaults().left();
            label = new Label(weapon.toUpperCase(Locale.ROOT), skin, "small");
            if (owned) {
                button.setDisabled(true);
                label.setStyle(skin.get("small-gray", LabelStyle.class));
            } else if (cash < price) {
                button.setDisabled(true);
                label.setColor(Color.RED);
            }
            button.add(label);
            
            button.row();
            if (!owned) label = new Label("Damage: " + damage + " Accuracy: " + accuracy + "%", skin, "very-small");
            else label = new Label("Damage: " + GameScreen.damage + " Accuracy: " + GameScreen.accuracy + "%", skin, "very-small");
            button.add(label);
            
            button.row();
            label = new Label("$" + formatMoney(price), skin, "very-small");
            button.add(label);
            
            if (owned || cash >= price) {
                button.row();
                label = new Label(owned ? "OWNED" : "BUY", skin, "small");
                button.add(label).right();
            }
            
            onChange(button, () -> {
                sfx_inventory.play(sfx);
                GameScreen.weapon = weapon;
                GameScreen.weaponSound = weaponSound;
                GameScreen.damage = damage;
                GameScreen.accuracy = accuracy;
                cash -= price;
                populate();
                dialog.hide();
            });
        }
        
        if (!foundWeapon) {
            dialog.getContentTable().row();
            var table = new Table();
            dialog.getContentTable().add(table).colspan(3).center();
            
            label = new Label("You have a rare weapon equipped. Yay you!", skin, "small");
            table.add(label);
            
            table.row();
            label = new Label(weapon.toUpperCase(Locale.ROOT), skin, "small-gray");
            table.add(label);
            
            table.row();
            label = new Label("Damage: " + damage + " Accuracy: " + accuracy + "%", skin, "very-small");
            table.add(label);
        }
        
        var textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
    }
    
    private void showSoothsayer() {
        var canAfford = cash >= FORTUNE_PRICE;
        var dialog = new Dialog("SOOTHSAYER", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        var text = Gdx.files.internal("soothsayer.txt").readString();
        text = text.replaceAll("@", Integer.toString(FORTUNE_PRICE));
        var label = new Label(text, skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label(canAfford ? !checkedFortune ?
                "Choose a card:" : "\"You've siphoned all my energy and I must withdraw for the day...\"" : "You cannot afford this service, peasant.", skin, "small");
        dialog.getContentTable().add(label);
        
        if (canAfford && !checkedFortune) {
            var textButton = new TextButton("MARKET TAROT", skin);
            dialog.button(textButton);
            onChange(textButton, this::showMarketTarot);
            
            textButton = new TextButton("BATTLE TAROT", skin);
            dialog.button(textButton);
            onChange(textButton, this::showBattleTarot);
            
            textButton = new TextButton("LUCK TAROT", skin);
            dialog.button(textButton);
            onChange(textButton, this::showLuckTarot);
        }
        
        dialog.getButtonTable().row().colspan(3);
        var textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(600, 350);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showMarketTarot() {
        sfx_magic.play(sfx);
        checkedFortune = true;
        var dialog = new Dialog("SOOTHSAYER", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        var text = Gdx.files.internal("marketfortune.txt").readString();
        var label = new Label(text, skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        GameScreen.goodOmen = true;
        
        var location = locations.random();
        var favored = "";
        var favoredScore = -Float.MAX_VALUE;
        var prefs = locationPreferences.get(location);
        for (int i = 0; i < prefs.size; i++) {
            var score = prefs.get(i);
            if (score > favoredScore) {
                favoredScore = score;
                favored = items.get(i);
            }
        }
        
        dialog.getContentTable().row();
        label = new Label("\"" + location + " prefers " + favored + " at a " + MathUtils.round(favoredScore * 100) + "% bonus. Thank you, come again.\"", skin, "small");
        dialog.getContentTable().add(label);
        
        dialog.getButtonTable().row().colspan(3);
        var textButton = new TextButton("OK", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            cash -= FORTUNE_PRICE;
            populate();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 350);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showBattleTarot() {
        sfx_magic.play(sfx);
        checkedFortune = true;
        var index = MathUtils.random(2);
        var dialog = new Dialog("SOOTHSAYER", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (index == 0) {
            var text = Gdx.files.internal("goodbattleomen.txt").readString();
            var label = new Label(text, skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            GameScreen.goodBattleOmen = true;
        } else if (index == 1) {
            var text = Gdx.files.internal("badbattleomen.txt").readString();
            var label = new Label(text, skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            GameScreen.badBattleOmen = true;
        } else if (index == 2) {
            var suites = new Array<>(new String[]{"hearts", "diamonds", "clubs", "spades"});
            var text = "You draw the following cards:\n";
            for (int i = 0; i < battleIntervals.size; i++) {
                text += battleIntervals.get(i) + " of " + suites.random() + (i == battleIntervals.size - 1 ? "" :", ");
            }
            text += "\n" + Gdx.files.internal("cards.txt").readString();
            var label = new Label(text, skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
        }
        
        dialog.getButtonTable().row().colspan(3);
        var textButton = new TextButton("OK", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            cash -= FORTUNE_PRICE;
            populate();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 400);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showLuckTarot() {
        sfx_magic.play(sfx);
        checkedFortune = true;
        var goodOmen = MathUtils.randomBoolean();
        var dialog = new Dialog("SOOTHSAYER", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (goodOmen) {
            var text = Gdx.files.internal("goodomen.txt").readString();
            var label = new Label(text, skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            GameScreen.goodOmen = true;
        } else {
            var text = Gdx.files.internal("badomen.txt").readString();
            var label = new Label(text, skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            GameScreen.badOmen = true;
        }
        
        dialog.getButtonTable().row().colspan(3);
        var textButton = new TextButton("OK", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            cash -= FORTUNE_PRICE;
            populate();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 350);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showSelectedItem(String item) {
        var price = prices.get(items.indexOf(item, false));
        var itemCount = inventory.get(item, 0);
        
        if (cash >= price && itemCount == 0) showBuyItem(item);
        else if (cash < price && itemCount > 0 || inventoryCount >= inventoryMax) showSellItem(item);
        else {
            var dialog = new Dialog("MARKET", skin);
            
            var table = dialog.getButtonTable();
            
            var textButton = new TextButton("BUY", skin);
            dialog.button(textButton);
            onChange(textButton, () -> showBuyItem(item));
            
            table.row();
            textButton = new TextButton("SELL", skin);
            dialog.button(textButton);
            onChange(textButton, () -> showSellItem(item));
            
            table.row();
            textButton = new TextButton("CANCEL", skin);
            dialog.button(textButton);
            
            dialog.show(stage);
        }
    }
    
    private void showBuyItem(String item) {
        var price = prices.get(items.indexOf(item, false));
        var itemCount = inventory.get(item, 0);
        
        var dialog = new Dialog("BUY " + item.toUpperCase(Locale.ROOT), skin);
        
        var table = dialog.getContentTable();
        
        var label = new Label("How many " + item + " do you want to buy at $" + formatMoney(price) + " each?" + (itemCount > 0 ? "\nYou have " + itemCount + " " + item + ".": ""), skin, "small");
        table.add(label).colspan(2);
        
        table.row();
        var slider = new Slider(1, Math.min(cash / price, inventoryMax - inventoryCount), 1, false, skin);
        slider.setValue(slider.getMaxValue());
        table.add(slider).minWidth(SLIDER_MIN_WIDTH).growX();
        
        table.row();
        var count = MathUtils.round(slider.getValue());
        var loanLabel = new Label("x" + count + " ($" + formatMoney(count * price) + ")", skin, "small-red");
        table.add(loanLabel);
        onChange(slider, () -> {
            var newCount = MathUtils.round(slider.getValue());
            loanLabel.setText("x" + newCount + " ($" + formatMoney(newCount * price) + ")");
        });
        
        var textButton = new TextButton("BUY", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            sfx_inventory.play(sfx);
            var newCount = MathUtils.round(slider.getValue());
            cash -= price * newCount;
            inventoryCount += newCount;
            inventory.put(item, itemCount + newCount);
            
            averagePaidPrice.put(item, averagePaidPrice.containsKey(item) ? (averagePaidPrice.get(item, 0) * itemCount + newCount * price) / (itemCount + newCount) : price);
            
            populate();
        });
        
        textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
    }
    
    private void showSellItem(String item) {
        var price = prices.get(items.indexOf(item, false));
        var itemCount = inventory.get(item, 0);
        
        var dialog = new Dialog("SELL " + item.toUpperCase(Locale.ROOT), skin);
        
        var table = dialog.getContentTable();
        
        var label = new Label("How many " + item + " do you want to sell at $" + formatMoney(price) + " each?" + "\nYou have " + itemCount + " " + item +
                (averagePaidPrice.containsKey(item) ? ".\nYou've paid $" + formatMoney(averagePaidPrice.get(item)) + " for each of them on average." : ""), skin, "small");
        table.add(label).colspan(2);
        
        table.row();
        var slider = new Slider(1, itemCount, 1, false, skin);
        slider.setValue(slider.getMaxValue());
        table.add(slider).minWidth(SLIDER_MIN_WIDTH).growX();
        
        table.row();
        var count = MathUtils.round(slider.getValue());
        var loanLabel = new Label("x" + count + " ($" + formatMoney(count * price) + ")", skin, "small-red");
        table.add(loanLabel);
        onChange(slider, () -> {
            var newCount = MathUtils.round(slider.getValue());
            loanLabel.setText("x" + newCount + " ($" + formatMoney(newCount * price) + ")");
        });
        
        var textButton = new TextButton("SELL", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            sfx_money.play(sfx);
            var newCount = MathUtils.round(slider.getValue());
            cash += price * newCount;
            inventoryCount -= newCount;
            inventory.put(item, itemCount - newCount);
            
            if (itemCount - newCount <= 0) averagePaidPrice.remove(item);
            populate();
        });
        
        textButton = new TextButton("CANCEL", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
    }
    
    private void showPocketEvent() {
        var pockets = MathUtils.random(POCKETS_OFFER_MINIMUM, POCKETS_OFFER_MAXIMUM);
        var price = MathUtils.random(POCKETS_PRICE_MINIMUM, POCKETS_PRICE_MAXIMUM);
        
        var dialog = new Dialog("BUY POCKETS", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label("A beggar approaches:\n\"Say man, wanna buy some pockets?\"", skin, "small");
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label);
        
        dialog.getContentTable().row();
        label = new Label(cash >= price ? "Purchase " + pockets + " for $" + formatMoney(price) + "." :
                "Yikes! You're too poor to even pay a beggar $" + formatMoney(price) + " for " + pockets + " pockets." +
                        "\nWhat a loser...", skin, "small");
        dialog.getContentTable().add(label);
        
        if (cash >= price) {
            var textButton = new TextButton("PURCHASE POCKETS", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_inventory.play(sfx);
                cash -= price;
                inventoryMax += pockets;
                populate();
            });
        }
        
        var textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showChestEvent() {
        var reward = MathUtils.random(CHEST_REWARD_MINIMUM, CHEST_REWARD_MAXIMUM) * day;
        var damage = MathUtils.random(CHEST_DAMAGE_MINIMUM, CHEST_DAMAGE_MAXIMUM);
        
        var dialog = new Dialog("A STRANGE CHEST", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label("On your way you discover an abandoned chest.\nYou can open it, but it might be a trap...", skin, "small");
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label);
        
        var textButton = new TextButton("OPEN CHEST", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            if (MathUtils.randomBoolean(CHEST_REWARD_CHANCE)) {
                sfx_money.play(sfx);
                var dialog2 = new Dialog("FAT STACKS!", skin);
                
                var rewardLabel = new Label("You open the chest to discover $" + formatMoney(reward) + ".\nGotta get that paper!", skin, "small");
                rewardLabel.setAlignment(Align.center);
                dialog2.getContentTable().add(rewardLabel);
                
                var rewardButton = new TextButton("OK", skin);
                dialog2.button(rewardButton);
                onChange(rewardButton, () -> {
                    cash += reward;
                    populate();
                    if (inCastle) {
                        showCastleRoom();
                    } else if (eventsText != null) showEventsDialog();
                });
                
                dialog2.show(stage);
            } else {
                var dialog2 = new Dialog("OUCHIES!", skin);
                
                var rewardLabel = new Label("You open the chest to only hear a mechanical click and then a sudden bright flash." +
                        "\nYou take " + damage + " damage.\nStop screwing around with other people's stuff, man!", skin, "small");
                rewardLabel.setAlignment(Align.center);
                dialog2.getContentTable().add(rewardLabel);
                
                var rewardButton = new TextButton("OK", skin);
                dialog2.button(rewardButton);
                onChange(rewardButton, () -> {
                    sfx_grenade.play(sfx);
                    sfx_hurt.play(sfx);
                    health -= damage;
                    if (health <= 0) {
                        health = 0;
                        showGameOver();
                    } else if (inCastle) {
                        showCastleRoom();
                    } else if (eventsText != null) showEventsDialog();
                    populate();
                });
                
                dialog2.show(stage);
            }
            
            
        });
        
        textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            if (inCastle) {
                showCastleRoom();
            }
        });
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showSharpenEvent() {
        var damage = GameScreen.damage + MathUtils.random(SHARPENING_DAMAGE_MINIMUM, SHARPENING_DAMAGE_MAXIMUM);
        var price = MathUtils.random(SHARPENING_PRICE_MINIMUM, SHARPENING_PRICE_MAXIMUM);
        var hasWeapon = weapon != null;
        
        var dialog = new Dialog("SHARPEN WEAPON", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label("A blacksmith approaches:\n\"Your weapon is rather dull, friend.\nI can take care of that... for the right price\"", skin, "small");
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label);
        
        dialog.getContentTable().row();
        if (hasWeapon) {
            label = new Label(
                    cash >= price ? "Sharpen your weapon to increase damage from " + GameScreen.damage + " to " + damage + " for $" + formatMoney(price) + "." :
                            "The black smith scoffs at your mere $" + formatMoney(
                                    cash) + ".\nYou need $" + formatMoney(
                                    price) + " to increase damage from " + GameScreen.damage + " to " + damage + ".",
                    skin, "small");
            dialog.getContentTable().add(label);
        } else {
            label = new Label("\"Wait a second, you don't have a weapon.\nSorry my eyes are getting bad in my old age." +
                    "\nI've got a case of gut rot too. And checkered stool.\nNot that it's really relevant to our current situation." +
                    "\nTo be frank, I'm just a bit lonely.\nI'm not frank though. I'm stevens. Nice to meet you.\"", skin, "small");
            label.setAlignment(Align.center);
            dialog.getContentTable().add(label);
        }
        
        if (cash >= price && hasWeapon) {
            var textButton = new TextButton("PURCHASE SHARPENING", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_inventory.play(sfx);
                cash -= price;
                GameScreen.damage = damage;
                populate();
            });
        }
        
        var textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private int previewWeaponDamage;
    private int previewWeaponAccuracy;
    private String previewWeaponName;
    private int previewWeaponPrice;
    private Sound previewWeaponSound;
    
    private void showWeaponEvent() {
        var hasWeapon = weapon != null;
        var index = MathUtils.random(5);
        switch (index) {
            case (0):
                previewWeaponName = "Knuckle Dusters";
                previewWeaponDamage = 5;
                previewWeaponAccuracy = 85;
                previewWeaponPrice = 200;
                previewWeaponSound = sfx_slash;
                break;
            case (1):
                previewWeaponName = "Cross of Saint Peter";
                previewWeaponDamage = 10;
                previewWeaponAccuracy = 75;
                previewWeaponPrice = 2500;
                previewWeaponSound = sfx_slash;
                break;
            case (2):
                previewWeaponName = "Key Blade";
                previewWeaponDamage = 25;
                previewWeaponAccuracy = 65;
                previewWeaponPrice = 6000;
                previewWeaponSound = sfx_slash;
                break;
            case (3):
                previewWeaponName = "Thumb Gun";
                previewWeaponDamage = 30;
                previewWeaponAccuracy = 60;
                previewWeaponPrice = 7500;
                previewWeaponSound = sfx_gun;
                break;
            case (4):
                previewWeaponName = "Ensorceled Garlic Clove";
                previewWeaponDamage = 28;
                previewWeaponAccuracy = 80;
                previewWeaponPrice = 50000;
                previewWeaponSound = sfx_slash;
                break;
            case (5):
                previewWeaponName = "God's Wrath";
                previewWeaponDamage = 25;
                previewWeaponAccuracy = 95;
                previewWeaponPrice = 200000;
                previewWeaponSound = sfx_slash;
                break;
        }
        
        var dialog = new Dialog("HUNTER'S GUILD", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label("A dapper gentleman from the Vampire Hunter's Guild approaches:\n\"A rare weapon is it's own reward...\"\nWhat does that even mean?", skin, "small");
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label);
        
        dialog.getContentTable().row();
        label = new Label(previewWeaponName.toUpperCase(Locale.ROOT), skin, "small");
        label.setColor(Color.GOLD);
        dialog.getContentTable().add(label).padTop(20);
        
        dialog.getContentTable().row();
        label = new Label("Damage: " + previewWeaponDamage + " Accuracy: " + previewWeaponAccuracy + "%", skin, "very-small");
        label.setColor(Color.RED);
        dialog.getContentTable().add(label).padBottom(20);
        
        if (hasWeapon) {
            dialog.getContentTable().row();
            label = new Label("Compared to your...", skin, "small");
            dialog.getContentTable().add(label);
            
            dialog.getContentTable().row();
            label = new Label(weapon.toUpperCase(Locale.ROOT), skin, "small");
            label.setColor(Color.GRAY);
            dialog.getContentTable().add(label);
            
            dialog.getContentTable().row();
            label = new Label("Damage: " + damage + " Accuracy: " + accuracy + "%", skin, "very-small");
            label.setColor(Color.GRAY);
            dialog.getContentTable().add(label).padBottom(20);
        }
        
        dialog.getContentTable().row();
        label = new Label(cash >= previewWeaponPrice ? "" : "The gentleman scoffs at your mere $" + formatMoney(cash) +
                        ".\nYou need $" + formatMoney(previewWeaponPrice) + ". Pathetic!", skin, "small");
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label);
        
        if (cash >= previewWeaponPrice) {
            var textButton = new TextButton("PURCHASE WEAPON", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                cash -= previewWeaponPrice;
                GameScreen.damage = previewWeaponDamage;
                GameScreen.accuracy = previewWeaponAccuracy;
                GameScreen.weapon = previewWeaponName;
                GameScreen.weaponSound = previewWeaponSound;
                populate();
            });
        }
        
        var textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showBeastiary(int index) {
        sfx_pages.play(sfx);
        var dialog = new Dialog("BEASTIARY", skin);
        
        var table = dialog.getContentTable();
        
        if (beastiaryUnlock == 0) {
            var label = new Label("There is nothing here yet...", skin, "small");
            table.add(label);
        } else {
            switch (index) {
                case 0:
                    var label = new Label("I have seen a great deal of wondrous and frightening things in my travels.\n" +
                            "I shall document them here in the likely case that I don't make it.", skin);
                    table.add(label);
                case 1:
                    label = new Label("Beastious Transformius", skin);
                    table.add(label);
                    
                    table.row();
                    var image = new Image(skin, "beast");
                    table.add(image);
                    break;
                case 2:
                    label = new Label("Bridea Nocturna", skin);
                    table.add(label);
                    
                    table.row();
                    image = new Image(skin, "bride");
                    table.add(image);
                    break;
                case 3:
                    label = new Label("Nobulus van Sapien", skin);
                    table.add(label);
                    
                    table.row();
                    image = new Image(skin, "noble");
                    table.add(image);
                    break;
                case 4:
                    label = new Label("Vamperius Egypticus", skin);
                    table.add(label);
                    
                    table.row();
                    image = new Image(skin, "pharaoh");
                    table.add(image);
                    break;
            }
        }
        
        dialog.getButtonTable().defaults().uniformX();
        if (index > 0) {
            var textButton = new TextButton("PREVIOUS", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                showBeastiary(index - 1);
            });
        }
        
        var textButton = new TextButton("CLOSE", skin);
        dialog.button(textButton);
        
        if (index < 4) {
            textButton = new TextButton("NEXT", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                showBeastiary(index + 1);
            });
        }
        
        dialog.show(stage);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showBrigandEvent() {
        sfx_evilLaugh.play(sfx);
        var dialog = new Dialog("BRIGANDS", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        var label = new Label(Gdx.files.internal("brigand.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label("You lost $" + formatMoney(cash) + ".", skin, "small");
        dialog.getContentTable().add(label);
        
        var textButton = new TextButton("CRY A LITTLE AND MOVE ON", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            cash = 0;
            populate();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 400);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showMobEvent() {
        sfx_evilLaugh.play(sfx);
        var dialog = new Dialog("MOB", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        var label = new Label(Gdx.files.internal("mob.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label("You lost all of your items.", skin, "small");
        dialog.getContentTable().add(label);
        
        var textButton = new TextButton("CRY A LITTLE AND MOVE ON", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            inventoryCount = 0;
            inventory.clear();
            populate();
        });
        
        dialog.show(stage);
        dialog.setSize(600, 400);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showSalesmanEvent() {
        var healthIncrease = MathUtils.random(FORMULA_HEALTH_MINIMUM, FORMULA_HEALTH_MAXIMUM);
        var price = MathUtils.random(FORMULA_PRICE_MINIMUM, FORMULA_PRICE_MAXIMUM);
        
        var dialog = new Dialog("SNAKE OIL SALESMAN", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label(Gdx.files.internal("salesman.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label(
                cash >= price ? "Drink formula for an increase of " + healthIncrease + " health for $" + formatMoney(
                        price) + "." :
                        "You're too poor to even consider such an offer. $" + formatMoney(
                                price) + " for an increase of " + healthIncrease + " health is a sad opportunity squandered.",
                skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        if (cash >= price) {
            var textButton = new TextButton("PURCHASE FORMULA", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_heal.play(sfx);
                cash -= price;
                healthMax += healthIncrease;
                health = healthMax;
                populate();
            });
        }
        
        var textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(600, 450);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showHealEvent() {
        var dialog = new Dialog("FOREST PIXIE", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var label = new Label(Gdx.files.internal("pixie.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        var textButton = new TextButton("EAT THE PIXIE", skin);
        dialog.button(textButton);
        onChange(textButton, () -> {
            sfx_heal.play(sfx);
            health = healthMax;
            populate();
        });
        
        textButton = new TextButton("SWAT IT AWAY", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(650, 450);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showBattle() {
        sfx_vamp.play(sfx);
        vampireHealth = VAMPIRE_HEALTH_MAX;
        var vampTable = new Table();
        var dialog = new Dialog("VAMPIRE ATTACK", skin) {
            @Override
            protected void result(Object object) {
                vampTable.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.removeActor()));
            }
        };
        
        var table = dialog.getContentTable();
        
        table.defaults().space(20);
        table.pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            table.add(label).row();
        }
        
        var label = new Label("You have been ambushed by a " + vampires.get(Math.min(beastiaryUnlock, 3)) + "!", skin, "small");
        table.add(label);
        
        if (weapon == null) {
            table.row();
            label = new Label("You have no weapon to defend yourself with...", skin, "small");
            table.add(label);
        } else {
            table.row();
            label = new Label(weapon.toUpperCase(Locale.ROOT), skin, "small-gray");
            table.add(label).space(0);
            
            table.row();
            label = new Label("Damage: " + damage + " Accuracy: " + accuracy + "%", skin, "very-small");
            table.add(label).space(0);
            
            var textButton = new TextButton("ATTACK", skin);
            dialog.button(textButton);
            onChange(textButton, this::showAttack);
        }
        
        table.row();
        label = new Label("What will you do?", skin, "small");
        table.add(label);
        
        var textButton = new TextButton("RUN", skin);
        dialog.button(textButton);
        onChange(textButton, this::showRun);
        
        dialog.show(stage);
        dialog.setSize(600, 350);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
        
        vampTable.setBackground(skin.getDrawable("vamp-border-10"));
        stage.addActor(vampTable);
        vampTable.addAction(Actions.sequence(Actions.color(new Color(1, 1, 1, 0)), Actions.fadeIn(.25f)));
        var image = new Image(skin, vampireImages.get(Math.min(beastiaryUnlock, 3)));
        image.setScaling(Scaling.none);
        vampTable.add(image);
        vampTable.pack();
        temp.set(dialog.getX(Align.right), dialog.getY(Align.center));
        vampTable.setPosition(temp.x, temp.y, Align.left);
    }
    
    private void showRun() {
        var success = MathUtils.randomBoolean(runawayChances.get(runAwayIndex)) || goodBattleOmen;
        var hurt = MathUtils.randomBoolean((vampireAccuracies.get(Math.min(beastiaryUnlock, 3)) + (badBattleOmen ? BAD_OMEN_VAMPIRE_ACCURACY_BONUS : 0)) / 100f);
        
        var vampTable = new Table();
        
        var dialog = new Dialog("RUN AWAY", skin) {
            @Override
            protected void result(Object object) {
                vampTable.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.removeActor()));
            }
        };
        
        var table = dialog.getContentTable();
        
        table.defaults().space(20);
        table.pad(10);
        if (success) {
            transitionMusic(locationMusics[locations.indexOf(location, false)]);
            vampTable.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.removeActor()));
            
            var label = new Label("You managed to get away!", skin, "small");
            table.add(label);
            
            var textButton = new TextButton("OK", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                goodBattleOmen = false;
                badBattleOmen = false;
                runAwayIndex++;
                if (runAwayIndex >= runawayChances.size) runAwayIndex = runawayChances.size - 1;
                if (inCastle) {
                    showCastleRoom();
                } else {
                    if (eventsText != null) showEventsDialog();
                }
            });
        } else {
            table.row();
            var label = new Label("You tried to escape and failed!", skin);
            table.add(label);
            
            if (runAwayIndex > 0) {
                table.row();
                label = new Label("This vampire is faster than anything you've seen before!", skin, "small");
                table.add(label);
            }
            
            if (hurt) {
                sfx_vampAttack.play(sfx);
                sfx_hurt.play(sfx);
                var damage = vampireDamages.get(Math.min(beastiaryUnlock, 3));
                health -= damage;
                if (health <= 0) health = 0;
                populate();
                
                table.row();
                label = new Label("The vampire attacks for " + damage + " damage.\nYou have " + health + " health", skin, "small");
                table.add(label);
                
                if (health <= 0) {
                    health = 0;
                    
                    table.row();
                    label = new Label("You were mortally wounded!", skin, "small");
                    table.add(label);
                    
                    var textButton = new TextButton("ACCEPT YOUR FATE", skin);
                    dialog.button(textButton);
                    onChange(textButton, this::showGameOver);
                } else {
                    if (weapon != null) {
                        var textButton = new TextButton("ATTACK", skin);
                        dialog.button(textButton);
                        onChange(textButton, this::showAttack);
                    }
                    
                    var textButton = new TextButton("RUN", skin);
                    dialog.button(textButton);
                    onChange(textButton, this::showRun);
                }
            } else {
                if (weapon != null) {
                    var textButton = new TextButton("ATTACK", skin);
                    dialog.button(textButton);
                    onChange(textButton, this::showAttack);
                }
                
                var textButton = new TextButton("RUN", skin);
                dialog.button(textButton);
                onChange(textButton, this::showRun);
            }
        }
        
        dialog.show(stage);
        dialog.setSize(600, 300);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
        
        vampTable.setBackground(skin.getDrawable("vamp-border-10"));
        stage.addActor(vampTable);
        vampTable.addAction(Actions.sequence(Actions.color(new Color(1, 1, 1, 0)), Actions.fadeIn(.25f)));
        var image = new Image(skin, vampireImages.get(Math.min(beastiaryUnlock, 3)));
        image.setScaling(Scaling.none);
        vampTable.add(image);
        vampTable.pack();
        temp.set(dialog.getX(Align.right), dialog.getY(Align.center));
        vampTable.setPosition(temp.x, temp.y, Align.left);
    }
    
    private void showAttack() {
        var success = MathUtils.randomBoolean((accuracy + (goodBattleOmen ? GOOD_BATTLE_OMEN_ACCURACY_BONUS : 0)) / 100f);
        var hurt = MathUtils.randomBoolean((vampireAccuracies.get(Math.min(beastiaryUnlock, 3)) + (badBattleOmen ? BAD_OMEN_VAMPIRE_ACCURACY_BONUS : 0)) / 100f);
        
        var vampTable = new Table();
        
        var dialog = new Dialog("ATTACK", skin) {
            @Override
            protected void result(Object object) {
                vampTable.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.removeActor()));
            }
        };
        
        var table = dialog.getContentTable();
        
        table.defaults().space(20);
        table.pad(10);
        if (success) {
            weaponSound.play(sfx);
            vampireHealth -= damage;
            if (vampireHealth < 0) vampireHealth = 0;
            
            if (vampireHealth <= 0) vampTable.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.removeActor()));
            var label = new Label("You attacked the vampire for " + damage + ".\n" + (vampireHealth <= 0 ? "It slumps over and dies." : "It still has " + vampireHealth + " health."), skin, "small");
            label.setAlignment(Align.center);
            table.add(label);
        } else {
            var label = new Label("You tried to attack and missed!", skin, "small");
            table.add(label);
        }
        
        if (hurt) {
            sfx_hurt.play(sfx);
            var damage = vampireDamages.get(Math.min(beastiaryUnlock, 3));
            health -= damage;
            if (health <= 0) health = 0;
            populate();
            
            table.row();
            var label = new Label("The vampire attacks for " + damage + " damage.\nYou have " + health + " health", skin, "small");
            table.add(label);
        }
        
        if (health <= 0) {
            health = 0;
            
            table.row();
            var label = new Label("You were mortally wounded!", skin, "small");
            table.add(label);
            
            var textButton = new TextButton("ACCEPT YOUR FATE", skin);
            dialog.button(textButton);
            onChange(textButton, this::showGameOver);
        } else if (vampireHealth <= 0) {
            var reward = rewards.get(Math.min(beastiaryUnlock, 3));
            var vampireName = vampires.get(Math.min(beastiaryUnlock, 3)).toUpperCase(Locale.ROOT);
            table.row();
            var label = new Label("YOU HAVE SLAIN THE "  + vampireName + ". You claim the reward of $" + formatMoney(reward), skin, "small");
            table.add(label);
            
            cash += reward;
            beastiaryUnlock++;
            populate();
            
            var textButton = new TextButton("VICTORY", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                goodBattleOmen = false;
                badBattleOmen = false;
                
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            });
        } else {
            if (weapon != null) {
                var textButton = new TextButton("ATTACK", skin);
                dialog.button(textButton);
                onChange(textButton, this::showAttack);
            }
            
            var textButton = new TextButton("RUN", skin);
            dialog.button(textButton);
            onChange(textButton, this::showRun);
        }
        
        dialog.show(stage);
        dialog.setSize(600, 325);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
        
        vampTable.setBackground(skin.getDrawable("vamp-border-10"));
        stage.addActor(vampTable);
        vampTable.addAction(Actions.sequence(Actions.color(new Color(1, 1, 1, 0)), Actions.fadeIn(.25f)));
        var image = new Image(skin, vampireImages.get(Math.min(beastiaryUnlock, 3)));
        image.setScaling(Scaling.none);
        vampTable.add(image);
        vampTable.pack();
        
        temp.set(dialog.getX(Align.right), dialog.getY(Align.center));
        vampTable.setPosition(temp.x, temp.y, Align.left);
    }
    
    private final static Vector2 temp = new Vector2();
    
    private void showWelcomeMessage() {
        var dialog = new Dialog("DAY ZERO", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        var label = new Label(Gdx.files.internal("welcome.txt").readString(), skin, "small");
        label.setWrap(true);
        dialog.getContentTable().add(label).growX();
        
        dialog.getContentTable().row();
        label = new Label("BUY, SELL, SLAY", skin, "small");
        dialog.getContentTable().add(label);
        
        dialog.getContentTable().row();
        label = new Label("SURVIVE TO DAY " + dayMax, skin);
        dialog.getContentTable().add(label);
        
        var textButton = new TextButton("BEGIN", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(600, 350);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showCastle() {
        sfx_portal.play(sfx);
        var dialog = new Dialog("CASTLE", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (door < DOOR_MAX) {
            var label = new Label(Gdx.files.internal("castle.txt").readString(), skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            
            var textButton = new TextButton("EXPLORE", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                inCastle = true;
                
                showCastleRoom();
            });
        } else {
            var label = new Label("You've had enough adventure on this day.", skin, "small");
            dialog.getContentTable().add(label);
            
            var textButton = new TextButton("LEAVE", skin);
            dialog.button(textButton);
        }
        
        dialog.show(stage);
        dialog.setSize(800, 450);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showCastleRoom() {
        var dialog = new Dialog("STRANGE CORRIDOR", skin);
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (door < DOOR_MAX) {
            var label = new Label(
                    (door != 0 ? "You return through the door you came in.\n" : "") + "In this strange corridor, it's hard to tell which way you have came and which way you have left.",
                    skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            
            var textButton = new TextButton("OPEN A DOOR", skin);
            dialog.button(textButton);
            onChange(textButton, () -> {
                sfx_door.play(sfx);
                switch (MathUtils.random(7)) {
                    case 0:
                        if (beastiaryUnlock < vampires.size) {
                            showBattle();
                            break;
                        }
                    case 1:
                        showChestEvent();
                        break;
                    case 2:
                        showPocketEvent();
                        break;
                    case 3:
                        showSalesmanEvent();
                        break;
                    case 4:
                        showSharpenEvent();
                        break;
                    case 5:
                        showWeaponEvent();
                        break;
                    case 6:
                        showHealEvent();
                        break;
                    case 7:
                        showEmptyRoomEvent();
                        break;
                }
                door++;
            });
        } else {
            var label = new Label(
                    "You return through the door you came in.\n" + Gdx.files.internal("castlefinish.txt").readString(),
                    skin, "small");
            label.setWrap(true);
            dialog.getContentTable().add(label).growX();
            
            var textButton = new TextButton("LEAVE", skin);
            dialog.button(textButton);
        }
        
        dialog.show(stage);
        dialog.setSize(550, 300);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    private void showEmptyRoomEvent() {
        var dialog = new Dialog("EMPTY ROOM", skin) {
            @Override
            public void hide(Action action) {
                super.hide(action);
                if (inCastle) {
                    showCastleRoom();
                } else if (eventsText != null) showEventsDialog();
            }
        };
        
        dialog.getContentTable().defaults().space(20);
        dialog.getContentTable().pad(10);
        
        if (inCastle) {
            var label = new Label(Gdx.files.internal("castleroom.txt").readString(), skin, "small");
            dialog.getContentTable().add(label).row();
        }
        
        var array = new Array<>(Gdx.files.internal("emptyroom.txt").readString().split("\\n"));
        var label = new Label(array.random(), skin, "small");
        dialog.getContentTable().add(label);
        
        var textButton = new TextButton("LEAVE", skin);
        dialog.button(textButton);
        
        dialog.show(stage);
        dialog.setSize(650, 300);
        dialog.setPosition(512 - MathUtils.floor(dialog.getWidth() / 2), 288 - MathUtils.floor(dialog.getHeight() / 2));
    }
    
    public static String formatMoney(long money) {
        if (money >= 1000000000000L) return format("%,.1fT", money / 1000000000000f);
        else if (money >= 1000000000) return format("%,.1fB", money / 1000000000f);
        else if (money >= 1000000) return format("%,.1fM", money / 1000000f);
        else if (money >= 10000) return format("%,.1fK", money / 1000f);
        return format("%,d", money);
    }
}