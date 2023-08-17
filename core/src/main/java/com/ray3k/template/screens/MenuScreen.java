package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Event;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;

public class MenuScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private SpineDrawable spineDrawable;
    public static long lastScore;
    public static String lastScoreName = "";
    
    @Override
    public void show() {
        super.show();
        
        stage = new Stage(new FitViewport(1024, 576), batch);
        Gdx.input.setInputProcessor(stage);
    
        var root = new Stack();
        root.setFillParent(true);
        stage.addActor(root);
        
        spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlood.skeletonData, SpineBlood.animationData);
        spineDrawable.getAnimationState().setAnimation(0, SpineBlood.animationStand, false);
        spineDrawable.getAnimationState().addAnimation(0, SpineBlood.animationAnimation, false, 0);
        var image = new Image(spineDrawable);
        root.add(image);
        spineDrawable.getAnimationState().addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                if (event.getData().getAudioPath() != null) {
                    var sound = Gdx.audio.newSound(Gdx.files.internal(event.getData().getAudioPath()));
                    sound.play(sfx);
                }
            }
        });
        
        var table = new Table();
        root.add(table);
        
        image = new Image(skin, "title");
        image.setScaling(Scaling.fit);
        table.add(image).growX().padLeft(100).padRight(100).padTop(50).space(50);
        
        table.row();
        var subTable = new Table();
        table.add(subTable);
        
        var label = new Label("In time of war, the loudest patriots are the greatest profiteers", skin, "subtitle");
        subTable.add(label).space(15);
        
        subTable.row();
        label = new Label("-August Bebel", skin, "subtitle-author");
        subTable.add(label).expandX().right();
        
        table.row();
        subTable = new Table();
        table.add(subTable).grow();
        
        subTable.defaults().space(100);
        var textButton = new TextButton("PLAY", skin);
        subTable.add(textButton);
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> {
            Gdx.input.setInputProcessor(null);
            core.transition(new IntroScreen());
        });
        
        textButton = new TextButton("OPTIONS", skin);
        subTable.add(textButton);
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> {
            Gdx.input.setInputProcessor(null);
            core.transition(new OptionsScreen());
        });
        
        textButton = new TextButton("CREDITS", skin);
        subTable.add(textButton);
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> {
            Gdx.input.setInputProcessor(null);
            core.transition(new CreditsScreen());
        });
        
        if (preferences.contains("highscore")) {
            table.row();
            subTable = new Table();
            table.add(subTable);
            
            subTable.defaults().space(20);
            if (!lastScoreName.equals("")) {
                label = new Label("Score: " + lastScoreName + " " + lastScore, skin, "small");
                subTable.add(label);
            }
            
            label = new Label("Highscore: " + preferences.getString("highscore-name", "") + " " + preferences.getLong("highscore", 0), skin, "small");
            subTable.add(label);
            
            textButton = new TextButton("SEE GLOBAL HIGH SCORES", skin, "very-small");
            subTable.add(textButton);
            onChange(textButton, () -> {
                Gdx.net.openURI("https://gamejolt.com/games/dracwars/832631/scores/842990/best");
            });
        }
    }
    
    @Override
    public void act(float delta) {
        spineDrawable.update(delta);
        
        if (!bgm_menuIntro.isPlaying() && !bgm_menu.isPlaying()) {
            bgm_menu.play();
            bgm_menu.setVolume(core.bgm);
            bgm_menu.setLooping(true);
        }
        
        stage.act(delta);
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
