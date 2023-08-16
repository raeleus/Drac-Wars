package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TypingButton;
import com.github.tommyettinger.textra.TypingConfig;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;

public class CreditsScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
        
        stage = new Stage(new FitViewport(1024, 576), batch);
        Gdx.input.setInputProcessor(stage);
        
        var root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.defaults().space(20);
        root.add().expand();
        
        root.row();
        var table = new Table();
        root.add(table);
        
        var label = new Label("A game by Raeleus", skin, "small");
        table.add(label);
        
        table.row();
        var textButton = new TextButton("Play more games!", skin, "very-small");
        table.add(textButton).right();
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> Gdx.net.openURI("https://raeleus.itch.io/"));
        
        root.row();
        table = new Table();
        root.add(table);
        
        label = new Label("Shroomtime - Music", skin, "small");
        table.add(label).colspan(2);
        
        table.row().spaceRight(10);
        textButton = new TextButton("Itch", skin, "very-small");
        table.add(textButton).expandX().right();
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> Gdx.net.openURI("https://shroomtime.itch.io/"));
        
        textButton = new TextButton("YouTube", skin, "very-small");
        table.add(textButton);
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> Gdx.net.openURI("https://www.youtube.com/@Shroom64"));
        
        root.row();
        table = new Table();
        root.add(table);
        
        label = new Label("BEFLUFFLYORBEGONE - Art", skin, "small");
        table.add(label);
        
        table.row();
        textButton = new TextButton("DeviantArt", skin, "very-small");
        table.add(textButton).right();
        textButton.addListener(sndChangeListener);
        onChange(textButton, () -> Gdx.net.openURI("https://www.deviantart.com/beflufflyorbegone"));
        
        root.row();
        textButton = new TextButton("OK", skin);
        root.add(textButton).expand().padBottom(30).bottom();
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new MenuScreen());
            }
        });
    
//        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put('\n', .5f);
//        Label label = stage.getRoot().findActor("label");
//        var typingLabel = new TypingLabel(label.getText().toString(), skin);
//        typingLabel.setAlignment(Align.center);
//        ((Table) label.getParent()).getCell(label).setActor(typingLabel);
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
