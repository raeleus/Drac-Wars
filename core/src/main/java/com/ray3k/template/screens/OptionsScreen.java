package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;

public class OptionsScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
    
        stage = new Stage(new FitViewport(1024, 576), batch);
        Gdx.input.setInputProcessor(stage);
    
        sceneBuilder.build(stage, skin, Gdx.files.internal("menus/options.json"));
    
        var textButton = stage.getRoot().findActor("ok");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new MenuScreen());
            }
        });
        
        Slider slider = stage.getRoot().findActor("bgm");
        slider.setValue(Core.bgm);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Core.bgm = ((Slider) actor).getValue();
                preferences.putFloat("bgm", Core.bgm);
                preferences.flush();
                bgm_menu.setVolume(Core.bgm);
                bgm_menuIntro.setVolume(Core.bgm);
            }
        });
    
        final Music sfx = bgm_audioSample;
        sfx.setLooping(true);
        
        slider = stage.getRoot().findActor("sfx");
        slider.setValue(Core.sfx);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Core.sfx = ((Slider) actor).getValue();
                preferences.putFloat("sfx", Core.sfx);
                preferences.flush();
                sfx.setVolume(Core.sfx);
            }
        });
        slider.addListener(new DragListener() {
            Music selectedMusic;
            {
                setTapSquareSize(0);
            }
        
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                sfx.play();
                if (bgm_menu.isPlaying()) selectedMusic = bgm_menu;
                else selectedMusic = bgm_menuIntro;
                selectedMusic.pause();
            }
        
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                sfx.pause();
                selectedMusic.play();
            }
        });
    }
    
    @Override
    public void act(float delta) {
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